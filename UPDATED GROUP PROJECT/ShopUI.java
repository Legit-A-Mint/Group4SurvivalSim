import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class ShopUI here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ShopUI extends Interface
{   
    private GreenfootImage bottomBar;
    
    public ShopUI(String name, int width, int height, int myX, int myY)
    {
        super(name, myX, myY);    
            
        bottomBar = new GreenfootImage(width, height);
        bottomBar.setColor(Color.BLACK);
        bottomBar.fill();
        setImage(bottomBar);
    }
    
    public void addedToWorld()
    {
        
    }
    
    @Override
    protected boolean isUserInteracting() {
        return Greenfoot.mouseClicked(this);
    }
}
