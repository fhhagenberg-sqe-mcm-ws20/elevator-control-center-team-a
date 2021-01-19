package at.fhhgb.team.a.elevators.app;

import at.fhhgb.team.a.elevators.exceptions.ElevatorSystemException;
import at.fhhgb.team.a.elevators.model.*;
import at.fhhgb.team.a.elevators.rmi.ConnectionService;
import at.fhhgb.team.a.elevators.rmi.RMIConnectionListener;
import at.fhhgb.team.a.elevators.threading.ThreadManager;
import sqelevator.IElevator;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Optional;

public class ElevatorControlCenter implements IElevatorSystem {

    /**
     * Callback to the App to indicate that the connection is established and
     * the {@link ElevatorControlCenter#building} created.
     */
    private final ConnectedListener connectedCallback;

    /**
     * Callback to the App to indicate that the connection is lost
     */
    private final DisconnectedListener disconnectedCallback;

    /**
     * RMI URL endpoint
     */
    private final String connectionURL;

    /**
     * Flag to not poll the API until a connection is established
     */
    private boolean connected;

    /**
     * Elevator that is controlled by this center
     */
    private IElevator elevatorApi;

    /**
     * The building used in the elevator control center.
     */
    private Building building;

    /**
     * Tick of the last successful update
     */
    private long lastUpdateTick;

    public ElevatorControlCenter(IElevator elevatorApi,
                                 ConnectedListener connectedCallback,
                                 DisconnectedListener disconnectedCallback) {
        this.connectedCallback = connectedCallback;
        this.disconnectedCallback = disconnectedCallback;
        this.elevatorApi = elevatorApi;
        connectionURL = "";
        connected = true;
        lastUpdateTick = -1;
    }

    public ElevatorControlCenter(String connectionURL,
                                 ConnectedListener connectedCallback,
                                 DisconnectedListener disconnectedCallback) {
        this.connectedCallback = connectedCallback;
        this.disconnectedCallback = disconnectedCallback;
        this.connectionURL = connectionURL;
        connected = false;
        waitForConnection(this::connected);
    }

    @Override
    public void run() {
        try {
            if (connected) {
                pollElevatorApi();
            }
        } catch (ElevatorSystemException e) {
            connected = false;
            disconnectedCallback.onConnectionLost();
            waitForConnection(this::reconnected);
        }
    }

    /**
     * Updates the {@link ElevatorControlCenter#building} by polling new values from the API
     * @throws ElevatorSystemException when a connection issue arises
     */
    @Override
    public void pollElevatorApi() throws ElevatorSystemException {
        long startTick;
        try {
            startTick = elevatorApi.getClockTick();
        } catch (RemoteException e) {
            throw new ElevatorSystemException("Failed to poll clock tick.", e);
        }

        if(startTick == lastUpdateTick) {
            // we already updated to that state
            return;
        }

        List<Floor> floors;
        List<Elevator> elevators;
        try {
            floors = createFloors();
            elevators = createElevators(floors);
            addFloorServiceAssignments(elevators, floors);
        } catch (RemoteException e) {
            throw new ElevatorSystemException("Failed to poll floors and elevators.", e);
        }


        long endTick;
        try {
            endTick = elevatorApi.getClockTick();
        } catch (RemoteException e) {
            throw new ElevatorSystemException("Failed to poll clock tick.", e);
        }

        if(startTick == endTick) {
            try {
                updateBuilding(floors, elevators);
            } catch (RemoteException rException) {
                throw new ElevatorSystemException("Failed to poll floors and elevators.", rException);
            }
            lastUpdateTick = startTick;
        } else {
            // ClockTick changed --> retry
            pollElevatorApi();
        }
    }

    @Override
    public Building getBuilding() {
        return this.building;
    }

    private void waitForConnection(RMIConnectionListener callback) {
        ConnectionService rmiService = new ConnectionService(connectionURL, callback);
        ThreadManager.getInstance().scheduleRunnable(rmiService, 100);
    }

