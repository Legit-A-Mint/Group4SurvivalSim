import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

/**
 * Write a description of class Island here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Island extends Effects
{
    private boolean createdHitbox, createdSeagulls;
    private GreenfootImage img;
    private CollisionHitbox hitbox;

    private static final double BOUNDINGFACTOR = 2.0;
    public Island(GreenfootImage img){
        this.img = img;
        img.scale((int)(img.getWidth() * 1.5), (int)(img.getHeight() * 1.5));
        setImage(img);
        createdHitbox = false;
        createdSeagulls = true;
    }

    public void act()
    {
        if(!createdSeagulls){
            int spawnNum = Greenfoot.getRandomNumber(5); // Spawn 0 - 5 seagulls

            for(int i = 0; i < spawnNum; i++){
                int spawnX = ((this.getX() - img.getWidth()/2) + Greenfoot.getRandomNumber(img.getWidth()) - ((SimulationWorld)getWorld()).getScroller().getScrolledX());
                int spawnY = ((this.getX() - img.getHeight()/2) + Greenfoot.getRandomNumber(img.getHeight()) - ((SimulationWorld)getWorld()).getScroller().getScrolledY());
                
                getWorld().addObject(new Seagull(false, Greenfoot.getRandomNumber(2) * 2 - 1, 1), spawnX, spawnY);
            }
            
            createdSeagulls = true;
        }
        if(!createdHitbox){
            hitbox = new CollisionHitbox(img.getWidth(), (int)(img.getHeight()/1.75), 2.5);
            getWorld().addObject(hitbox, this.getX(), this.getY() - 30);
            createdHitbox = true;
        }
    }
}
