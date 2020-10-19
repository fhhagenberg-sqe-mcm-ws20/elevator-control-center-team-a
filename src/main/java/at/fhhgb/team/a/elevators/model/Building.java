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

    /**
     * Retrieves the floors in the building.
     * @return the floors
     */
    public ArrayList<Floor> getFloors() {
        return floors;
    }

    public void setFloors(ArrayList<Floor> floors) {
        this.floors = floors;
    }

    /**
     * Retrieves the elevators in the building.
     * @return the elevators
     */
    public ArrayList<Elevator> getElevators() {
        return elevators;
    }

    public void setElevators(ArrayList<Elevator> elevators) {
        this.elevators = elevators;
    }
}
