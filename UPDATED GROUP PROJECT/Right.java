import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Right here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Right extends DisplayCharacter
{
    public Right ()
    {
        setImage("charArror.png");
        getImage().mirrorHorizontally();
        getImage().scale(50,100);
    }
    public void act()
    {
        if (Greenfoot.mouseClicked(this))
        {
            characterNum++;
            setDisplay();
        }
    }
}
