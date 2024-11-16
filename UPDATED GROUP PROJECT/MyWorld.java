import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class MyWorld here.
 * 
 * @Andrew
 * @Darius
 * @Jonarahn
 * @Logan
 *
 * @1.2.2
 */
public class MyWorld extends World
{
    public Scroller scroller; // scroll controller
    private Player player; // main actor
    private Lives lives;
    private int waveCount, actCount;

    private static final int MAX_SPAWN_DISTANCE = 100;
    private boolean spawnOnce;

    private static final int WIDTH = 2000, height = 2000;
    public MyWorld()
    {
        super(1024, 576, 1, false);
        // settings
        
        waveCount = 0;
        actCount = 0;
        spawnOnce = true;


        addObject(scroller = new Scroller(this, new GreenfootImage("water.png"), WIDTH, height));
        addObject(player = new Player(), this.getWidth()/2, this.getHeight()/2);
        
        //border hitbox
        addObject(new Hitbox(WIDTH, 100), WIDTH/2, height);
        addObject(new Hitbox(WIDTH, 100), WIDTH/2, 0);
        addObject(new Hitbox(100, height), WIDTH, height/2);
        addObject(new Hitbox(100, height), 0, height/2);
        
        
        //addObject(new Hitbox(200, 200), 275, 400);
        //addObject(new Hitbox(200, 200), 600, 900);
        
        addObject(new Hitbox(10, 10), 500 - getScroller().getScrolledX(), 500 - getScroller().getScrolledY());
        
        
        Slider slider = new Slider("TestSlider", "rail.png", "circle.png", 1, 130, 155, 540);
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