    private void reconnected(IElevator elevatorApi) {
        this.elevatorApi = elevatorApi;
        connected = true;
        ThreadManager.getInstance().stopCurrentTasks();
        connectedCallback.onConnectionEstablished();
    }

    private void connected(IElevator elevatorApi) {
        this.elevatorApi = elevatorApi;
        connected = true;
        ThreadManager.getInstance().stopCurrentTasks();

        while (null == building) {
            try {
                pollElevatorApi();
            } catch (ElevatorSystemException e) {
                connected = false;
                waitForConnection(this::connected);
            }
        }
        connectedCallback.onConnectionEstablished();
    }

    private void updateBuilding(List<Floor> floors, List<Elevator> elevators) throws RemoteException {
        if(building == null) {
            building = createBuilding(floors, elevators);
        } else {
            updateFloors();
            updateElevators(building.getFloors());
        }
    }

    private void updateFloors() throws RemoteException {
        int floorNum = elevatorApi.getFloorNum();
        for(int i = 0; i < floorNum; i ++) {
            boolean buttonDown = elevatorApi.getFloorButtonDown(i);
            boolean buttonUp = elevatorApi.getFloorButtonUp(i);

            Floor floor = building.getFloor(i);

            if (floor == null) {
                floor = new Floor(i);
                List<Floor> floors = building.getFloors();
                floors.add(floor);

                building.setFloors(floors);
            }

            floor.clearButtonState();
            if(buttonDown) {
                floor.pressDownButton();
            }

            if(buttonUp) {
                floor.pressUpButton();
            }
        }
    }

    private void updateElevators(List<Floor> floors) throws RemoteException {
        for (Elevator elevator: building.getElevators()) {
            updateElevator(floors, elevator.getNumber());
        }

        int elevatorNum = elevatorApi.getElevatorNum();
        for(int i = 0; i < elevatorNum; i ++) {

            Elevator elevator = building.getElevator(i);

            if (elevator == null) {
                elevator = createElevator(floors, i);

                List<Elevator> elevators = building.getElevators();
                elevators.add(elevator);

                building.setElevators(elevators);
            }

            if (elevator != null) {
                updateElevator(floors, elevator.getNumber());
            }
        }
    }

    private void updateElevator(List<Floor> floors, int elevatorNumber) throws RemoteException {
        int target = elevatorApi.getTarget(elevatorNumber);
        int elevatorFloor = elevatorApi.getElevatorFloor(elevatorNumber);
        Optional<Floor> targetFloor = floors.stream().filter(f -> f.getNumber() == target).findFirst();
        Optional<Floor> closestFloor = floors.stream().filter(f -> f.getNumber() == elevatorFloor).findFirst();

        if(!targetFloor.isPresent() || !closestFloor.isPresent()) {
            // no floors found - skip this elevator
            return;
        }

        int committedDirection = elevatorApi.getCommittedDirection(elevatorNumber);
        int elevatorAccel = elevatorApi.getElevatorAccel(elevatorNumber);
        int elevatorDoorStatus = elevatorApi.getElevatorDoorStatus(elevatorNumber);
        int elevatorPosition = elevatorApi.getElevatorPosition(elevatorNumber);
        int elevatorSpeed = elevatorApi.getElevatorSpeed(elevatorNumber);
        int weight = elevatorApi.getElevatorWeight(elevatorNumber);
        Position currentPosition = new Position(elevatorPosition, closestFloor.get());

        Elevator elevator = building.getElevator(elevatorNumber);
        updateElevatorButtons(elevator);
        elevator.setCommittedDirection(Direction.fromNumber(committedDirection));
        elevator.setSpeed(elevatorSpeed);
        elevator.setAcceleration(elevatorAccel);
        elevator.setDoorStatus(DoorStatus.fromNumber(elevatorDoorStatus));
        elevator.setTarget(targetFloor.get());
        elevator.setCurrentPosition(currentPosition);
        elevator.setWeight(weight);
        elevator.updated();
    }

    private void update(Observable o, Object arg) {
        if (arg instanceof Elevator) {
            try {
                elevatorApi.setTarget(((Elevator) arg).getNumber(), ((Elevator) arg).getTarget().getNumber());
            } catch (RemoteException e) {
                System.out.println(e);
            }
        }
    }

