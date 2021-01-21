package at.fhhgb.team.a.elevators.auto;

import at.fhhgb.team.a.elevators.model.*;

import java.util.Comparator;
import java.util.Optional;
import java.util.function.Predicate;

public class AutoModeExecutor implements EccModeExecutor {
    @Override
    public void execute(Building building) {
        building.getElevators().forEach(this::setNewTarget);
    }

    private void setNewTarget(Elevator elevator) {
        var currentFloor = elevator.getCurrentPosition().getClosestFloor();
        var targetFloor = elevator.getTarget();
        var doorStatus = elevator.getDoorStatus();

        if(currentFloor != targetFloor || doorStatus != DoorStatus.open) {
            // elevator still moving or door not open yet
            return;
        }

        int minimumFloorNumber = elevator.getFloors().stream().mapToInt(Floor::getNumber).min().orElse(currentFloor.getNumber());
        int maximumFloorNumber = elevator.getFloors().stream().mapToInt(Floor::getNumber).max().orElse(currentFloor.getNumber());

        if(currentFloor.getNumber() == minimumFloorNumber) {
            // lowest floor -> go up
            setTargetToNextAvailableFloorUp(elevator, currentFloor.getNumber());
        } else if(currentFloor.getNumber() == maximumFloorNumber) {
            // highest floor -> go down
            setTargetToNextAvailableFloorDown(elevator, currentFloor.getNumber());
        } else {
            // in between -> continue last committed direction
            if(elevator.getCommittedDirection() == Direction.up) {
                setTargetToNextAvailableFloorUp(elevator, currentFloor.getNumber());
            } else {
                setTargetToNextAvailableFloorDown(elevator, currentFloor.getNumber());
            }
        }
    }

    private void setTargetToNextAvailableFloorUp(Elevator elevator, int currentFloorNumber) {
        setTargetToNextAvailableFloor(elevator, Comparator.comparingInt(Floor::getNumber), Direction.up, floor -> floor.getNumber() > currentFloorNumber);
    }

    private void setTargetToNextAvailableFloorDown(Elevator elevator, int currentFloorNumber) {
        setTargetToNextAvailableFloor(elevator, Comparator.comparingInt(Floor::getNumber).reversed(), Direction.down, floor -> floor.getNumber() < currentFloorNumber );
    }

    private void setTargetToNextAvailableFloor(Elevator elevator, Comparator<Floor> comparator, Direction direction, Predicate<Floor> filter) {
        Optional<Floor> nextFloor = elevator.getFloors().stream()
                .filter(filter)
                .sorted(comparator)
                .findFirst();

        if (nextFloor.isPresent()) {
            elevator.setTarget(nextFloor.get());
            elevator.setCommittedDirection(direction);
        }
    }
}
