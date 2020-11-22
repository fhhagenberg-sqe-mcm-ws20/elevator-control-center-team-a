package at.fhhgb.team.a.elevators.model;

import javafx.scene.image.Image;

public enum Direction {
    up(0, new Image("images/up-arrow.png")),
    down(1, new Image("images/down-arrow.png")),
    uncommitted(2, new Image("images/right-arrow.png"));

    public final int number;
    public final Image image;

    Direction(int number, Image image) {
        this.number = number;
        this.image = image;
    }

    /**
     * Provides the direction of an elevator (up / down / uncommitted).
     * @param number the flag number of the direction
     * @return the corresponding direction
     */
    public static Direction fromNumber(int number) {
        switch (number) {
            case 0:
                return up;
            case 1:
                return down;
            case 2:
                return uncommitted;
        }
        return null;
    }
}
