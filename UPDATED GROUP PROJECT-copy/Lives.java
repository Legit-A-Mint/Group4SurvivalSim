import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A class to represent the player's health bar (lives) and handle game over logic.
 * 
 * @Jonathan
 * @1.0.0
 */
public class Lives extends Interface {    
    private String name;
    private int maxValue; // The maximum health value (initial lives)
    private int myX, myY;

    private GreenfootImage hearts; // Image for the heart
    private GreenfootImage storeHearts; // Image for the numerical value of lives
    
    private Player player;
    
    private static final int HEART_OFFSET = 20;

    // Constructor to initialize the Lives object
    public Lives(){
        /*
        this.myX = myX;
        this.myY = myY;
        */
        
        hearts = new GreenfootImage("pixel_Heart.png"); // Load the heart image
        
        // Get instance of Player
        player = getWorld().getObjects(Player.class).get(0);
        
        // Values 
        maxValue = player.getMaxHp(); // Set max lives
        
        // setImage
        updateDisplay(); // Update the display immediately
    }
    
    @Override
    public boolean isUserInteracting(){
        return true;
    }

    // This method is called each frame to update the display of the health bar
    public void act() {
        // super.act();
        updateDisplay(); // Continuously update the display
    }
    
    public void updateDisplay() {
        for (int i = 0; i < player.getHp(); i++) {
            storeHearts.drawImage(hearts, maxValue*(hearts.getWidth() + HEART_OFFSET) , 0);
        }
        setImage(storeHearts);
    }
}
