import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class StatsLabel here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class StatsLabel extends UI
{
    public StatsLabel(GreenfootImage img)
    {
        setImage(img);
    }
    
    public StatsLabel(GreenfootImage img, int x, int y)
    {
        setImage(img);
        getImage().scale(x,y);
    }

    public void setText(GreenfootImage img)
    {
        setImage(img);
    }

    public void scale(int x, int y)
    {
        getImage().scale(x,y);
    }
}
