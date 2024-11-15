import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class MyWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MyWorld extends World
{
    public Scroller scroller; // scroll controller
    private Player player; // main actor
    private Lives lives;
    private int timer;

    public MyWorld()
    {
        super(1024, 576, 1, false);
        // settings
        
        addObject(scroller = new Scroller(this, new GreenfootImage("bgtemp.png"), 1700, 1500));
        addObject(player = new Player(), this.getWidth()/2, this.getHeight()/2);
        //addObject(lives = new Lives(), 50, 20);
        addObject(new Bass(this), this.getWidth()/2 + 100, this.getHeight()/2 + 100);
        
        // addObject(new MiniMap(), 30, 370);
    }
    
    public void addObject(Actor a){
        
    }
    
    public void act()
    {
        scroller.scroll(getWidth()/2-player.getX(), getHeight()/2-player.getY());
    }
    
    /** returns the lives counter object */
    public Lives getLivesCounter()
    {
        return lives;
    }
    
    public Scroller getScroller(){
        return scroller;
    }

    /** prevents restarting after game over (called by greenfoot framework) */
    /**
    public void started()
    {
        if (!getObjects(GameOver.class).isEmpty()) Greenfoot.stop();
    }
    */
}
