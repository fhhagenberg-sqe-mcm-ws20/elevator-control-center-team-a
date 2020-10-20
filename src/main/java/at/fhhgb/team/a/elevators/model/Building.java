package at.fhhgb.team.a.elevators.model;

import java.util.ArrayList;

public class Building {

    /** The floors in the building, including the ground floor.
     * It is assumed there are no floors below ground level. */
    private ArrayList<Floor> floors = new ArrayList<>();

    /** The elevators in the building.
     * Elevator numbering starts at zero for elevator 1. */
    private ArrayList<Elevator> elevators = new ArrayList<>();

    public Building() {

    }

    public Building(ArrayList<Floor> floors,
                    ArrayList<Elevator> elevators) {
        this.floors = floors;
        this.elevators = elevators;
    }

    /**
     * Retrieves the floors in the building.
     * @return the floors
     */
    public ArrayList<Floor> getFloors() {
        return floors;
    }

    /**
     * Retrieves the elevators in the building.
     * @return the elevators
     */
    public ArrayList<Elevator> getElevators() {
        return elevators;
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
                .orElse(new Elevator());
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
                .orElse(new Floor());
    }

    public void setFloors(ArrayList<Floor> floors) {
        this.floors = floors;
    }

    public void setElevators(ArrayList<Elevator> elevators) {
        this.elevators = elevators;
    }
}
