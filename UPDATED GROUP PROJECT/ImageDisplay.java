import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class StatsLabel here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ImageDisplay extends Interface
{
    public ImageDisplay(GreenfootImage img)
    {
        setImage(img);
    }
    
    public ImageDisplay(String img)
    {
        setImage(img);
    }
    
    public ImageDisplay(GreenfootImage img, int x, int y)
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
    
    @Override
    protected boolean isUserInteracting(){
        return Greenfoot.mouseMoved(this);
    }
}
