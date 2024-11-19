    import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import greenfoot.GreenfootImage;

/**
 * Write a description of class HpBar here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Lives extends SuperSmoothMover
{
    /**
     * Act - do whatever the HpBar wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    
    private int currentHp; // the value
    private int maxValue; // the value
    
    private static final int heartOffset = 4;
    
    private GreenfootImage hearts;
    private Font storeHearts;
    
    public Lives(int max){
        hearts = new GreenfootImage("pixel_Heart.png");
        maxValue = max;
        currentHp = maxValue;
        hearts.drawString(Integer.toString(currentHp), 512, 100);
        hearts.scale(80, 80);
        updateDisplay();
    }
    
    public void act()
    {
        updateDisplay();
    }
    
    private void updateDisplay() {
        hearts = new GreenfootImage("pixel_Heart.png");
        hearts.drawString(Integer.toString(currentHp), 512, 100);
        setImage(hearts);
    }
    
    public int getValue() {
        return currentHp; 
    }
    
    @Override /** over ride methods in super smooth mover to prevent movement */
    public void setLocation(int x, int y) {}
    public void setLocation(double x, double y) {}
    
}
