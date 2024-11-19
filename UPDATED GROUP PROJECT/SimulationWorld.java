import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

/**
 * Write a description of class MyWorld here.
 * 
 * @Andrew
 * @Darius
 * @Jonathan
 * @Logan
 *
 * @1.2.7
 */
public class SimulationWorld extends World
{
    public Scroller scroller; // Scroll controller
    private Player player; // Main actor
    private Lives lives;
    private int waveCount, actCount;
    private boolean spawnOnce, countOnce;

    private static final int MAX_SPAWN_DISTANCE = 200;
    private static final int WIDTH = 2000, height = 2000;
    private int delay;

    private double exactY, exactX;
    
    private Button pauseButton;
    private static boolean acting;

    // https://pixabay.com/sound-effects/gentle-ocean-waves-fizzing-bubbles-64980/
    public static GreenfootSound ambientSound = new GreenfootSound("gentle_Ocean.mp3");
    
    public SimulationWorld(String playerModel)
    {
        super(1024, 576, 1, false);
        // settings

        waveCount = 0;
        actCount = 0;
        spawnOnce = true;
        delay = 30;

        addObject(scroller = new Scroller(this, new GreenfootImage("water.png"), WIDTH, height));
        addObject(player = new Player(playerModel), this.getWidth()/2, this.getHeight()/2);

        //border hitbox
        addObject(new Hitbox(WIDTH, 100, 2.5), WIDTH/2, height);
        addObject(new Hitbox(WIDTH, 100, 2.5), WIDTH/2, 0);
        addObject(new Hitbox(100, height, 2.5), WIDTH, height/2);
        addObject(new Hitbox(100, height, 2.5), 0, height/2);

        addObject(new Island(new GreenfootImage("island.png")), 500 - getScroller().getScrolledX(), 500 - getScroller().getScrolledY());
        addObject(new Island(new GreenfootImage("island.png")), 600 - getScroller().getScrolledX(), 1750 - getScroller().getScrolledY());
        addObject(new Island(new GreenfootImage("island.png")), 1750 - getScroller().getScrolledX(), 900 - getScroller().getScrolledY());
        addObject(new Island(new GreenfootImage("island.png")), 1500 - getScroller().getScrolledX(), 250 - getScroller().getScrolledY());
        addObject(new Island(new GreenfootImage("island.png")), 1200 - getScroller().getScrolledX(), 1500 - getScroller().getScrolledY());
        //addObject(new Hitbox(200, 200), 275, 400);
        //addObject(new Hitbox(200, 200), 600, 900);

        
        addObject(new Kraken(), getScroller().getScrollWidth()/2 - getScroller().getScrolledX(), getScroller().getScrollHeight()/2 - getScroller().getScrolledY());
        Slider slider = new Slider("TestSlider", "rail.png", "circle.png", 1, 130, 155, 540);
        addObject(slider, 155, 540);
        //addObject(lives = new Lives(), 50, 20);

        // addObject(new MiniMap(), 30, 370);
        //setPaintOrder(Hitbox.class, SliderObject.class, Slider.class, Island.class, Player.class, Enemy.class);
    }

    public void addedToWorld ()
    {
        // Plays the ambient noise in a loop
        ambientSound.playLoop();
    }

    public void started ()
    {
        // Plays the ambient noise in a loop
        ambientSound.playLoop();
    }

    public void stopped ()
    {
        // Stops playing the ambient noises when simulation is paused
        ambientSound.pause();
    }
    
    public void addObject(Actor a){

    }

    public void act()
    {
        /*
        actCount++;
        
        switch(waveCount) {
                case(0):

                // Manual wave simulator

                if(spawnOnce){
                    spawnOnce = false;
                    for(int i = 0; i < 3; i++){
                        addObject(new Bass(), Greenfoot.getRandomNumber (player.getX() + MAX_SPAWN_DISTANCE) + (player.getX() - MAX_SPAWN_DISTANCE), 
                            Greenfoot.getRandomNumber (player.getY() + MAX_SPAWN_DISTANCE) + (player.getY() - MAX_SPAWN_DISTANCE));
                        addObject(new Shark(), Greenfoot.getRandomNumber (player.getX() + MAX_SPAWN_DISTANCE) + (player.getX() - MAX_SPAWN_DISTANCE), 
                            Greenfoot.getRandomNumber (player.getY() + MAX_SPAWN_DISTANCE) + (player.getY() - MAX_SPAWN_DISTANCE));
                    }

                }
                break;
                
                case(1):
                delay--;
                if(delay == 0 && countOnce){
                    spawnOnce = true;
                    countOnce = false;
                }

                if(spawnOnce){
                    spawnOnce = false;
                    for(int i = 0; i < 5; i++){
                        addObject(new Shark(), Greenfoot.getRandomNumber (player.getX() + MAX_SPAWN_DISTANCE) + (player.getX() - MAX_SPAWN_DISTANCE), 
                            Greenfoot.getRandomNumber (player.getY() + MAX_SPAWN_DISTANCE) + (player.getY() - MAX_SPAWN_DISTANCE));
                    }
                }
                break;

        }
        
        if(actCount == 400 || (getObjects(Enemy.class).isEmpty() && countOnce == false)){
            waveCount++;
            actCount = 0;
            delay = 30;
            countOnce = true;
        }
        */
        scroller.scroll(getWidth()/2-player.getX(), getHeight()/2-player.getY(), this, (ArrayList<SuperSmoothMover>)(getObjects(SuperSmoothMover.class)));
    }

    /** returns the lives counter object */
    public Lives getLivesCounter()
    {
        return lives;
    }

    public Scroller getScroller(){
        return scroller;
    }

    public void addObject(Actor object, double x, double y){
        super.addObject(object, (int)(x + 0.5), (int)(y + 0.5));
    }

    /** prevents restarting after game over (called by greenfoot framework) 

    public void started()
    {
    if (!getObjects(GameOver.class).isEmpty()) Greenfoot.stop();
    }

     */

    public static double getDistance (Actor a, Actor b)
    {
        return Math.hypot (a.getX() - b.getX(), a.getY() - b.getY());
    }

    public double getPreciseY() 
    {
        return exactY;
    }

    public double exactY(){
        return exactY;
    }

    public double getPreciseX() 
    {
        return exactY;
    }

    public double exactX(){
        return exactY;
    }
}

