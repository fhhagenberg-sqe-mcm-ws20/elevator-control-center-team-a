package at.fhhgb.team.a.elevators.app;

import sqelevator.IElevator;
import at.fhhgb.team.a.elevators.model.*;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ElevatorControlCenter {

    /**
     * Elevator that is controlled by this center
     */
    private IElevator elevatorApi;

    /**
     * The building used in the elevator control center.
     */
    private Building building;

    public ElevatorControlCenter(IElevator elevatorApi) {
        this.elevatorApi = elevatorApi;
    }

    public void pollElevatorApi() throws RemoteException {
        long startTick = elevatorApi.getClockTick();
        List<Floor> floors = createFloors();
        List<Elevator> elevators = createElevators(floors);
        addFloorServiceAssignments(elevators, floors);
        long endTick = elevatorApi.getClockTick();

        if(startTick == endTick) {
            if(building == null) {
                building = createBuilding(floors, elevators);
            } else {
                updateBuilding(floors, elevators);
            }
        } else {
            // ClockTick changed --> retry
            pollElevatorApi();
        }
    }

    public void updateBuilding(List<Floor> floors, List<Elevator> elevators) {
        building.setFloors(floors);
        building.setElevators(elevators);
    }

    public Building getBuilding() {
        return this.building;
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

        Elevator elevator = new Elevator(elevatorNumber, elevatorCapacity, elevatorWeight, targetFloor.get(), currentPosition);
        elevator.setCommittedDirection(Direction.fromNumber(committedDirection));
        elevator.setSpeed(elevatorSpeed);
        elevator.setAcceleration(elevatorAccel);
        elevator.setDoorStatus(DoorStatus.fromNumber(elevatorDoorStatus));
        return elevator;
    }

    private List<Floor> createFloors() throws RemoteException {
        List<Floor> floors = new ArrayList<>();
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

    private void addFloorServiceAssignments(List<Elevator> elevators, List<Floor> floors){
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
