import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * Represents the Kraken enemy.
 * 
 * @author 
 * @version 
 */
public class Kraken extends Enemy
{
    private boolean isInitialized;
    private boolean createdHitbox;
    private Hitbox hitbox;

    public Kraken(){
        super();
        img = new GreenfootImage[12];
        createdHitbox = false;
        isMovable = false;
        hp = 100;
        damage = 5;

        img[0] = new GreenfootImage("KrakenF1.png");
        // Set the animation frames
        for(int i = 0; i < img.length; i++){
            img[i] = new GreenfootImage("KrakenF" + (i+1) + ".png");
        }
        isInitialized = false;
    }

    public void act()
    {
        super.act();
        animate(this, img, img[0].getWidth(), img[0].getHeight(), 24, 1);

        // Handle collisions
        if (checkForCollision()) {
            attackAnimation();
            attack();
        }
    }

    /**
     * Check for collisions with the player or other specific objects.
     * @return true if a collision occurs, false otherwise.
     */
    public boolean checkForCollision() {
        if (getPlayerCollision()) {
            return true;
        }

        // Optionally, add other collision checks (e.g., with projectiles or world elements)
        // Example: Check for collisions with Tentacle objects
        ArrayList<Tentacle> tentacles = (ArrayList<Tentacle>) getIntersectingObjects(Tentacle.class);
        return !tentacles.isEmpty();
    }

    /**
     * Attack the player upon collision.
     */
    @Override
    public void attack() {
        if (getPlayer() != null) {
            getPlayer().damageMe(damage);
        }
    }

    /**
     * Play the Kraken's attack animation.
     */
    @Override
    public void attackAnimation() {
        animate(this, img, img[0].getWidth(), img[0].getHeight(), 6, direction);
    }

    @Override
    protected void createHitbox() {
        if (!createdHitbox) {
            hitbox = new Hitbox((int) (img[0].getWidth() / 2), (int) (img[0].getHeight() / 2), 2.5);
            getWorld().addObject(hitbox, this.getX() - 7, this.getY());
            createdHitbox = true;
        }
    }

    // Empty implementations for methods that do not apply to Kraken
    @Override
    public void repel() {}

    @Override
    public void pushAwayFromObjects(ArrayList<Actor> nearbyObjects, double minDistance) {}
}
