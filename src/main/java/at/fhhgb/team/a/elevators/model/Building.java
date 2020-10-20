package at.fhhgb.team.a.elevators.model;

import java.util.HashSet;
import java.util.Set;

public class Building {

    /** The height of all floors. */
    public static float FLOOR_HEIGHT = 500;

    /** The ground floor of the building. */
    public final static Floor GROUND_FLOOR = new Floor(0);

    /** The floors in the building, including the ground floor.
     * It is assumed there are no floors below ground level. */
    private Set<Floor> floors = new HashSet<>();

    /** The elevators in the building.
     * Elevator numbering starts at zero for elevator 1. */
    private Set<Elevator> elevators = new HashSet<>();

    public Building(float floorHeight,
                    Set<Floor> floors,
                    Set<Elevator> elevators) {
        FLOOR_HEIGHT = floorHeight;

        this.floors = floors;
        this.elevators = elevators;
    }

    /**
     * Retrieves the number of floors in the building.
     * @return total number of floors
     */
    public int getFloorNum() {
        return floors.size();
    }

    /**
     * Retrieves the number of elevators in the building.
     * @return total number of elevators
     */
    public int getElevatorNum() {
        return elevators.size();
    }

    /**
     * Retrieves the elevator with the specific number.
     * @param elevatorNumber the number of the elevator which is searched
     * @return the elevator with the specified number
     */
    public Elevator getElevator(int elevatorNumber) {
        return elevators
                .stream()
                .filter(e -> e.getNumber() == elevatorNumber)
                .findFirst()
                .orElse(null);
    }

    /**
     * Retrieves the floor with the specific number.
     * @param floorNumber the number of the floor which is searched
     * @return the floor with the specified number
     */
    public Floor getFloor(int floorNumber) {
        return floors
                .stream()
                .filter(f -> f.getNumber() == floorNumber)
                .findFirst()
                .orElse(null);
    }
}
