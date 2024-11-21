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
    private static int currentHp; // the value
    private int maxValue; // the value
    private int myX, myY;
    
    private GreenfootImage hearts;
    private GreenfootImage storeHearts;
    
    Player player;
    
    public Lives(String name, int myX, int myY, int max){
        super(name, myX, myY);
        
        this.myX = myX;
        this.myY = myY;
        hearts = new GreenfootImage("pixel_Heart.png");
        maxValue = max;
        currentHp = maxValue;
        updateDisplay(max);
    }
    
    public void act()
    {
        super.act();
    }
    
    public void updateDisplay(int health) {
        hearts = new GreenfootImage("pixel_Heart.png");
        hearts.scale(150, 150);
        storeHearts = new GreenfootImage(Integer.toString(health), 50, Color.WHITE, null);
        if (currentHp >= 1000)
        {
            hearts.drawImage(storeHearts, hearts.getWidth()/2 - 43, hearts.getHeight()/2 - 15);
        }
        else if (currentHp >= 100)
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
        setImage(hearts);
    }
    
    public void setHp(int hp) {
        currentHp = hp;
        updateDisplay(currentHp);
    }
    
    @Override
    protected boolean isUserInteracting() {
        return Greenfoot.mouseMoved(this);
    }
}
