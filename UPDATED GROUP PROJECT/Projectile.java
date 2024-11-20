import greenfoot.*;
import java.util.ArrayList;

/**
 * Write a description of class Projectile here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Projectile extends Effects{
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

    public Projectile(String img){
        speed = 6;
        lifeSpan = 350;
        damage = 1000;
        
        this.img = new GreenfootImage(img);

        fadeLength = 100;
    }

    public void act(){
        if (SimulationWorld.isActing())
        {
            if(lifeSpan > 0) lifeSpan--;

            fade(this, lifeSpan, fadeLength);

            if(enemy == null){
                targeting();
            }
            else if(enemy != null){
                if(lifeSpan > 0) move(speed);
                else if(lifeSpan == 0){ 
                    getWorld().removeObject(this);
                    return;
                }

                if(enemy.getHitbox() != null){
                    try{
                        if(getOneIntersectingObject(Enemy.class) != null){
                            hitEnemy = (Enemy) getOneIntersectingObject(Enemy.class);
                            hitEnemy.damageMe(damage);
                            getWorld().removeObject(this);
                        }
                        /**
                        if(this.intersects(enemy.getHitbox())){
                        enemy.damageMe(damage);
                        getWorld().removeObject(this);
                        }
                         */
                    }catch(Exception e){

                    }
                }
            }

            move(speed);
        }
    }

    public void targeting (){
        double closestTargetDistance = 0;
        double distanceToActor;

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

            //speedX = (((enemy.getX() - getX())/closestTargetDistance)*speedMulti);
            //speedY = (((enemy.getY() - getY())/closestTargetDistance)*speedMulti);
        }
    }
}
