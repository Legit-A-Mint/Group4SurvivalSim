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
    
    public Lives(String name, int myX, int myY, int max){
        super(name, myX, myY);
        
        this.myX = myX;
        this.myY = myY;
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
    
    public void updateValue(int newValue) 
    {
        currentHp = newValue; // Ensure HP doesn't go below 0
        updateDisplay(); // Refresh the hearts image
    }

    private void updateDisplay() {
        hearts = new GreenfootImage("pixel_Heart.png");
        hearts.scale(150, 150);
        //storeHearts = new GreenfootImage("test", 150, Color.WHITE, Color.BLACK);
        storeHearts = new GreenfootImage(Integer.toString(currentHp), 50, Color.WHITE, null);
        if (currentHp >= 100)
        {
            hearts.drawImage(storeHearts, hearts.getWidth()/2 - 33, hearts.getHeight()/2 - 15);
        }
        else if (currentHp >= 10)
        {
            hearts.drawImage(storeHearts, hearts.getWidth()/2 - 20, hearts.getHeight()/2 - 15);
        }
        else
        {
            hearts.drawImage(storeHearts, hearts.getWidth()/2 - 10, hearts.getHeight()/2 - 15);
        }
        if (currentHp <= 0)
        {
            Greenfoot.setWorld(new EndScreen());
        }
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
