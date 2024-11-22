import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Shop class that extends Interface and implements the isUserInteracting method.
 * Handles the interaction with the shop interface.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Shop extends Interface
{
    /**
     * Act - do whatever the Shop wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        if (isUserInteracting()) {
            
        }
    }

    /**
     * This method checks if the user is interacting with the shop.
     * It might check for a mouse click or other input.
     * @return true if the user is interacting, false otherwise.
     */
    @Override
    public boolean isUserInteracting()
    {
        if (Greenfoot.mouseClicked(this)) {
            return true;  
        }
        return false;  
    }
}

