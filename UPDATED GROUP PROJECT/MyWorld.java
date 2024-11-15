import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class MyWorld here.
 * 
 * @Andrew
 * @Darius
 * @Jonarahn
 * @Logan
 *
 * @1.2.0
 */
public class MyWorld extends World
{
    public Scroller scroller; // scroll controller
    private Player player; // main actor
    private Lives lives;
    private int waveCount, actCount;

    private static final int MAX_SPAWN_DISTANCE = 100;
    private boolean spawnOnce;

    public MyWorld()
    {
        super(1024, 576, 1, false);
        // settings
        
        waveCount = 0;
        actCount = 0;
        spawnOnce = true;


        addObject(scroller = new Scroller(this, new GreenfootImage("bgtemp.png"), 5048, 2024));
        addObject(player = new Player(), this.getWidth()/2, this.getHeight()/2);
        
        
        addObject(new Hitbox(200, 200), 375, 400);
        
        Slider slider = new Slider("TestSlider", "rail.png", "circle.png", 1, 130);
        addObject(slider, 155, 540);
        //addObject(lives = new Lives(), 50, 20);

        // addObject(new MiniMap(), 30, 370);
    }

    public void addObject(Actor a){

    }

    public void act()
    {
        actCount++;

        if(actCount == 300){
            spawnOnce = true;
            waveCount++;
            actCount = 0;
        }

        switch(waveCount) {
                case(0):

                // Manual wave simulator

                if(spawnOnce){
                    spawnOnce = false;
                    for(int i = 0; i < 3; i++){
                        addObject(new Bass(), Greenfoot.getRandomNumber (player.getX() + MAX_SPAWN_DISTANCE) + (player.getX() - MAX_SPAWN_DISTANCE), 
                            Greenfoot.getRandomNumber (player.getY() + MAX_SPAWN_DISTANCE) + (player.getY() - MAX_SPAWN_DISTANCE));
                    }

                }
                break;

        }

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
