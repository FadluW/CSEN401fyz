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

    
    public static Direction directionOf(String d) {
        switch (d.toLowerCase()) {
            case "up": return Direction.UP;
            case "down": return Direction.DOWN;
            case "left": return Direction.LEFT;
            case "right": return Direction.RIGHT;
        }
        return null;
    }
}
