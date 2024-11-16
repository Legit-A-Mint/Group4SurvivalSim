import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

/**
 * Write a description of class Island here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Island extends SuperSmoothMover
{
    private boolean createdHitbox;
    private GreenfootImage img;
    private Hitbox hitbox;
    
    private static final double BOUNDINGFACTOR = 2.0;
    public Island(GreenfootImage img){
        this.img = img;
        setImage(img);
        createdHitbox = false;
    }
    
    
    
    
    public void act()
    {
        if(!createdHitbox){
            hitbox = new Hitbox(img.getWidth(), (int)(img.getHeight()/1.35), 2.5);
            getWorld().addObject(hitbox, this.getX(), this.getY() + 10);
            createdHitbox = true;
        }
    }
}
