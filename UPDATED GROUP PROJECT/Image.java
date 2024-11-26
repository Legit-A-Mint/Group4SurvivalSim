import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import greenfoot.GreenfootImage;

/**
 * Write a description of class DisplayCharacter here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Image extends Interface
{
    private GreenfootImage img;
    public Image (String img)
    {
        this.img = new GreenfootImage(img);
        setImage(img);
    }
    public Image (String img, int x, int y)
    {
        this.img = new GreenfootImage(img);
        setImage(img);
        getImage().scale(x,y);
    }
    public void changeImg(String img, int x, int y)
    {
        setImage(img);
        getImage().scale(x,y);
    }
    
    @Override
    protected boolean isUserInteracting(){
        return Greenfoot.mouseMoved(this);
    }
}
