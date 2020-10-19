package at.fhhgb.team.a.elevators.model;

import java.util.ArrayList;

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

    /** Which floor buttons have been pressed. */
    private ArrayList<Button> pressedButtons = new ArrayList<>();

    /** The current position of the elevator, both in feet and to the closest floor. */
    private Position currentPosition = new Position();

    /** Whether the doors for an elevator are open or closed.
     * The status may also indicate a transition between open or closed. */
    private DoorStatus doorStatus = DoorStatus.closed;

    /** The current floor target of the elevator as set by the controller.
     * The elevator will travel to that target and stop until directed to the next target. */
    private Floor target = new Floor();

    /** The current committed direction of the elevator.
     * Elevators responding to a passenger floor button must have a committed direction, up or down.
     * Otherwise, passengers wishing to go up would board the same elevators as passengers wishing to go down.
     * Accordingly, each elevator can have an up,
     * down or uncommitted committed direction set in response to passenger travel. */
    private Direction committedDirection = Direction.uncommitted;

    /** Whether a particular elevator services a particular floor.
     * When elevators are allowed to only service certain floors,
     * this can help to achieve greater passenger service. Every elevator must service the ground floor. */
    private ArrayList<Floor> floorService = new ArrayList<>();

    /** This provides the current weight of the elevator less the weight of the empty elevator –
     * hence the weight of the passengers on board.
     * This can be useful for detecting when the elevator is getting full. */
    private float weight = 0.0f;

    public Elevator() {

    }

    /**
     * Sets the floor target of a particular elevator.
     * Typically, this target is set in response to a given passenger selecting a floor up or down button,
     * or selecting a floor button in an elevator.
     * The elevator will travel to that target and stop until directed to the next target.
     * @param target the floor which should be targeted from the elevator
     */
    public void setTarget(Floor target) {
        this.target = target;
    }

    /**
     * Sets the current committed direction of the elevator.
     * This is set so that passengers board elevators going in their desired direction.
     * This is indicated by the controller by whether the up or down floor button has been pressed on a given floor.
     * This is an important concept and it should be recognized that in some cases,
     * an elevator may be going down, for example, but its committed direction is up –
     * reflecting the fact it has been dispatched down to pick up a passenger who is traveling up.
     * Whenever an elevator is being dispatched to a target floor, it should have a committed direction set.
     * @param committedDirection the direction which should be committed to the elevator
     */
    public void setCommittedDirection(Direction committedDirection) {
        this.committedDirection = committedDirection;
    }

    /**
     * Sets whether a particular elevator services a particular floor.
     * In larger buildings, it is typically better to set elevator service to a particular subset of floors.
     * Note that the ground floor will always get service.
     * @param floorService the list of floors which should be serviced from the elevator
     */
    public void setFloorService(ArrayList<Floor> floorService) {
        this.floorService = floorService;
    }
}
