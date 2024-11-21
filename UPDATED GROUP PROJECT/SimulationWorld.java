import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

public class SimulationWorld extends World
{
    public Scroller scroller; // Scroll controller
    private Player player; // Main actor
    private Lives lives;
    private int waveCount, actCount;
    private boolean spawnOnce, countOnce;
    public static int killCount;
    public static double diffMulti;

    private static final int MAX_SPAWN_DISTANCE = 200;
    private static final int WIDTH = 2000, height = 2000;
    private int delay;

    private double exactY, exactX;

    private Button pauseButton;
    private Slider slider;
    private static boolean acting;

    private int coinSpawnTimer;  // Timer to track coin spawning time
    private int coinDisplay;

    public static GreenfootSound ambientSound = new GreenfootSound("gentle_Ocean.mp3");

    private Label waveLabel; 

    public SimulationWorld(String playerModel, int maxLives, int speed, double difficulty)
    {
        super(1024, 576, 1, false);

        waveCount = 0;  // Initialize wave count
        actCount = 0;
        spawnOnce = true;
        delay = 30;

        acting = true;

        coinSpawnTimer = 0;  // Initialize the coin spawn timer

        diffMulti = difficulty;

        addObject(lives = new Lives("Heart", 512, 60, maxLives), WIDTH/2, 100);
        addObject(scroller = new Scroller(this, new GreenfootImage("water.png"), WIDTH, height));
        addObject(player = new Player(playerModel, speed, lives), this.getWidth()/2, this.getHeight()/2);

        // border hitbox
        addObject(new Hitbox(WIDTH, 100, 2.5), WIDTH/2, height);
        addObject(new Hitbox(WIDTH, 100, 2.5), WIDTH/2, 0);
        addObject(new Hitbox(100, height, 2.5), WIDTH, height/2);
        addObject(new Hitbox(100, height, 2.5), 0, height/2);

        addObject(new Island(new GreenfootImage("island.png")), 500 - getScroller().getScrolledX(), 500 - getScroller().getScrolledY());
        addObject(new Island(new GreenfootImage("island.png")), 600 - getScroller().getScrolledX(), 1750 - getScroller().getScrolledY());
        addObject(new Island(new GreenfootImage("island.png")), 1750 - getScroller().getScrolledX(), 900 - getScroller().getScrolledY());
        addObject(new Island(new GreenfootImage("island.png")), 1500 - getScroller().getScrolledX(), 250 - getScroller().getScrolledY());
        addObject(new Island(new GreenfootImage("island.png")), 1200 - getScroller().getScrolledX(), 1500 - getScroller().getScrolledY());

        // GUI
        addObject(pauseButton = new Button("PauseButton", new String[] {"db_1.png", "db_2.png", "db_3.png"}, true, 1, 55, 35), 55, 35);
        addObject(slider = new Slider("TestSlider", "rail.png", "circle.png", 1, 130, 155, 540), 155, 540);

        waveLabel = new Label("Wave " + (waveCount + 1), 40);  // Initial label will display "Wave 1"
        addObject(waveLabel, 200, 25);  // Position it on the screen
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
        if (acting)
        {
            actCount++;

            // Update coin spawn timer
            coinSpawnTimer++;

            // Every 30 seconds, spawn 5 coins at random locations
            if (coinSpawnTimer >= 900) {  // 900 ticks = 30 seconds (assuming 30 FPS)
                spawnCoins();
                coinSpawnTimer = 0;  // Reset the timer after spawning coins
            }

            // Handle enemy waves
            handleWaves();

            scroller.scroll(getWidth()/2 - player.getX(), getHeight()/2 - player.getY(), this, (ArrayList<SuperSmoothMover>)(getObjects(SuperSmoothMover.class)));
        }
        if (Greenfoot.mouseClicked(pauseButton))
        {
            acting = !acting;
            if (acting)
            {
                ambientSound.playLoop();
            }
            else
            {
                ambientSound.pause();
            }
        }
    }

    // Handle the spawning of enemies for each wave
    private void handleWaves() {
        // Check if all enemies have been defeated before starting a new wave
        if (getObjects(Enemy.class).isEmpty()) {
            waveCount++;  // Increment the wave count
            spawnEnemiesForWave(waveCount);  // Spawn enemies based on the wave count

            // Update the wave label to display the new wave count
            waveLabel.setText("Wave " + (waveCount + 1));  
        }
    }

    // Method to spawn enemies based on the current wave
    private void spawnEnemiesForWave(int wave) {
        // For each wave, spawn one more enemy from each enemy class
        for (int i = 0; i < wave; i++) {
            spawnEnemy(Bass.class);
            spawnEnemy(Krakite.class);
            spawnEnemy(Shark.class);
            spawnEnemy(Swordfish.class);
            spawnEnemy(Whale.class);
        }
    }

    // Method to spawn a single enemy at a random location
    private void spawnEnemy(Class<? extends Actor> enemyClass) {
        int randomX = Greenfoot.getRandomNumber(WIDTH);
        int randomY = Greenfoot.getRandomNumber(height);

        try {
            Actor enemy = enemyClass.getDeclaredConstructor().newInstance();  // Create a new instance of the enemy class
            addObject(enemy, randomX, randomY);  // Add the enemy to the world at a random location
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to spawn 5 coins at random locations within the world
    private void spawnCoins() {
        for (int i = 0; i < 5; i++) {
            // Ensure that coins are within the visible world area (1024x576)
            int randomX = Greenfoot.getRandomNumber(getWidth());  // Random X position within the visible world width (1024)
            int randomY = Greenfoot.getRandomNumber(getHeight()); // Random Y position within the visible world height (576)
            addObject(new Coins(), randomX, randomY);  // Add the coin to the world
        }
    }

    public Scroller getScroller(){
        return scroller;
    }

    public void addObject(Actor object, double x, double y){
        super.addObject(object, (int)(x + 0.5), (int)(y + 0.5));
    }

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
}
