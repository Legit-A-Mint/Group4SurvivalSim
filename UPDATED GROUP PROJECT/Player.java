import greenfoot.*;
import greenfoot.GreenfootImage;
import java.util.ArrayList;

/**
 * The Player class represents the main player character.
 * It handles movement, collisions, shooting, and interactions.
 * 
 * @lumilk
 * @Andrew Xu
 * 
 * @version 1.1.0
 */
public class Player extends Effects {
    private GreenfootImage[] playerImage = new GreenfootImage[1];
    private double speed;
    private double dx = 0, dy = 0;
    private int hitpoints, maxhitpoints;
    private int shootCounter, weaponCooldown;

    // Hitbox variables
    private Hitbox hitbox;
    private boolean createdHitbox;
    private int collisionCounter = 0; // Track number of collisions
    private final int MAX_COLLISION_ATTEMPTS = 3;

    // Direction variables for animation
    private int direction;

    // Constructor
    public Player() {
        playerImage[0] = new GreenfootImage("boy.png");
        speed = 7;
        weaponCooldown = 10;
        createdHitbox = false;
        maxhitpoints = 20;
        hitpoints = maxhitpoints;
    }

    // Called on every act cycle
    public void act() {
        animate(this, playerImage, playerImage[0].getWidth(), playerImage[0].getHeight(), 16, direction);

        if (shootCounter > 0) {
            shootCounter--;
        }

        if (!createdHitbox) {
            createHitbox();
        }

        handleMovement();
        handleInputs();
        updateHitboxPosition();
        
        System.out.println("Player Coordinants ("+ (getX() - ((MyWorld)getWorld()).getScroller().getScrolledX())
        + ", " +  (getY() - ((MyWorld)getWorld()).getScroller().getScrolledY()) + ")");
    }

    // Create the player's hitbox
    private void createHitbox() {
        hitbox = new Hitbox(playerImage[0].getWidth() - 30, playerImage[0].getHeight() / 2, 0, 0, this, 2.5);
        getWorld().addObject(hitbox, getX(), getY());
        createdHitbox = true;
    }

    // Handle player movement and collisions
    private void handleMovement() {
        dx = 0;
        dy = 0;

        // Input-based movement
        if (Greenfoot.isKeyDown("a")) {
            dx -= speed;
            direction = 3; // Left
        }
        if (Greenfoot.isKeyDown("d")) {
            dx += speed;
            direction = 1; // Right
        }
        if (Greenfoot.isKeyDown("w")) {
            dy -= speed;
        }
        if (Greenfoot.isKeyDown("s")) {
            dy += speed;
        }

        handleCollision(dx, dy);
    }

    // Handle movement with collision detection
    private void handleCollision(double dx, double dy) {
        double futureX = getX() + dx;
        double futureY = getY() + dy;

        // Handle horizontal movement
        hitbox.setLocation(futureX, getY());
        if (!isCollidingWithHitbox()) {
            setLocation(futureX, getY());
            collisionCounter = 0; // Reset collision counter
        } else {
            resetHitboxPosition();
            handleRepel("horizontal");
        }

        // Handle vertical movement
        hitbox.setLocation(getX(), futureY);
        if (!isCollidingWithHitbox()) {
            setLocation(getX(), futureY);
            collisionCounter = 0; // Reset collision counter
        } else {
            resetHitboxPosition();
            handleRepel("vertical");
        }
    }

    // Handle shooting inputs
    private void handleInputs() {
        if (shootCounter == 0 && !getWorld().getObjects(Enemy.class).isEmpty()) {
            if (Greenfoot.isKeyDown("e")) {
                shootCounter = weaponCooldown;
                shoot();
            }
        }
    }

    // Addobject projectile to my position
    private void shoot() {
        getWorld().addObject(new Projectile(), getX(), getY());
    }
    
    public void damageMe(int damage){
        if(hitpoints > 0){
            hitpoints -= damage;   
        }
        //System.out.println("Player HP: " + hitpoints); 
    }

    // Repel the player upon collision
    private void handleRepel(String direction) {
        collisionCounter++;
        if (collisionCounter >= MAX_COLLISION_ATTEMPTS) {
            if (direction.equals("horizontal")) {
                setLocation(getX() - dx * 2, getY());
            } else if (direction.equals("vertical")) {
                setLocation(getX(), getY() - dy * 2);
            }
            collisionCounter = 0; // Reset collision counter
        }
    }

    // Update the hitbox position to align with the player
    private void updateHitboxPosition() {
        hitbox.setLocation(getX(), getY());
    }

    // Reset hitbox position to match the player
    private void resetHitboxPosition() {
        hitbox.setLocation(getX(), getY());
    }

    // Check if the hitbox is colliding with other objects
    private boolean isCollidingWithHitbox() {
        for (Hitbox other : hitbox.getIntersectingHitboxes()) {
            if (other != hitbox && other.checkCollision(hitbox)) {
                return true;
            }
        }
        return false;
    }
    
    public Hitbox getHitbox(){
        return this.hitbox;
    }
}
