package model.world;

public enum Direction {
    RIGHT, LEFT, UP, DOWN;

    public String toString() {
        switch (this) {
            case DOWN:
                return "Down";
            case LEFT:
                return "Left";
            case RIGHT:
                return "Right";
            case UP:
                return "Up";
            default:
                return "";
        }
    }
}
