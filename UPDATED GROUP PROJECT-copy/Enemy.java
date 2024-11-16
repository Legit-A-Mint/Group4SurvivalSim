import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

/**
 * Write a description of class Enemies here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Enemy extends Effects
{
    protected int direction;
    protected double speed;
    protected Player player;

    public Enemy(){
        speed = 1;
        direction = 1;
    }

    public void act()
    {
        lookForTarget();
        repel();
    }

    public void lookForTarget(){
        if(!getWorld().getObjects(Player.class).isEmpty()){
            player = getWorld().getObjects(Player.class).get(0);
            turnTowards(player.getX(), player.getY());
            move(speed);
        }
    }

    //modified repel method
    public void repel() {
        ArrayList<Enemy> enemies = (ArrayList<Enemy>)getIntersectingObjects(Enemy.class);
        ArrayList<Actor> actorsTouching = new ArrayList<Actor>();

        for (Enemy e : enemies) actorsTouching.add(e);
        pushAwayFromObjects(actorsTouching, 4);
    }

    /**
     * New repel method! Seems to work well. Can be used in both directions, but for now
     * commented out movement on x so players are only "repelled" in a y-direction.
     * 
     * @author Mr Cohen
     * @since February 2023
     */
    public void pushAwayFromObjects(ArrayList<Actor> nearbyObjects, double minDistance) {
        // Get the current position of this actor
        int currentX = getX();
        int currentY = getY();

        // Iterate through the nearby objects
        for (Actor object : nearbyObjects) {
            // Get the position and bounding box of the nearby object
            int objectX = object.getX();
            int objectY = object.getY();
            int objectWidth = object.getImage().getWidth();
            int objectHeight = object.getImage().getHeight();

            // Calculate the distance between this actor and the nearby object's bounding oval
            double distance = Math.sqrt(Math.pow(currentX - objectX, 2) + Math.pow(currentY - objectY, 2));

            // Calculate the effective radii of the bounding ovals
            double thisRadius = Math.max(getImage().getWidth() / 4.0, getImage().getHeight() / 4.0);
            double objectRadius = Math.max(objectWidth / 4.0, objectHeight / 4.0);

            // Check if the distance is less than the sum of the radii
            if (distance < (thisRadius + objectRadius + minDistance)) {
                // Calculate the direction vector from this actor to the nearby object
                int deltaX = objectX - currentX;
                int deltaY = objectY - currentY;

                // Calculate the unit vector in the direction of the nearby object
                double length = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
                double unitX = deltaX / length;
                double unitY = deltaY / length;

                // Calculate the amount by which to push the nearby object
                double pushAmount = (thisRadius + objectRadius + minDistance) - distance;

                // Update the position of the nearby object to push it away

                // 2d version, allows pushing on x and y axis, commented out for now but it works, just not the
                // effect I'm after:
                object.setLocation(objectX + (int)(pushAmount * unitX), objectY + (int)(pushAmount * unitY));
            }
        }
    }

}
