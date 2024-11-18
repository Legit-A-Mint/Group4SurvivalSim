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
        img.scale((int)(img.getWidth() * 1.5), (int)(img.getHeight() * 1.5));
        setImage(img);
        createdHitbox = false;
    }
    
    public void act()
    {
        if(!createdHitbox){
            hitbox = new Hitbox(img.getWidth(), (int)(img.getHeight()/1.75), 2.5);
            getWorld().addObject(hitbox, this.getX(), this.getY() - 30);
            createdHitbox = true;
        }
    }
}
