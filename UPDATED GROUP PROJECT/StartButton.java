import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class StartButton here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class StartButton extends StartScreen
{
    public StartButton()
    {
        setImage("PlayButton.png");
        getImage().scale(393, 159);
    }
    public void act()
    {
        if (Greenfoot.mouseClicked(this))
        {
            nextWorld();
        }
    }
}
