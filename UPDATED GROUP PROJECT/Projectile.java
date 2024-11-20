import greenfoot.*;
import java.util.ArrayList;

public class Projectile extends Effects {
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
    protected int lifeSpan;
    protected int damage;
    protected int fadeLength;

    // Constructor to initialize default values
    public Projectile() {
        speed = 6;
        lifeSpan = 350;
        damage = 1000;
        fadeLength = 100;
    }

    public void act() {
        if (SimulationWorld.isActing()) {
            if (lifeSpan > 0) lifeSpan--; // Decrease lifespan
            fade(this, lifeSpan, fadeLength); // Handle fade effect

            // If the target is set, move towards it
            if (target != null) {
                moveTowardsTarget();
            } 
            // If no target, try to find an enemy to target
            else {
                targeting();
            }

            // Remove the projectile when its lifespan ends
            if (lifeSpan == 0) {
                getWorld().removeObject(this);
            }
        }
    }

    // Method to set the target directly (e.g., called from Player)
    public void setTarget(Actor target) {
        this.target = target;
    }

    // Move the projectile towards the current target
    public void moveTowardsTarget() {
        if (target != null) {
            turnTowards(target);
            move(speed); // Move the projectile in the direction of the target
            checkHit(); // Check if it hits an enemy
        }
    }

    // Search for the nearest enemy in range and set it as the target
    public void targeting() {
        double closestTargetDistance = 0;
        double distanceToActor;

        enemies = (ArrayList<Enemy>) getObjectsInRange(500, Enemy.class); // Get all enemies within range
        if (enemies.size() > 0) {
            // Find the closest enemy
            enemy = enemies.get(0);
            closestTargetDistance = SimulationWorld.getDistance(this, enemy);

            for (Enemy o : enemies) {
                distanceToActor = SimulationWorld.getDistance(this, o);
                if (distanceToActor < closestTargetDistance) {
                    enemy = o; // Set the closer enemy as the target
                    closestTargetDistance = distanceToActor;
                }
            }

            // If a target is found, start moving towards it
            target = enemy;
            targetFound = true;
            turnTowards(enemy); // Adjust orientation towards the target
        }
    }

    // Check if the projectile hits the target
    public void checkHit() {
        if (target != null) {
            if (this.intersects(target)) { // Check if the projectile collides with the target
                if (target instanceof Enemy) {
                    hitEnemy = (Enemy) target;
                    hitEnemy.damageMe(damage); // Damage the enemy
                }
                getWorld().removeObject(this); // Remove the projectile after hitting the target
            }
        }
    }
}
