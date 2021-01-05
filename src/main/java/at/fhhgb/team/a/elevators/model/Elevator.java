package at.fhhgb.team.a.elevators.model;

import java.util.*;

/**
 * Represents an elevator used in the system
 */
public class Elevator extends Observable {

    /** The number of the elevator.
     *  Elevator numbering starts at zero for elevator 1. */
    private int number = 0;

    /** The maximum number of passengers that can fit on an elevator. */
    private int capacity = 0;

    private float speed = 0.0f;

    private float acceleration = 0.0f;

    private Position currentPosition;

    private DoorStatus doorStatus = DoorStatus.closed;

    /** The current floor target of the elevator as set by the controller.
     * The elevator will travel to that target and stop until directed to the next target. */
    private Floor target;

    /** The current committed direction of the elevator.
     * Elevators responding to a passenger floor button must have a committed direction, up or down.
     * Otherwise, passengers wishing to go up would board the same elevators as passengers wishing to go down.
     * Accordingly, each elevator can have an up,
     * down or uncommitted committed direction set in response to passenger travel. */
    private Direction committedDirection = Direction.uncommitted;

    /** Contains all serviced Floors {@link Floor} as Key and the state of the
     * Button {@link Boolean} in the Elevator.
     * Every elevator must service the ground floor. */
    private final Map<Floor, Boolean> elevatorButtons = new HashMap<>();

    /** This provides the current weight of the elevator less the weight of the empty elevator –
     * hence the weight of the passengers on board.
     * This can be useful for detecting when the elevator is getting full. */
    private float weight = 0.0f;

    public Elevator(int number,
                    int capacity,
                    float weight) {
        this.number = number;
        this.capacity = capacity;
        this.weight = weight;
    }

    /**
     * Retrieves the distinct number of the elevator.
     * @return distinct number of the elevator
     */
    public int getNumber() {
        return number;
    }

    /**
     * Retrieves the maximum number of passengers that can fit on the elevator.
     * @return maximum number of passengers
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Provides the current speed of the elevator in feet per sec.
     * @return the speed of the elevator where positive speed is up and negative is down
     */
    public float getSpeed() {
        return speed;
    }

    /**
     * Provides the current acceleration of the specified elevator in feet per sec^2.
     * @return the acceleration of the elevator where positive speed is acceleration and negative is deceleration
     */
    public float getAcceleration() {
        return acceleration;
    }

    /**
     * Provides the current position of the elevator.
     * @return the current position of the elevator, both in feet and to the closest floor
     */
    public Position getCurrentPosition() {
        return currentPosition;
    }

    /**
     * Provides the current status of the doors of the specified elevator (open/closed).
     * @return the door status of the elevator
     */
    public DoorStatus getDoorStatus() {
        return doorStatus;
    }

    /**
     * Retrieves the floor target of the elevator.
     * @return current floor target of the elevator
     */
    public Floor getTarget() {
        return target;
    }

    /**
     * Retrieves the committed direction of the specified elevator (up / down / uncommitted).
     * @return the current direction of the specified elevator
     */
    public Direction getCommittedDirection() {
        return committedDirection;
    }

    /**
     * Retrieves the weight of passengers on the elevator.
     * @return total weight of all passengers in lbs
     */
    public float getWeight() {
        return weight;
    }

    /**
     * Provides the status of a floor request button on the elevator (on/off).
     * @param floorNumber - floor number button being checked on the elevator
     * @return returns boolean to indicate if floor button on the elevator is active (true) or not (false)
     */
    public boolean getElevatorButton(int floorNumber) {
        Optional<Floor> floor = elevatorButtons.keySet().stream()
                .filter(f -> f.getNumber() == floorNumber)
                .findFirst();

        if (!floor.isPresent()) {
            return false;
        }

        return elevatorButtons.get(floor.get());
    }

    /**
     * Retrieves whether or not the elevator will service the specified floor (yes/no).
     * @param floorNumber floor whose service status by the elevator is being retrieved
     * @return service status whether the floor is serviced by the elevator (yes=true,no=false)
     */
    public boolean servicesFloor(int floorNumber) {
        return elevatorButtons.keySet().stream()
                .anyMatch(f -> f.getNumber() == floorNumber);
    }

    /**
     * Sets the floor target of the elevator.
     * @param target floor number which the specified elevator is to target
     */
    public void setTarget(Floor target) {
        this.target = target;
        setChanged();
        notifyObservers();
    }

    /**
     * Sets the committed direction of the elevator (up / down / uncommitted). 
     * @param committedDirection direction being set
     */
    public void setCommittedDirection(Direction committedDirection) {
        this.committedDirection = committedDirection;
        setChanged();
        notifyObservers();
    }

    /**
     * Adds a particular floor to be serviced by the elevator.
     * @param floor the floor which should be serviced from the elevator
     * @param pressed the status of the button for the specified floor
     */
    public void addFloorService(Floor floor, boolean pressed) {
        elevatorButtons.put(floor, pressed);
        setChanged();
        notifyObservers();
    }

    public void setSpeed(float speed) {
        this.speed = speed;
        setChanged();
        notifyObservers();
    }

    public void setAcceleration(float acceleration) {
        this.acceleration = acceleration;
        setChanged();
        notifyObservers();
    }

    public void setCurrentPosition(Position currentPosition) {
        this.currentPosition = currentPosition;
        setChanged();
        notifyObservers();
    }

    public void setDoorStatus(DoorStatus doorStatus) {
        this.doorStatus = doorStatus;
        setChanged();
        notifyObservers();
    }

    public List<Floor> getFloors() {
        return new ArrayList<>(elevatorButtons.keySet());
    }
}
