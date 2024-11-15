import greenfoot.*;

/**
 * Write a description of class Projectile here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Projectile extends Effects{
    private Actor origin, target; 
    private GreenfootImage img;
    private int targetX, targetY;
    
    private int lifeSpan;
    
    public Projectile(Actor origin, Actor target, int lifeSpan){
        this.origin = origin;
        this.target = target;
        this.lifeSpan = lifeSpan;
        
        img = new GreenfootImage("arrow.png");
        
        img = new GreenfootImage(20, 20);
        img.setColor(Color.BLACK);
        img.fill();
        setImage(img);
        
        targetX = target.getX();
        targetY = target.getY();
        
        this.setLocation(origin.getX() - 50, origin.getY());
        
    }
    
    public Projectile(Actor origin){
        this.origin = origin;
        this.lifeSpan = -1;
        
        img = new GreenfootImage(20, 20);
        img.setColor(Color.BLACK);
        img.fill();
        setImage(img);
        
        img = new GreenfootImage("arrow.png");
        setImage(img);
        
        this.setLocation(origin.getX(), origin.getY());
        
        /*
        this.setLocation(getWorld().getObjects(ScrollableWorld.class).get(0).getX() + relativeX, 
            getWorld().getObjects(ScrollableWorld.class).get(0).getY() + relativeY);*/
    }
    
    public void act(){
        this.setLocation(origin.getX(), origin.getY());
        if(lifeSpan == -1){
            moveToTarget();
        }
        if(lifeSpan > 0){
            lifeSpan--;
            moveToTarget();
        }
        if(lifeSpan == 0){
            //getWorld().removeObject(this);
        }
    }

    private void moveToTarget(){
        if(target != null){
            turnTowards(targetX, targetY);  
            move(1);
        }
        else{
            turnTowards(500, 500);
            move(1);
        }
    }
}
