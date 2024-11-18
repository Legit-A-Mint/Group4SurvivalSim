import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class StartScreen here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class StartScreen extends Actor
{
    private int worldNumber = 0;
    public StartScreen ()
    {
        // image from https://www.reddit.com/r/PixelArt/comments/150mtr6/ocean_by_me/#lightbox
        setImage("PixelOceanStart.png");
        getImage().scale(1024, 576);
    }
    public void act()
    {
        if (Greenfoot.isKeyDown("enter"))
        {
            Greenfoot.setWorld(new SelectCharacter());
        }
    }
}
