import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

/**
 * World where the game simulation takes place.
 * This world handles spawning coins, player, enemies, islands, and more.
 */
public class SimulationWorld extends World
{
    // Game objects and variables
    private Scroller scroller; // Scroll controller
    private Player player; // Main actor
    private Lives lives;
    private Button pauseButton;
    private Slider slider;
    
    private int coinSpawnDelay;  // Timer for spawning coins (in frames)
    private static final int SPAWN_INTERVAL = 30 * 60; // 30 seconds in frames (assuming 60 FPS)
    
    private static final int MAX_SPAWN_DISTANCE = 200;
    private static final int WIDTH = 2000, height = 2000;
    
    private int waveCount, actCount, delay;
    private boolean spawnOnce, countOnce;
    public static int killCount;
    public static double diffMulti;

    private double exactY, exactX;
    private static boolean acting;

    public static GreenfootSound ambientSound = new GreenfootSound("gentle_Ocean.mp3");

    public SimulationWorld(String playerModel, int maxLives, int speed, double difficulty)
    {
        super(1024, 576, 1, false);

        // Initialize variables
        waveCount = 0;
        actCount = 0;
        spawnOnce = true;
        delay = 30;
        coinSpawnDelay = SPAWN_INTERVAL;  // Initialize coin spawn delay

        acting = true;
<<<<<<< Updated upstream
        
        diffMulti = difficulty;

        addObject(scroller = new Scroller(this, new GreenfootImage("water.png"), WIDTH, height));
=======

        // Add background and player
        scroller = new Scroller(this, new GreenfootImage("water.png"), WIDTH, height);
>>>>>>> Stashed changes
        addObject(player = new Player(playerModel, speed), this.getWidth()/2, this.getHeight()/2);

        // Add borders and islands
        addObject(new Hitbox(WIDTH, 100, 2.5), WIDTH/2, height);
        addObject(new Hitbox(WIDTH, 100, 2.5), WIDTH/2, 0);
        addObject(new Hitbox(100, height, 2.5), WIDTH, height/2);
        addObject(new Hitbox(100, height, 2.5), 0, height/2);

        addObject(new Island(new GreenfootImage("island.png")), 500 - getScroller().getScrolledX(), 500 - getScroller().getScrolledY());
        addObject(new Island(new GreenfootImage("island.png")), 600 - getScroller().getScrolledX(), 1750 - getScroller().getScrolledY());
        addObject(new Island(new GreenfootImage("island.png")), 1750 - getScroller().getScrolledX(), 900 - getScroller().getScrolledY());
        addObject(new Island(new GreenfootImage("island.png")), 1500 - getScroller().getScrolledX(), 250 - getScroller().getScrolledY());
        addObject(new Island(new GreenfootImage("island.png")), 1200 - getScroller().getScrolledX(), 1500 - getScroller().getScrolledY());

        // Add enemies
        addObject(new Kraken(), getScroller().getScrollWidth()/2 - getScroller().getScrolledX(), getScroller().getScrollHeight()/2 - getScroller().getScrolledY());

<<<<<<< Updated upstream
        // GUI
        addObject(pauseButton = new Button("PauseButton", new String[] {"db_1.png", "db_2.png", "db_3.png"}, true, 1, 55, 35), 55, 35);

        addObject(slider = new Slider("TestSlider", "rail.png", "circle.png", 1, 130, 155, 540), 155, 540);

        addObject(lives = new Lives("Heart", 512, 60, maxLives), WIDTH/2, 100);

        // addObject(new MiniMap(), 30, 370);
        //setPaintOrder(Hitbox.class, SliderObject.class, Slider.class, Island.class, Player.class, Enemy.class);
    }

    public void addedToWorld ()
=======
        // GUI Elements
        pauseButton = new Button("PauseButton", new String[] {"db_1.png", "db_2.png", "db_3.png"}, true, 1, 55, 35);
        addObject(pauseButton, 55, 35);
        
        slider = new Slider("TestSlider", "rail.png", "circle.png", 1, 130, 155, 540);
        addObject(slider, 155, 540);
        
