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
    private int targetRelX, targetRelY;
    private int targetX, targetY;
    private int myX, myY;
    private int lifeSpan;
    private boolean setTarget = false;
    
    public Projectile(Actor origin, Actor target, int lifeSpan){
        this.origin = origin;
        this.target = target;
        this.lifeSpan = lifeSpan;
        
        img = new GreenfootImage("arrow.png");
        
        img = new GreenfootImage(20, 20);
        img.setColor(Color.BLACK);
        img.fill();
        setImage(img);
        
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
        
        targetRelX = 100; //relative X position in the scrollable world
        targetRelY = 100; //relative Y position in the scrollable world
        /*
        this.setLocation(getWorld().getObjects(ScrollableWorld.class).get(0).getX() + relativeX, 
            getWorld().getObjects(ScrollableWorld.class).get(0).getY() + relativeY);*/
    }
    
    public void act(){
        System.out.println(toString());
        if(!setTarget){
            Scroller scroller = ((MyWorld)getWorld()).getScroller();
            if(target != null){
                targetX = target.getX() - scroller.getScrolledX();
                targetY = target.getY() - scroller.getScrolledY();
            }
            else{
                targetX = targetRelX - scroller.getScrolledX();
                targetY = targetRelY - scroller.getScrolledY();
            }
            
            //this.setLocation(origin.getX(), origin.getY());
            turnTowards(targetX, targetY); 
            setTarget = true;
        }      
        
        //this.setLocation(origin.getX(), origin.getY());
        if(lifeSpan == -1){
            moveToTarget();
        }
        if(lifeSpan > 0){
            lifeSpan--;
            moveToTarget();
        }
        if(lifeSpan == 0){
            getWorld().removeObject(this);
        }
    }

    private void moveToTarget(){
        //this.setLocation(origin.getX(), origin.getY());
        if(target != null){ 
            turnTowards(targetX, targetY);
            move(1);
        }
        else{
            turnTowards(targetX, targetY);
            move(1);
        }
    }
    
    public String toString(){
        return "pointing at: (" + targetX + ", " + targetY + ")";
    }
}
