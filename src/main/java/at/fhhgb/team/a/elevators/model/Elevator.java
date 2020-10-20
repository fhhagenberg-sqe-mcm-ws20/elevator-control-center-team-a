package at.fhhgb.team.a.elevators.model;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Represents an elevator used in the system
 */
public class Elevator {

    /** The number of the elevator.
     *  Elevator numbering starts at zero for elevator 1. */
    private int number = 0;

    /** The maximum number of passengers that can fit on an elevator. */
    private int capacity = 0;

    /** The current speed of the elevator.
     * Positive speed indicates an elevator heading up
     * while negative indicates an elevator going down. */
    private float speed = 0.0f;

    /** The current acceleration of the elevators. */
    private float acceleration = 0.0f;

    /** The current position of the elevator, both in feet and to the closest floor. */
    private Position currentPosition = new Position();

    /** Whether the doors for an elevator are open or closed.
     * The status may also indicate a transition between open or closed. */
    private DoorStatus doorStatus = DoorStatus.closed;

    /** The current floor target of the elevator as set by the controller.
     * The elevator will travel to that target and stop until directed to the next target. */
    private Floor target = Building.GROUND_FLOOR;

    /** The current committed direction of the elevator.
     * Elevators responding to a passenger floor button must have a committed direction, up or down.
     * Otherwise, passengers wishing to go up would board the same elevators as passengers wishing to go down.
     * Accordingly, each elevator can have an up,
     * down or uncommitted committed direction set in response to passenger travel. */
    private Direction committedDirection = Direction.uncommitted;

    /** Whether a particular elevator services a particular floor.
     * When elevators are allowed to only service certain floors,
     * this can help to achieve greater passenger service.
     * Every elevator must service the ground floor. */
    private Set<Floor> servicedFloors = new HashSet<>();

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
        Floor floor = servicedFloors.stream()
                .filter(f -> f.getNumber() == floorNumber)
                .findFirst()
                .orElse(Building.GROUND_FLOOR);

        return floor.isDownButtonOn() || floor.isUpButtonOn();
    }

    /**
     * Retrieves whether or not the elevator will service the specified floor (yes/no).
     * @param floorNumber floor whose service status by the elevator is being retrieved
     * @return service status whether the floor is serviced by the elevator (yes=true,no=false)
     */
    public boolean servicesFloor(int floorNumber) {
        Optional<Floor> floor = servicedFloors.stream()
                .filter(f -> f.getNumber() == floorNumber)
                .findFirst();

        return floor.isPresent();
    }

    /**
     * Sets the floor target of the elevator.
     * @param target floor number which the specified elevator is to target
     */
    public void setTarget(Floor target) {
        this.target = target;
    }

    /**
     * Sets the committed direction of the elevator (up / down / uncommitted). 
     * @param committedDirection direction being set
     */
    public void setCommittedDirection(Direction committedDirection) {
        this.committedDirection = committedDirection;
    }

    /**
     * Adds a particular floor to be serviced by the elevator.
     * @param floor the flor which should be serviced from the elevator
     */
    public void addFloorService(Floor floor) {
        servicedFloors.add(floor);
    }
}
