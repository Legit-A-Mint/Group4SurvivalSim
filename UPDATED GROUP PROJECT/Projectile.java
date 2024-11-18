import greenfoot.*;
import java.util.ArrayList;

/**
 * Write a description of class Projectile here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Projectile extends SuperSmoothMover{
    protected Actor origin, target; 
    protected GreenfootImage img;
    protected boolean targetFound = false;

    // Other classes
    protected ArrayList<Enemy> enemies;
    protected Enemy enemy;
    protected EnemyHitbox enemyHitbox;
    
    // Inherited Instance variables
    protected double speed;
    protected int lifeSpan;
    protected int damage;
    
    /** temp constructor to do testing with */
    // Please use contructors in subclasses in real version
    public Projectile(){
        speed = 6;
        lifeSpan = 150;
        damage = 1;
    }
    
    public void act(){
        if(lifeSpan > 0) lifeSpan--;

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
                
                    if(this.intersects(enemy.getHitbox())){
                        enemy.damageMe(damage);
                        getWorld().removeObject(this);
                    }
                }catch(Exception e){
                    
                }
            }
        }
        
        move(speed);
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
            enemy = enemies.get(0);
            // Use method to get distance to target. This will be used
            // to check if any other targets are closer
            closestTargetDistance = MyWorld.getDistance (this, enemy);

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
