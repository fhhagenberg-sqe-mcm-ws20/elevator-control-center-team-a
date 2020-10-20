package at.fhhgb.team.a.elevators.app;

import at.fhhgb.team.a.elevators.api.IElevator;
import at.fhhgb.team.a.elevators.model.Building;
import at.fhhgb.team.a.elevators.model.Direction;
import at.fhhgb.team.a.elevators.model.Elevator;
import at.fhhgb.team.a.elevators.model.Floor;

import java.rmi.RemoteException;
import java.time.Clock;

public class ElevatorControlCenter implements IElevator {

    /**
     * The building used in the elevator control center.
     * The building itself has floors and elevators.
     */
    private Building building = new Building();

    /**
     * The clock of the elevator control center.
     */
    private Clock clock = Clock.systemUTC();

    public ElevatorControlCenter() {

    }

    public ElevatorControlCenter(Building building) {
        this.building = building;
    }

    @Override
    public int getCommittedDirection(int elevatorNumber) throws RemoteException {
        Elevator elevator = building.getElevator(elevatorNumber);
        return elevator.getCommittedDirection()
                .number;
    }

    @Override
    public int getElevatorAccel(int elevatorNumber) throws RemoteException {
        Elevator elevator = building.getElevator(elevatorNumber);
        return (int) elevator.getAcceleration();
    }

    @Override
    public boolean getElevatorButton(int elevatorNumber, int floorNumber) throws RemoteException {
        Elevator elevator = building.getElevator(elevatorNumber);
        return elevator.getElevatorButton(floorNumber);
    }

    @Override
    public int getElevatorDoorStatus(int elevatorNumber) throws RemoteException {
        Elevator elevator = building.getElevator(elevatorNumber);
        return elevator.getDoorStatus()
                .number;
    }

    @Override
    public int getElevatorFloor(int elevatorNumber) throws RemoteException {
        Elevator elevator = building.getElevator(elevatorNumber);
        return elevator.getCurrentPosition()
                .getClosestFloor()
                .getNumber();
    }

    @Override
    public int getElevatorNum() throws RemoteException {
        return building.getElevators().size();
    }

    @Override
    public int getElevatorPosition(int elevatorNumber) throws RemoteException {
        Elevator elevator = building.getElevator(elevatorNumber);
        return (int) elevator.getCurrentPosition()
                .getPositionFeet();
    }

    @Override
    public int getElevatorSpeed(int elevatorNumber) throws RemoteException {
        Elevator elevator = building.getElevator(elevatorNumber);
        return (int) elevator.getSpeed();
    }

    @Override
    public int getElevatorWeight(int elevatorNumber) throws RemoteException {
        Elevator elevator = building.getElevator(elevatorNumber);
        return (int) elevator.getWeight();
    }

    @Override
    public int getElevatorCapacity(int elevatorNumber) throws RemoteException {
        Elevator elevator = building.getElevator(elevatorNumber);
        return elevator.getCapacity();
    }

    @Override
    public boolean getFloorButtonDown(int floorNumber) throws RemoteException {
        Floor floor = building.getFloor(floorNumber);
        return floor.getDownButton()
                .isOn();
    }

    @Override
    public boolean getFloorButtonUp(int floorNumber) throws RemoteException {
        Floor floor = building.getFloor(floorNumber);
        return floor.getUpButton()
                .isOn();
    }

    @Override
    public int getFloorHeight() throws RemoteException {
        Floor firstFloor = building.getFloor(0);
        return (int) firstFloor.getHeight();
    }

    @Override
    public int getFloorNum() throws RemoteException {
        return building.getFloors().size();
    }

    @Override
    public boolean getServicesFloors(int elevatorNumber, int floorNumber) throws RemoteException {
        Elevator elevator = building.getElevator(elevatorNumber);
        return elevator.servicesFloor(floorNumber);
    }

    @Override
    public int getTarget(int elevatorNumber) throws RemoteException {
        Elevator elevator = building.getElevator(elevatorNumber);
        return elevator.getTarget()
                .getNumber();
    }

    @Override
    public void setCommittedDirection(int elevatorNumber, int direction) throws RemoteException {
        Elevator elevator = building.getElevator(elevatorNumber);

        Direction dir = Direction.fromNumber(direction);
        elevator.setCommittedDirection(dir);
    }

    @Override
    public void setServicesFloors(int elevatorNumber, int floorNumber, boolean service) throws RemoteException {
        Elevator elevator = building.getElevator(elevatorNumber);

        if (service) {
            Floor floor = building.getFloor(floorNumber);
            elevator.addFloorService(floor);
        }
    }

    @Override
    public void setTarget(int elevatorNumber, int target) throws RemoteException {
        Elevator elevator = building.getElevator(elevatorNumber);

        Floor floor = building.getFloor(target);
        elevator.setTarget(floor);
    }

    @Override
    public long getClockTick() throws RemoteException {
        return clock.millis();
    }
}
