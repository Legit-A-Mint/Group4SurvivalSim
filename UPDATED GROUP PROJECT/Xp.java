import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Represents a Coin for the ShopUI.
 * 
 * The Coin spawns with a predefined image.
 * 
 * @author 
 * @version 
 */
public class Xp extends Enemy {
    /**
     * Constructor for objects of class Coin.
     */
    public Xp() {
        // Set the image for the coin
        GreenfootImage coinImage = new GreenfootImage("coin.png"); // Ensure "coin.png" is in the project folder
        setImage(coinImage);
    }

    /**
     * Act - do whatever the Coin wants to do.
     */
    @Override
    public void act() {
        // Add behavior for the Coin here, if needed
    }

    /**
     * Implementation of the abstract method attack().
     * Since a Coin does not attack, this is left empty or with minimal behavior.
     */
    @Override
    protected void attack() {
        // Coins do not perform attacks
    }

    /**
     * Implementation of the abstract method attackAnimation().
     * Since a Coin does not have an attack animation, this is left empty.
     */
    @Override
    protected void attackAnimation() {
        // Coins do not have attack animations
    }
}
