import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Left here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Left extends DisplayCharacter
{
    public Left ()
    {
        setImage("characterSelectArrow.jpg");
        getImage().scale(50,100);
    }
    public void act()
    {
        if (Greenfoot.mouseClicked(this))
        {
            characterNum--;
            setDisplay(characterNum);
        }
    }
}
