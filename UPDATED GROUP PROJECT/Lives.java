import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import greenfoot.GreenfootImage;

/**
 * Write a description of class HpBar here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Lives extends Interface
{    
    private String name;
    private int currentHp; // the value
    private int maxValue; // the value
    
    private GreenfootImage hearts;
    private GreenfootImage storeHearts;
    
    Color heartRed = new Color(247,5,1,255);
    
    public Lives(String name, int myX, int myY, int max){
        super(name, myX, myY);
        
        hearts = new GreenfootImage("pixel_Heart.png");
        maxValue = max;
        currentHp = maxValue;
        updateDisplay();
    }
    
    public void act()
    {
        super.act();
        updateDisplay();
    }
    
    private void updateDisplay() {
        hearts = new GreenfootImage("pixel_Heart.png");
        storeHearts = new GreenfootImage(Integer.toString(currentHp), 150, Color.WHITE, heartRed);
        hearts.drawImage(storeHearts, 175, 125);
        hearts.scale(100, 100);
        setImage(hearts);
    }
    
    public int getValue() {
        return currentHp; 
    }
    
    @Override
    protected boolean isUserInteracting() {
        return Greenfoot.mouseMoved(this);
    }
}
