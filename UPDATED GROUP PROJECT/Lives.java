import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import greenfoot.GreenfootImage;

/**
 * A class to represent the player's health bar (lives) and handle game over logic.
 * 
 * @Jonathan
 * @1.0.0
 */
public class Lives extends SuperSmoothMover {    
    private String name;
    private int maxValue; // The maximum health value (initial lives)

    private GreenfootImage hearts; // Image for the heart
    private GreenfootImage storeHearts; // Image for the numerical value of lives

    private Player player;

    private static final int HEART_OFFSET = 20;
    
    private int tempThing = 3; 

    // Constructor to initialize the Lives object
    public Lives(){
        // Get instance of Player
        //player = getWorld().getObjects(Player.class).get(0);

        hearts = new GreenfootImage("pixel_Heart.png"); // Load the heart image
        //storeHearts = new GreenfootImage((hearts.getWidth() + HEART_OFFSET*2)*player.getMaxHp(), hearts.getHeight());

        storeHearts = new GreenfootImage(220, hearts.getHeight());
        
        // Values 
        //maxValue = player.getMaxHp(); // Set max lives

        // setImage
        //updateDisplay(); // Update the display immediately

        /**
        hearts = new GreenfootImage("pixel_Heart.png"); // Load the heart image
        setImage(hearts);
        */
    }


    // This method is called each frame to update the display of the health bar
    public void act() {
        // super.act();
        player = getWorld().getObjects(Player.class).get(0);
        updateDisplay(); // Continuously update the display
    }

    public void updateDisplay() {
        /*
        for (int i = 0; i < tempThing; i++) {
            storeHearts.drawImage(hearts, ((hearts.getWidth() + HEART_OFFSET))*i , 0);
        }
        */
       
        storeHearts.drawImage(hearts, 0, 0);
        storeHearts.drawImage(hearts, 30, 0);
        //
        setImage(storeHearts);
    }

    @Override
    public void setLocation(int x, int y){}

    @Override
    public void setLocation(double x, double y){}
}
