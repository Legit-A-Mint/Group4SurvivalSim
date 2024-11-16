import greenfoot.*;
import java.util.ArrayList;

/**
 * Write a description of class Projectile here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Projectile extends SuperSmoothMover{
    private Actor origin, target; 
    private GreenfootImage img;

    private boolean targetFound = false;

    private ArrayList<Enemy> enemies;
    private double speed;
    private int lifeSpan;
    private Enemy myTarget;

    /**
     * FAILSAFE VERSION, IN CASE MOMVEMENT REPOSITIONING FAILS
     * SWITCH BACK TO SETLOCATION
     * 
    private double speedMulti = 3;
    private double speedX, speedY;
    private int x, y;
     */

    public Projectile(double Speed){
        this.speed = 5;
        this.lifeSpan = 150;
    }

    public void act(){

        if(lifeSpan > 0) lifeSpan--;

        if(myTarget == null){
            targeting();
        }
        else if(myTarget != null){
            if(lifeSpan > 0) move(speed);
            else if(lifeSpan == 0) getWorld().removeObject(this);
            if(this.intersects(myTarget)){
                myTarget.damageMe(1);
                getWorld().removeObject(this);
            } 
        }
    }

    public void targeting (){
        double closestTargetDistance = 0;
        double distanceToActor;
        // Get a list of all Flowers in the World, cast it to ArrayList
        // for easy management

        enemies = (ArrayList<Enemy>)getObjectsInRange(40, Enemy.class);

        int range = 150;
        int maxRange =  500;
        while(enemies.size() == 0 && range <= maxRange){
            if (enemies.size() == 0){
                enemies = (ArrayList<Enemy>)getObjectsInRange(range, Enemy.class);
                range += 50;
            } 
            if (enemies.size() != 0){
                break;
            }
         }
        
        if (enemies.size() > 0)
        {
            // set the first one as my target
            myTarget = enemies.get(0);
            // Use method to get distance to target. This will be used
            // to check if any other targets are closer
            closestTargetDistance = MyWorld.getDistance (this, myTarget);

            // Loop through the objects in the ArrayList to find the closest target
            for (Enemy o : enemies)
            {
                // Cast for use in generic method
                //Actor a = (Actor) o;
                // Measure distance from me
                distanceToActor = MyWorld.getDistance(this, o);
                // If I find a Enemy closer than my current target, I will change
                // targets
                if (distanceToActor < closestTargetDistance)
                {
                    myTarget = o;
                    closestTargetDistance = distanceToActor;
                }
            }

            targetFound = true;
            turnTowards(myTarget);

            //speedX = (((myTarget.getX() - getX())/closestTargetDistance)*speedMulti);
            //speedY = (((myTarget.getY() - getY())/closestTargetDistance)*speedMulti);
        }
    }
}
