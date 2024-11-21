import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A class to represent the player's health bar (lives) and handle game over logic.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Lives extends Interface {    
    private String name;
    private int currentHp; // The current health (lives) value
    private int maxValue; // The maximum health value (initial lives)
    private int myX, myY;

    private GreenfootImage hearts; // Image for the heart
    private GreenfootImage storeHearts; // Image for the numerical value of lives

    // Constructor to initialize the Lives object
    public Lives(String name, int myX, int myY, int max) {
        super(name, myX, myY);

        this.myX = myX;
        this.myY = myY;
        hearts = new GreenfootImage("pixel_Heart.png"); // Load the heart image
        maxValue = 200; // Set max lives
        currentHp = maxValue; // Initialize current lives
        updateDisplay(); // Update the display immediately
    }

    // This method is called each frame to update the display of the health bar
    public void act() {
        super.act();
        updateDisplay(); // Continuously update the display
    }

    // Method to update the value of health and refresh the display
    public void updateValue(int newValue) {
        currentHp = newValue; // Set the new health value
        updateDisplay(); // Refresh the hearts image
    }

    // This method handles the display of the health bar and updates when health reaches zero
    private void updateDisplay() {
        hearts = new GreenfootImage("pixel_Heart.png"); // Reset the heart image
        hearts.scale(150, 150); // Resize heart image

        // Display the current health value as text
        storeHearts = new GreenfootImage(Integer.toString(currentHp), 50, Color.WHITE, null);

        // Adjust the position of the health number based on its size
        if (currentHp >= 100) {
            hearts.drawImage(storeHearts, hearts.getWidth()/2 - 33, hearts.getHeight()/2 - 15);
        } else if (currentHp >= 10) {
            hearts.drawImage(storeHearts, hearts.getWidth()/2 - 20, hearts.getHeight()/2 - 15);
        } else {
            hearts.drawImage(storeHearts, hearts.getWidth()/2 - 10, hearts.getHeight()/2 - 15);
        }

        // If health is 0 or less, transition to the game over screen (EndScreen)
        if (currentHp <= 0) {
            Greenfoot.setWorld(new EndScreen()); // Switch to the EndScreen world
        }

        setImage(hearts); // Set the updated image as the actor's image
    }

    // Getter for the current health value
    public int getValue() {
        return currentHp;
    }

    // Override the method to check if the user is interacting with the health bar (for other purposes)
    @Override
    protected boolean isUserInteracting() {
        return Greenfoot.mouseMoved(this); // Detect if the mouse is moving over the health bar
    }
}
