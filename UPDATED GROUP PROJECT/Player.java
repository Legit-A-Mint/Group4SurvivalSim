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

    // AI movement variables
    private int aiMoveTimer = 0;
    private int aiMoveDuration = 30; // Time steps to continue a movement direction
    private int aiDirection; // Randomly chosen direction

    // Which floating device is player using (0 = floaty, 1 = wood raft, 2 = metal boat)
    private GreenfootImage playerImg;
    private GreenfootImage tempImg;
    private int floatyNum = 0;

    // Direction variables for animation
    private int direction;

    public Player(String playerModel, int speed) {
        floatyImage[0] = new GreenfootImage("floaty.png");
        floatyImage[1] = new GreenfootImage("wood.png");
        floatyImage[2] = new GreenfootImage("metal.png");
        playerImg = new GreenfootImage(playerModel);
        setRaft();

        this.speed = speed;
        weaponCooldown = 10;
        createdHitbox = false;
        maxhp = 999999999;
        hp = maxhp;
        coins = 0; // Initialize coins
        inventory = new ArrayList<>(); // Initialize inventory
    }

    public void act() {
        //System.out.println("Player: (" + (getX() - ((SimulationWorld)getWorld()).getScroller().getScrolledX()) + ", " + (getY() - ((SimulationWorld)getWorld()).getScroller().getScrolledY()) + ")");

        if (SimulationWorld.isActing()) {
            animate(this, playerImage, playerImage[0].getWidth(), playerImage[0].getHeight(), 16, direction);

            if (shootCounter > 0) {
                shootCounter--;
            }

            if (!createdHitbox) {
                createHitbox();
            }

            //handleAIMovement(); // Replaced input-based movement with AI
            handleInputs();
            updateHitboxPosition();
        }
    }

    public void setRaft() {
        if (floatyNum == 0) {
            // if your not on a raft, the floaty has to be drawn on top of you
            tempImg = new GreenfootImage(playerImg);
            tempImg.drawImage(floatyImage[floatyNum], 0, 0);
            playerImage[0] = tempImg;
        } else {
            // otherwise draw player ontop of raft
            tempImg = new GreenfootImage(floatyImage[floatyNum]);
            tempImg.drawImage(playerImg, 0, 0);
            playerImage[0] = tempImg;
        }
        setImage(playerImg);
    }

    // AI-controlled random movement
    private void handleAIMovement() {
        if (aiMoveTimer == 0) {
            // Choose a random direction: 0 = up, 1 = right, 2 = down, 3 = left
            aiDirection = Greenfoot.getRandomNumber(4);
            aiMoveTimer = aiMoveDuration;
        }

        dx = 0;
        dy = 0;

        // Set movement direction based on AI decision
        switch (aiDirection) {
            case 0: // Move up
                dy -= speed;
                direction = 0; // Set animation direction
                break;
            case 1: // Move right
                dx += speed;
                direction = 1; // Set animation direction
                break;
            case 2: // Move down
                dy += speed;
                direction = 2; // Set animation direction
                break;
            case 3: // Move left
                dx -= speed;
                direction = 3; // Set animation direction
                break;
        }

        // Handle collisions and apply movement
        handleCollision(dx, dy);

        // Decrement the AI timer
        aiMoveTimer--;
    }

    // Add an item to the inventory
    public void addItem(Object item) {
        inventory.add(item);
        System.out.println("Item added to inventory: " + item.getClass().getSimpleName());
    }

    // Get the player's inventory
    public ArrayList<Object> getInventory() {
        return inventory;
    }

    // Add coins
    public void addCoins(int amount) {
        coins += amount;
        System.out.println("Added " + amount + " coins. Total: " + coins);
    }

    // Spend coins
    public void spendCoins(int amount) {
        if (coins >= amount) {
            coins -= amount;
            System.out.println("Spent " + amount + " coins. Remaining: " + coins);
        } else {
            System.out.println("Not enough coins! Required: " + amount + ", Available: " + coins);
        }
    }

    // Get current coins
    public int getCoins() {
        return coins;
    }

    // Create the player's hitbox
    private void createHitbox() {
        hitbox = new Hitbox(playerImage[0].getWidth() - 30, playerImage[0].getHeight() / 2, 0, 0, this, 2.5);
        getWorld().addObject(hitbox, getX(), getY());
        createdHitbox = true;
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
        }

        // Handle vertical movement
        hitbox.setLocation(getX(), futureY);
        if (!isCollidingWithHitbox()) {
            setLocation(getX(), futureY);
            collisionCounter = 0; // Reset collision counter
        } else {
            resetHitboxPosition();
        }
    }

    private void handleInputs() {
        
        
        // Optional: Add AI-based shooting logic here
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
    public void damageMe(int damage) {
        if (hp > 0) {
            hp -= damage;
            System.out.println("PLAYER HP: " + hp);
        }
    }

    private void updateHitboxPosition() {
        hitbox.setLocation(getX(), getY());
    }

    private void resetHitboxPosition() {
        hitbox.setLocation(getX(), getY());
    }

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
}
