import java.util.List;
import java.util.ArrayList;
import greenfoot.*;

/**
 * 
 * @lumilk
 * @1.0.0
 */

public class Player extends Effects {
    private GreenfootImage[] playerImage = new GreenfootImage[1];
    private GreenfootImage[] floatyImage = new GreenfootImage[3];
    private double speed;
    private double dx = 0, dy = 0;
    private int hp, maxhp;
    private int shootCounter, weaponCooldown;

    // Inventory
    private ArrayList<Object> inventory;
    private int coins;

    // Hitbox variables
    private Hitbox hitbox;
    private boolean createdHitbox;
    private int collisionCounter = 0;
    private final int MAX_COLLISION_ATTEMPTS = 3;

    // Which floating device is player using (0 = floaty, 1 = wood raft, 2 = metal boat)
    private GreenfootImage playerImg;
    private GreenfootImage tempImg;
    private int floatyNum = 0;

    // Direction variables for animation
    private int direction;
    
    SimulationWorld world;
    Lives lives;

    private Actor target;  // Target for AI-controlled player to move towards

    public Player(String playerModel, int speed, int health, Lives lives) {
        floatyImage[0] = new GreenfootImage("floaty.png");
        floatyImage[1] = new GreenfootImage("wood.png");
        floatyImage[2] = new GreenfootImage("metal.png");
        playerImg = new GreenfootImage(playerModel);
        setRaft(0);

        this.lives = lives;
        this.speed = speed;
        
        weaponCooldown = 15;  // Cooldown in terms of ticks, 60 ticks = 1 second
        createdHitbox = false;
        maxhp = health;
        hp = maxhp;
        coins = 0; // Initialize coins
        inventory = new ArrayList<>(); // Initialize inventory
    }

    public void act() {
        if (SimulationWorld.isActing()) {
            if (shootCounter > 0) {
                shootCounter--; // Decrease shoot counter to create a delay
            }

            if (!createdHitbox) {
                createHitbox();
            }

            // AI-controlled movement
            setTargetToNearestCoinOrEnemy();  // Set target to the nearest coin or enemy
            aiMove();

            handleInputs();  // Handle player shooting
            updateHitboxPosition();
            setRaft(world.getKillCount());
            collectCoins();
        }
    }
    
    private void collectCoins() {
        Actor coin = getOneIntersectingObject(Coins.class);
        if (coin != null) {
            Coins c = (Coins) coin;
            addCoins(c.COIN_VALUE);  // Add coins to player
            getWorld().removeObject(c);  // Remove coin from world
        }
    }

    public void addCoins(int amount) {
        coins += amount;
    }
    
    public int getCoins() {
        return coins;
    }
    
    public void setRaft(int num) {
        if (floatyNum == 0)
        {
            // if you're not on a raft, the floaty has to be drawn on top of you
            tempImg = new GreenfootImage(playerImg);
            tempImg.drawImage(floatyImage[floatyNum], 0, 0);
            playerImage[0] = tempImg;
        }
        else
        {
            // otherwise draw player on top of raft
            tempImg = new GreenfootImage(floatyImage[num]);
            tempImg.drawImage(playerImg, 0, 0);
            playerImage[0] = tempImg;
        }
        setImage(playerImage[0]);
    }

    // Create the player's hitbox
    private void createHitbox() {
        hitbox = new Hitbox(playerImage[0].getWidth() - 30, playerImage[0].getHeight() / 2, 0, 0, this, 2.5);
        getWorld().addObject(hitbox, getX(), getY());
        createdHitbox = true;
    }

    // AI-controlled movement
    private void aiMove() {
        if (target != null) {
            // Get the target's position
            double targetX = target.getX();
            double targetY = target.getY();
            
            // Calculate direction vector
            double deltaX = targetX - getX();
            double deltaY = targetY - getY();
            double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
            
            // Normalize the direction vector to get the movement direction
            double directionX = deltaX / distance;
            double directionY = deltaY / distance;
            
            // Move towards the target
            dx = directionX * speed;
            dy = directionY * speed;

            handleCollision(dx, dy);  // Handle collision detection while moving

            // If the target is an enemy and is within shooting range, shoot at it
            if (target instanceof Enemy && distance < 500) {  // 500 is the shooting range
                shootWithDelay();  // Ensure shooting with a delay
            }
        }
    }

    // Set the target to the nearest coin or enemy
    private void setTargetToNearestCoinOrEnemy() {
        Actor nearestTarget = null;
        double nearestDistance = Double.MAX_VALUE;
        
        // Check for coins and enemies
        List<Actor> objectsInWorld = getWorld().getObjects(Actor.class);  // Use List<Actor>
        for (Actor obj : objectsInWorld) {
            // Only target coins or enemies
            if (obj instanceof Coins || obj instanceof Enemy) {
                double deltaX = obj.getX() - getX();
                double deltaY = obj.getY() - getY();
                double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
                
                // Find the nearest object
                if (distance < nearestDistance) {
                    nearestDistance = distance;
                    nearestTarget = obj;
                }
            }
        }
        
        // Set the nearest object as the target
        target = nearestTarget;
    }

    // Handle player movement with collision detection
    private void handleMovement() {
        dx = 0;
        dy = 0;

        // Input-based movement (for manual control)
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

    // Handle shooting inputs with a 1-second delay
    private void handleInputs() {
        if (shootCounter == 0 && !getWorld().getObjects(Enemy.class).isEmpty()) {
            if (Greenfoot.isKeyDown("space")) {
                shootWithDelay();  // Only shoot if the cooldown has passed
            }
        }
    }

    // Add projectile to player's position with a delay
    protected void shootWithDelay() {
        if (shootCounter <= 0) {
            shootCounter = weaponCooldown; // Reset shoot cooldown
            getWorld().addObject(new Projectile("arrow.png"), getX(), getY());
        }
    }

    // Damage the player
    public void damageMe(int damage) {
        if (hp > 0) {
            hp -= damage * world.diffMulti;
            lives.setHp(hp);
            System.out.println("PLAYER HP: " + hp);
        }
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

    public Hitbox getHitbox() {
        return this.hitbox;
    }

    // Set the target for the AI to move towards (could be an enemy or other object)
    public void setTarget(Actor target) {
        this.target = target;
    }
    
    public void addInventory(String item) {
        inventory.add(item);  // Adds item to the player's inventory
    }

    public void addHp(int amount) {
        hp = Math.min(hp + amount, maxhp);  // Increase HP but not exceeding max HP
    }
    
     public int getHp() {
        return hp;
    }
}