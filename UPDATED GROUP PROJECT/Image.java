import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class DisplayCharacter here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Image extends UI
{
    public Image (String img, int x, int y)
    {
        setImage(img);
        getImage().scale(x,y);
    }
    public void changeImg(String img, int x, int y)
    {
        setImage(img);
        getImage().scale(x,y);
    }
}
