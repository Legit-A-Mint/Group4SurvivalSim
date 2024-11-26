import greenfoot.*;
import java.util.ArrayList;

/**
 * Write a description of class Projectile here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class Projectile extends Effects{
    protected Actor origin, target; 
    protected GreenfootImage img;
    protected boolean targetFound = false;

    // Other classes
    protected ArrayList<Enemy> enemies;
    protected Enemy enemy;
    protected Enemy hitEnemy;
    protected EnemyHitbox enemyHitbox;

    // Inherited Instance variables
    protected double speed;
    protected int damage;
    protected int attackSpeed;
    protected int lifeSpan;

    private int fadeLength;
    protected boolean removeMe;

    public Projectile(){
        removeMe = false;
        fadeLength = 100;
    }

    public abstract void hitSomething();

    public void act(){
        if (SimulationWorld.isActing())
        {
            if(lifeSpan > 0) lifeSpan--;

            fade(this, lifeSpan, fadeLength);

            if(enemy == null){
                targeting();
            }
            else if(enemy != null){
                if(lifeSpan > 0){
                    move(speed);
                }
                else if(lifeSpan <= 0){ 
                    removeMe = true;
                    return;
                }
            }
            move(speed);
        }
        if(removeMe){
            getWorld().removeObject(this);
        }
    }

    public void targeting (){
        double closestTargetDistance = 0;
        double distanceToActor;

        enemies = (ArrayList<Enemy>)getObjectsInRange(40, Enemy.class);

        int range = 150;
        int maxRange =  1250;
        while(enemies.size() == 0 && range <= maxRange){
            if (enemies.size() == 0){
                enemies = (ArrayList<Enemy>)getObjectsInRange(range, Enemy.class);
                range += 200;
            } 
            if (enemies.size() != 0){
                break;
            }
        }

        if (enemies.size() > 0)
        {
            // set the first one as my target
            enemy = enemies.get(0);
            // Use method to get distance to target. This will be used
            // to check if any other targets are closer
            closestTargetDistance = SimulationWorld.getDistance (this, enemy);

            // Loop through the objects in the ArrayList to find the closest target
            for (Enemy o : enemies)
            {
                // Cast for use in generic method
                //Actor a = (Actor) o;
                // Measure distance from me
                distanceToActor = SimulationWorld.getDistance(this, o);
                // If I find a Enemy closer than my current target, I will change
                // targets
                if (distanceToActor < closestTargetDistance)
                {
                    enemy = o;
                    closestTargetDistance = distanceToActor;
                }
            }

            targetFound = true;
            turnTowards(enemy);
        }
    }

    public void markForRemoval(){
        removeMe = true;
    }

    public int getWeaponCooldown(){
        return attackSpeed;
    }
}
