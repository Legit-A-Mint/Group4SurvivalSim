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
    private GreenfootImage storeHearts;
    
    public Lives(){
        hearts = new GreenfootImage("pixel_Heart.png");
        maxValue = 5;
        storeHearts = new GreenfootImage( maxValue*(hearts.getWidth() + heartOffset), hearts.getHeight());
        updateDisplay();
        setImage(storeHearts);
    }
    
    private void updateDisplay() {
        for (int i = 0; i < currentHp; i++) {
            storeHearts.drawImage(hearts, maxValue*(hearts.getWidth() + heartOffset) , 0);
        }
    }
    
    public int getValue() {
        return currentHp; 
    }
    
    
    @Override /** over ride methods in super smooth mover to prevent movement */
    public void setLocation(int x, int y) {}
    public void setLocation(double x, double y) {}
    
}
