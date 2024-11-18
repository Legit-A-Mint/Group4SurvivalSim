import greenfoot.*; // (World, Actor, GreenfootImage, Greenfoot, and MouseInfo)

public class Node extends SuperSmoothMover {
    private int x, y; // Grid coordinates
    private boolean walkable; // Indicates if this node can be traversed
    private Node parent; // Used for reconstructing paths

    public Node(int x, int y, boolean walkable) {
        this.x = x;
        this.y = y;
        this.walkable = walkable;
    }

    public void setWalkable(boolean walkable) {
        this.walkable = walkable;
    }

    public boolean isWalkable() {
        return walkable;
    }

    public int getXCoord() {
        return x;
    }

    public int getYCoord() {
        return y;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public Node getParent() {
        return parent;
    }

    public void act() {
        // Visualization or debugging (e.g., change color to show path)
    }
}
