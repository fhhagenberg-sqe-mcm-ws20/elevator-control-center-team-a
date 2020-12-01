package at.fhhgb.team.a.elevators.model;

import java.util.List;

public class Building {

    /** The height of all floors. */
    public static float FLOOR_HEIGHT = 500;

    public void setFloors(List<Floor> floors) {
        this.floors = floors;
    }

    public void setElevators(List<Elevator> elevators) {
        this.elevators = elevators;
    }

    /** The floors in the building, including the ground floor.
     * It is assumed there are no floors below ground level. */
    private List<Floor> floors;

    /** The elevators in the building.
     * Elevator numbering starts at zero for elevator 1. */
    private List<Elevator> elevators;

    public Building(float floorHeight,
                    List<Elevator> elevators,
                    List<Floor> floors) {
        FLOOR_HEIGHT = floorHeight;

        this.elevators = elevators;
        this.floors = floors;
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

    public List<Elevator> getElevators() {
        return elevators;
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

    public List<Floor> getFloors() {
        return floors;
    }
}
