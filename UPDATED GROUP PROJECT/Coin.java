import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Represents a Coin for the ShopUI.
 * 
 * The Coin spawns with a predefined image.
 * 
 * @author (your name)
 * @version (a version number or a date)
 */
public class Coin extends ShopUI {
    /**
     * Constructor for objects of class Coin.
     */
    public Coin(String name, int width, int height, int myX, int myY) {
        super(name, width, height, myX, myY);
        
        // Set the image for the coin
        GreenfootImage coinImage = new GreenfootImage("coin.png"); //Make the file name "Coin.png"
        setImage(coinImage);
    }

    /**
     * Act - do whatever the Coin wants to do.
     */
    @Override
    public void act() {
        // Add code here if you want anything specific for the coin to do
    }
    
    /*  Use this code to set the paramteters as needed to the world's area so it spawns 
     *  within and not outside the world
     *  
     *                      IMPORTANT
     *                          |
     *                          v
     *  Coin coin = new Coin("Coin", 50, 50, 100, 100); // Replace parameters as needed
        getWorld().addObject(coin, 200, 300); // Replace 200, 300 with desired coordinates */
}
