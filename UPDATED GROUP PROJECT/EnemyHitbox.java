import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
import java.util.*;

/**
 * Full hitbox class
 * 
 * @Andrew Xu
 * @1.0.0
 */
public class EnemyHitbox extends SuperSmoothMover
{
    //image variables
    private GreenfootImage box;
    private Actor owner;
    private String type;
    private static final boolean visible = false;
    
    private int offsetX, offsetY;
    private double boundingFactor;
    public EnemyHitbox(int h, int w, double boundingFactor){
        this.boundingFactor = boundingFactor;
        this.type = type;
        box = new GreenfootImage(h, w);
        box.setColor(Color.RED);
        box.setTransparency(55); //less distracting when turned on
        if(visible){
            box.fill();
        }
        setImage(box);
    }
    
    public EnemyHitbox(int h, int w, int offsetX, int offsetY, Actor owner, double boundingFactor){
        this.boundingFactor = boundingFactor;
        this.type = type;
        box = new GreenfootImage(h, w);
        box.setColor(Color.RED);
        box.setTransparency(55);
        if(visible){
            box.fill();
        }
        setImage(box);
        this.owner = owner;
        
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }
    
    public void act()
    {
        if(owner != null){
            moveWithOwner();
        }
    }  

    private void moveWithOwner(){
        setLocation(owner.getX() + offsetX, owner.getY() + offsetY);
    }
}