    private Building createBuilding(List<Floor> floors, List<Elevator> elevators) throws RemoteException {
        int floorHeight = elevatorApi.getFloorHeight();
        return new Building(floorHeight, elevators, floors);
    }

    private List<Elevator> createElevators(List<Floor> floors) throws RemoteException {
        List<Elevator> elevators = new ArrayList<>();
        int elevatorNum = elevatorApi.getElevatorNum();

        for(int i = 0; i < elevatorNum; i ++) {
            Elevator elevator = createElevator(floors, i);
            if (elevator == null) continue;

            elevators.add(elevator);
        }

        return elevators;
    }

    private Elevator createElevator(List<Floor> floors, int elevatorNumber) throws RemoteException {
        int target = elevatorApi.getTarget(elevatorNumber);
        int elevatorFloor = elevatorApi.getElevatorFloor(elevatorNumber);
        Optional<Floor> targetFloor = floors.stream().filter(f -> f.getNumber() == target).findFirst();
        Optional<Floor> closestFloor = floors.stream().filter(f -> f.getNumber() == elevatorFloor).findFirst();

        if(!targetFloor.isPresent() || !closestFloor.isPresent()) {
            // no floors found - skip this elevator
            return null;
        }

        int committedDirection = elevatorApi.getCommittedDirection(elevatorNumber);
        int elevatorAccel = elevatorApi.getElevatorAccel(elevatorNumber);
        int elevatorCapacity = elevatorApi.getElevatorCapacity(elevatorNumber);
        int elevatorDoorStatus = elevatorApi.getElevatorDoorStatus(elevatorNumber);
        int elevatorPosition = elevatorApi.getElevatorPosition(elevatorNumber);
        int elevatorSpeed = elevatorApi.getElevatorSpeed(elevatorNumber);
        int elevatorWeight = elevatorApi.getElevatorWeight(elevatorNumber);
        Position currentPosition = new Position(elevatorPosition, closestFloor.get());

        Elevator elevator = new Elevator(elevatorNumber, elevatorCapacity, elevatorWeight);
        elevator.setCommittedDirection(Direction.fromNumber(committedDirection));
        elevator.setSpeed(elevatorSpeed);
        elevator.setAcceleration(elevatorAccel);
        elevator.setDoorStatus(DoorStatus.fromNumber(elevatorDoorStatus));
        elevator.setTarget(targetFloor.get());
        elevator.setCurrentPosition(currentPosition);
        elevator.addObserver(this::update);
        return elevator;
    }

    private List<Floor> createFloors() throws RemoteException {
        List<Floor> floors = new ArrayList<>();
        int floorNum = elevatorApi.getFloorNum();
        for(int i = 0; i < floorNum; i ++) {
            boolean buttonDown = elevatorApi.getFloorButtonDown(i);
            boolean buttonUp = elevatorApi.getFloorButtonUp(i);

            Floor floor = new Floor(i);
            floor.clearButtonState();

            if(buttonDown) {
                floor.pressDownButton();
            }

            if(buttonUp) {
                floor.pressUpButton();
            }

            floors.add(floor);
        }

        return floors;
    }

    private void addFloorServiceAssignments(List<Elevator> elevators, List<Floor> floors) throws RemoteException {
        for (Floor f : floors) {
            for (Elevator e : elevators) {
                boolean serviced = elevatorApi.getServicesFloors(e.getNumber(), f.getNumber());
                if(serviced) {
                    boolean pressed = elevatorApi.getElevatorButton(e.getNumber(), f.getNumber());
                    e.addFloorService(f, pressed);
                }
            }
        }
    }

    private void updateElevatorButtons(Elevator elevator) throws RemoteException {
        for (Floor floor : elevator.getFloors()) {
            boolean isPressed = elevatorApi.getElevatorButton(elevator.getNumber(), floor.getNumber());
            elevator.setFloorButton(floor, isPressed);
        }
    }
}
