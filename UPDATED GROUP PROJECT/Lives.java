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
    private int myX, myY;
    
    private GreenfootImage hearts;
    private GreenfootImage storeHearts;
    
    Color heartRed = new Color(247,5,1,255);
    
    public Lives(String name, int myX, int myY, int max, boolean fadesAway){
        super(name, myX, myY);
        
        this.myX = myX;
        this.myY = myY;
        this.fadesAway = fadesAway;
        
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
        hearts.scale(150, 150);
        //storeHearts = new GreenfootImage("test", 150, Color.WHITE, Color.BLACK);
        storeHearts = new GreenfootImage(Integer.toString(currentHp), 50, Color.WHITE, heartRed);
        hearts.drawImage(storeHearts, 50, 50);
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
