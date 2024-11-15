import greenfoot.*;
import greenfoot.GreenfootImage;
import java.util.ArrayList;

/**
 * Write a description of class Player here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Player extends Effects
{
    protected GreenfootImage playerImage, reflectedPlayerImage;
    private int direction, speed;

    // private Enemy targetEnemy;
    // private ArrayList<Enemy> enemies;

    // private Backpack inventory;

    /**
    private int playerHealth, playerMaxHealth, gold, intelligence, actCount;
    private double maxSpeed, speed;
    private boolean attackReady;

    private double closestTargetDistance = 0;
    private double distanceToActor;
    private int relativeX, relativeY;
    private Hitbox h;
    private boolean createdHitbox;
    private boolean collided;  
     */

    public Player(){
        playerImage = new GreenfootImage("shark.png");
        //reflectedPlayerImage = new GreenfootImage("shark.png");
        //reflectedPlayerImage.mirrorHorizontally();
        setImage(playerImage);
        
        speed = 3;
        //direction = 1;

        /*
        playerMaxHealth = 5 + moddedH;
        playerHealth = playerMaxHealth;
        gold = 0;
        CPU = moddedCPU;
        maxSpeed = (1.25 + moddedSpeed) * CPU;
        speed = (maxSpeed) * CPU;
         */

        //createdHitbox = false;
    }

    public void act()
    {
        // get user directed movement
        int dx = 0, dy = 0;
        
        if (Greenfoot.isKeyDown("a")) dx-= speed;
        if (Greenfoot.isKeyDown("d")) dx+= speed;
        if (Greenfoot.isKeyDown("w")) dy-= speed;
        if (Greenfoot.isKeyDown("s")) dy+= speed;
        
        // If not centered, start to center it
        if (dx != 0 || dy != 0)
        {
            // limit movement to keep boy image in world window at all times
            if (getX()+dx < getImage().getWidth()/2 || getX()+dx > getWorld().getWidth()-getImage().getWidth()/2) dx = 0;
            if (getY()+dy < getImage().getHeight()/2 || getY()+dy > getWorld().getHeight()-getImage().getHeight()/2) dy = 0;
            /** these last two statements go hand in hand with the boy being the central actor in the world */
            // move
            setLocation(getX()+dx, getY()+dy);
        }
    }

}
