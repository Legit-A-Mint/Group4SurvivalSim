import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class DisplayCharacter here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class DisplayCharacter extends UI
{
    public void setCharImg(String img)
    {
        setImage(img);
        getImage().scale(300, 300);
    }
}
