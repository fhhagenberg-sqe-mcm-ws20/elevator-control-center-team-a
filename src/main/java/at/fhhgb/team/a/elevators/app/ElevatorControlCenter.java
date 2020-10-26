package at.fhhgb.team.a.elevators.app;

import sqelevator.IElevator;
import at.fhhgb.team.a.elevators.model.*;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.Clock;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class ElevatorControlCenter {

    /**
     * Elevator that is controlled by this center
     */
    private IElevator elevatorApi;

    /**
     * The building used in the elevator control center.
     */
    private Building building;

    /**
     * The clock tick of the elevator control center.
     */
    private long clockTick;

    public ElevatorControlCenter(IElevator elevatorApi) {
        this.elevatorApi = elevatorApi;
    }

    public void pollElevatorApi() throws RemoteException {
        this.clockTick = elevatorApi.getClockTick();
        updateBuilding();
    }

    public Building getBuilding() {
        return this.building;
    }

    private void updateBuilding() throws RemoteException {
        int floorHeight = elevatorApi.getFloorHeight();
        Set<Floor> floors = createFloors();
        Set<Elevator> elevators = createElevators(floors);
        addFloorServiceAssignments(elevators, floors);
        Building building = new Building(floorHeight, elevators, floors);
        this.building = building;
    }

    private Set<Elevator> createElevators(Set<Floor> floors) throws RemoteException {
        Set<Elevator> elevators = new HashSet<>();
        int elevatorNum = elevatorApi.getElevatorNum();

        for(int i = 0; i < elevatorNum; i ++) {
            Elevator elevator = createElevator(floors, i);
            if (elevator == null) continue;

            elevators.add(elevator);
        }

        return elevators;
    }

    private Elevator createElevator(Set<Floor> floors, int elevatorNumber) throws RemoteException {
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

        Elevator elevator = new Elevator(elevatorNumber, elevatorCapacity, elevatorWeight, targetFloor.get(), currentPosition);
        elevator.setCommittedDirection(Direction.fromNumber(committedDirection));
        elevator.setSpeed(elevatorSpeed);
        elevator.setAcceleration(elevatorAccel);
        elevator.setDoorStatus(DoorStatus.fromNumber(elevatorDoorStatus));
        return elevator;
    }

    private Set<Floor> createFloors() throws RemoteException {
        Set<Floor> floors = new HashSet<>();
        int floorNum = elevatorApi.getFloorNum();
        for(int i = 0; i < floorNum; i ++) {
            boolean buttonDown = elevatorApi.getFloorButtonDown(i);
            boolean buttonUp = elevatorApi.getFloorButtonUp(i);

            Floor floor = new Floor(i);

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

    private void addFloorServiceAssignments(Set<Elevator> elevators, Set<Floor> floors){
        floors.stream().forEach(f -> {
            elevators.stream().forEach(e -> {
                try {
                    boolean serviced = elevatorApi.getServicesFloors(e.getNumber(), f.getNumber());
                    if(serviced) {
                        boolean pressed = elevatorApi.getElevatorButton(e.getNumber(), f.getNumber());
                        e.addFloorService(f, pressed);
                    }
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }
            });
        });
    }
}
