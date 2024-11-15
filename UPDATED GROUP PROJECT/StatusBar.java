import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class StatusBar here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class StatusBar extends Interface
{
    /**
     * Act - do whatever the StatusBar wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        super.act();
        
        
    }
    @Override
    protected boolean isUserInteracting() {
        return Greenfoot.mouseClicked(this);
    }
}
