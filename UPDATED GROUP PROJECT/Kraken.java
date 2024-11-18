import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Kraken here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Kraken extends Boss
{
    private boolean isInitialized;
    private GreenfootImage img;
    public Kraken(int hp){
        super(hp);
        setImage("coin.png");
        isInitialized = false;
    }
    public void act()
    {
        if(!isInitialized){
            this.setLocation(((((MyWorld)getWorld()).getScroller().getImage().getWidth()/2) - 
            (((MyWorld)getWorld()).getScroller().getScrolledX())), 
            ((((MyWorld)getWorld()).getScroller().getImage().getHeight()/2) -
            (((MyWorld)getWorld()).getScroller().getScrolledY())));     
            
            isInitialized = true;
        }
        
        
    }
    
    private void tentacleAttack(){
        
    }
    private void summonAttack(){
        
    }
    private void aoeAttack(){
        
    }
}
