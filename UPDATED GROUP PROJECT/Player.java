import greenfoot.*;
import greenfoot.GreenfootImage;
import java.util.ArrayList;

/**
 * Write a description of class Player here.
 * 
 * @lumilk
 * @Andrew
 * 
 * @1.0.0
 */
public class Player extends Effects
{
    protected GreenfootImage playerImage, reflectedPlayerImage;
    private double speed;
    private double dx = 0, dy = 0;

    //hitbox variables
    private Hitbox hitbox;
    private boolean createdHitbox;
    private int collisionCounter = 0; //track num times collision has been used
    private final int MAX_COLLISION_ATTEMPTS = 3; 

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

    private boolean collided;  
     */

    public Player(){
        playerImage = new GreenfootImage("shark.png");
        reflectedPlayerImage = playerImage;
        reflectedPlayerImage.mirrorHorizontally();
        setImage(playerImage);

        speed = 3;

        /*
        playerMaxHealth = 5 + moddedH;
        playerHealth = playerMaxHealth;
        gold = 0;
        CPU = moddedCPU;
        maxSpeed = (1.25 + moddedSpeed) * CPU;
        speed = (maxSpeed) * CPU;
         */

        createdHitbox = false;
    }

    public void act()
    {
        if(!createdHitbox){
            hitbox = new Hitbox(playerImage.getWidth() - 30, playerImage.getHeight()/2, 0, 0, this);
             getWorld().addObject(hitbox, 0, 0);
            createdHitbox = true;
        }   
        
        //System.out.println("Collisions: "  + collisionCounter + " Colliding: " + isCollidingWithHitbox() + " dx: " + dx + " dy: " + dy);
        
        handleMovement();
        handleInputs();
        updateHitboxPosition();
    }

    private void handleMovement(){
        dx = 0;
        dy = 0;

        //handle movement (based on CPU's input)    
        if (Greenfoot.isKeyDown("a")) dx-= speed;
        if (Greenfoot.isKeyDown("d")) dx+= speed;
        if (Greenfoot.isKeyDown("w")) dy-= speed;
        if (Greenfoot.isKeyDown("s")) dy+= speed;

        //attempt horizontal movement
        /**get future position with +- deltaX*/
        double futureX = getX() + dx;
        //set hitbox X to future location
        hitbox.setLocation(futureX, getY());
        if (!isCollidingWithHitbox()){
            setLocation(futureX, getY());
            collisionCounter = 0; //reset counter since not colliding
        } 
        else{
            hitbox.setLocation(getX(), getY());
            collisionCounter++; //colliding - increment counter
            if (collisionCounter >= MAX_COLLISION_ATTEMPTS){
                repel(dx, 0); //push back
                collisionCounter = 0; 
            }
        } 
        //attempt vertical movement
        /**get future position with +- deltaY*/
        double futureY = getY() + dy;
        //set hitbox Y to future location
        hitbox.setLocation(getX(), futureY);
        if (!isCollidingWithHitbox()){
            setLocation(getX(), futureY);
            collisionCounter = 0; //reset counter since not colliding
        } 
        else{
            hitbox.setLocation(getX(), getY());
            collisionCounter++; //colliding - increment counter 
            if (collisionCounter >= MAX_COLLISION_ATTEMPTS){
                repel(0, dy); //push back
                //collisionCounter = 0; 
            }
        }

        /**
        //temporarily disabled, figure out how to get this to work with new collision + movement methods*/
        /*
        //If not centered, start to center myself to world
        if (dx != 0 || dy != 0){
            //limit movement to keep image in world window at all times
            if (getX()+dx < getImage().getWidth()/2 || getX()+dx > getWorld().getWidth()-getImage().getWidth()/2) dx = 0;
            if (getY()+dy < getImage().getHeight()/2 || getY()+dy > getWorld().getHeight()-getImage().getHeight()/2) dy = 0;
            /** these last two statements go hand in hand with the boy being the central actor in the world 
            //setLocation(getX()+dx, getY()+dy);
        }*/
    }

    private void handleInputs(){
        if (Greenfoot.isKeyDown("e")) getWorld().addObject(new Projectile(this),0 , 0);
    }
    
    private void repel(double dx, double dy) {
        //push back in opposite direction of movement
        int pushBackDistance = 3;
        
        /** FIGURE OUT A WAY TO REPEL, ENEMIES CANNOT PUSH PLAYER
        if (dx == 0 && dy == 0){
            setLocation(getX() - pushBackDistance, getY() - pushBackDistance); // Apply only if unstuck
        }*/
    }   

    private void updateHitboxPosition() {
        //align hitbox with player
        hitbox.setLocation(getX(), getY());
    }

    private boolean isCollidingWithHitbox() {
        //check my hitbox is touching any other hitboxes
        for (Hitbox other : hitbox.getIntersectingHitboxes()) {
            return(other != hitbox && other.checkCollision(hitbox));
        }
        return false;
    }
}