        lives = new Lives("Heart", 512, 60, maxLives);
        addObject(lives, WIDTH/2, 100);

        shopUI shop = new shopUI(player);
        addObject(shop, 400, 300);
    }
    
    // Plays ambient sound when the world is added to the simulation
    public void addedToWorld() 
>>>>>>> Stashed changes
    {
        ambientSound.playLoop();
    }

    // Called when the simulation starts
    public void started() 
    {
        ambientSound.playLoop();
    }

    // Stops playing the ambient noises when simulation is paused
    public void stopped() 
    {
        ambientSound.pause();
    }

    // Called on every act cycle
    public void act()
    {
        actCount++;
<<<<<<< Updated upstream
        /*
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
        addObject(new Whale(), Greenfoot.getRandomNumber (player.getX() + MAX_SPAWN_DISTANCE) + (player.getX() - MAX_SPAWN_DISTANCE), 
        Greenfoot.getRandomNumber (player.getY() + MAX_SPAWN_DISTANCE) + (player.getY() - MAX_SPAWN_DISTANCE));
        addObject(new Swordfish(), Greenfoot.getRandomNumber (player.getX() + MAX_SPAWN_DISTANCE) + (player.getX() - MAX_SPAWN_DISTANCE), 
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
=======
>>>>>>> Stashed changes

        // Decrease the coin spawn timer
        if (coinSpawnDelay > 0) {
            coinSpawnDelay--;
        }

<<<<<<< Updated upstream
        if(actCount == 400 || (getObjects(Enemy.class).isEmpty() && countOnce == false)){
        waveCount++;
        actCount = 0;
        delay = 30;
        countOnce = true;
=======
        // Check if it's time to spawn coins (every 30 seconds)
        if (coinSpawnDelay == 0) {
            spawnCoins();  // Spawn the coins
            coinSpawnDelay = SPAWN_INTERVAL;  // Reset the timer
>>>>>>> Stashed changes
        }

        // Other actions like scrolling the world based on player position
        scroller.scroll(getWidth()/2 - player.getX(), getHeight()/2 - player.getY(), this, (ArrayList<SuperSmoothMover>)(getObjects(SuperSmoothMover.class)));

        // Handle pausing the game
        if (Greenfoot.mouseClicked(pauseButton))
        {
        if (acting)
        {
        acting = false;
        ambientSound.pause();
        }
<<<<<<< Updated upstream
        else
        {
        acting = true;
        ambientSound.playLoop();
        }
        }
         */
        scroller.scroll(getWidth()/2-player.getX(), getHeight()/2-player.getY(), this, (ArrayList<SuperSmoothMover>)(getObjects(SuperSmoothMover.class)));
=======
>>>>>>> Stashed changes
    }

    // Spawns 5 coins at random locations within the world
    private void spawnCoins() 
    {
        for (int i = 0; i < 5; i++) {
            // Random position within the world bounds
            int randomX = Greenfoot.getRandomNumber(getWidth());
            int randomY = Greenfoot.getRandomNumber(getHeight());
            
            // Create a new coin and add it to the world at the random position
            Coins coin = new Coins();
            addObject(coin, randomX, randomY);
        }
    }

    // Additional methods...
    public static double getDistance (Actor a, Actor b)
    {
        return Math.hypot (a.getX() - b.getX(), a.getY() - b.getY());
    }

    public double getPreciseY() 
    {
        return exactY;
    }

    public double exactY() 
    {
        return exactY;
    }

    public double getPreciseX() 
    {
        return exactX;
    }

    public double exactX() 
    {
        return exactX;
    }

    // Check if simulation is active (false if "paused")
    public static boolean isActing()
    {
        return acting;
    }

    public static void addkillCount()
    {
        System.out.println("+1");
        killCount++;
    }

    public static int getKillCount()
    {
        if (killCount < 5)
        {
            return 0;
        }
        else if (killCount < 25)
        {
            return 1;
        }
        else
        {
            return 2;
        }
    }

    // New method to get the scroller object
    public Scroller getScroller() 
    {
        return scroller;
    }
}
