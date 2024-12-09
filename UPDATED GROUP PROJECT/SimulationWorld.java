import greenfoot.*;  // Import Greenfoot classes for world, actors, images, and sound
import java.util.*;  // Import utility classes such as List

/**
 * Full SimulationWorld
 * @Andrew
 * @Darius
 * @Logan
 * @Jonathan
 */

public class SimulationWorld extends World{
    // Declaration of instance variables
    public Scroller scroller; // The scroll controller to manage the background
    private Player player; // This is the player actor
    private Lives lives; // This displays the amount of lives you have
    private ImageDisplay coin; // The coin image
    private ImageDisplay displayCoins; // Display number of coins
    private int actCount; // Tracks and counts the number of waves and actions
    private boolean spawnOnce, countOnce; // Used for one-time actions
    public static int killCount; // Counts for the kills across the game
    public static double diffMulti; // This multiplies the difficulty of the simulation
    // Used for the world size and maximum spawn distance
    private static final int MAX_SPAWN_DISTANCE = 200;
    private static final int WIDTH = 2500, HEIGHT = 2500;
    private int delay; // This is used to delay variables

    private double exactY, exactX; // This tracks the exact coordinate

    // UI elements
    private Button pauseButton, shopButton; // The pause button for the gameplay
    private Slider slider; // The slider shown on the bottom left
    private static boolean acting; // Used to check if the game is active and running
    private Shop shop;
    private boolean shopToggled = false;

    private int coinSpawnTimer;  // Timer to track coin spawning time
    private int coinDisplay; // Used to track how many coins to display

    // Background ambient sound
    public static GreenfootSound ambientSound = new GreenfootSound("gentle_Ocean.mp3");

    private Label waveLabel; // Label to display the current wave number
    private boolean createdKraken; // The flag for Kraken 

    // Variables for handling waves
    private int waveTimer;
    private int waveCount;
    private boolean firstWave;
    private static final int FINAL_WAVE = 20;
    private static final int WAVE_IDLE_TIME = 300; // Adjust idle time for wave
    private Image waveClearImage;

    // Heart counter variables
    private ImageDisplay heartCounter;

    // Ambient class spawnage
    private int seagullTimer;
    private static final int SEAGULL_SPAWN_TIME = 500;

    private static int actCounter;

    // Constructor for the world, initializes objects
    public SimulationWorld(String playerModel, int maxLives, double speed, double difficulty, int coin){
        super(1024, 646, 1, false); // Create world with size 1024x576 pixels

        // Initialize game variables
        waveCount = 0;  // Start at wave 0
        firstWave = true; // Ensure start at first wave
        actCount = 0; // Start at action count 0
        spawnOnce = true; // The flag for spawning 
        delay = 30; // The delay variable
        coinDisplay = coin;
        actCounter = 0;

        acting = true; // Set the game to be active and running

        coinSpawnTimer = 0;  // Initialize coin spawn timer

        diffMulti = difficulty; // Set difficulty multiplier

        // Add scroller for background movement
        addObject(scroller = new Scroller(this, new GreenfootImage("water.png"), WIDTH, HEIGHT));

        // Add player to the center of the screen
        addObject(player = new Player(playerModel, speed, maxLives, coinDisplay), this.getWidth() / 2, this.getHeight() / 2);

        // Add border hitboxes to prevent the player from going outside the world
        addObject(new CollisionHitbox(WIDTH, 100, 2.5), WIDTH / 2, HEIGHT); // Bottom border
        addObject(new CollisionHitbox(WIDTH, 100, 2.5), WIDTH / 2, 0); // Top border
        addObject(new CollisionHitbox(100, HEIGHT, 2.5), WIDTH, HEIGHT / 2); // Right border
        addObject(new CollisionHitbox(100, HEIGHT, 2.5), 0, HEIGHT / 2); // Left border

        // Add islands to the world
        addObject(new Island(new GreenfootImage("island.png")), 520 - getScroller().getScrolledX(), 520 - getScroller().getScrolledY());
        addObject(new Island(new GreenfootImage("island.png")), 620 - getScroller().getScrolledX(), 2000 - getScroller().getScrolledY());
        addObject(new Island(new GreenfootImage("island.png")), 1750 - getScroller().getScrolledX(), 1200 - getScroller().getScrolledY());
        addObject(new Island(new GreenfootImage("island.png")), 1500 - getScroller().getScrolledX(), 550 - getScroller().getScrolledY());
        addObject(new Island(new GreenfootImage("island.png")), 1200 - getScroller().getScrolledX(), 1500 - getScroller().getScrolledY());
        addObject(new Island(new GreenfootImage("island.png")), 1900 - getScroller().getScrolledX(), 1900 - getScroller().getScrolledY());

        // Add GUI elements like pause button and slider
        addObject(pauseButton = new Button("PauseButton", new String[]{"pause1.png", "pause2.png", "pause3.png"}, true, 1, 1, false), 55, 35);  
        addObject(shopButton = new Button("ShopButton" , new String[]{"Shop1.png", "Shop2.png", "Shop3.png"}, true, 1, 1, false), 55, 100);

        addObject(slider = new Slider("TestSlider.", "rail.png", "circle.png", 1, 130, false), 180, getHeight() - 50);  

        addObject(shop = new Shop(), 725, getHeight() - 90);  
        shop.showShop(false);
        // Add a label to display the wave number
        // waveLabel = new Label("Wave " + (waveCount + 1), 40, 200, 25);  // Initialize label to show wave
        // addObject(waveLabel, 200, 25);  // Position the wave label on the screen

        //Add a label to display the wave number
        waveLabel = new Label("Wave " + (waveCount + 1), 40, false);  // Initialize label to show wave
        addObject(waveLabel, getWidth() - 100, 25);  // Position the wave label on the screen

        // Always keep in world
        addObject(waveClearImage = new Image("WaveClear.png"), getWidth()/2, getHeight()/2 - 100);
        waveClearImage.getImage().setTransparency(0);

        // Add lives
        //addObject(lives = new Lives(), 235, 30);

        // Add coins
        addObject(this.coin = new ImageDisplay("coin.png"), 150, 100);
        addObject(displayCoins = new ImageDisplay(new GreenfootImage(Integer.toString(coinDisplay), 50, Color.WHITE, null)), 215, 100);

        addObject(new ImageDisplay("pixel_Heart.png"), 150, 30);
        addObject(heartCounter = new ImageDisplay(new GreenfootImage(Integer.toString(player.getHP()), 50, Color.WHITE, null)), 215, 30);

        setPaintOrder(ImageDisplay.class, Shop.class, Image.class, Interface.class, Seagull.class, Lives.class, Projectile.class);
    }

    /**
     * @Darius
     * AddedToworld, started, stopped
     */
    // Method that gets called when the world is added to the Greenfoot environment
    public void addedToWorld ()
    {
        // Plays the ambient noise in a loop
        ambientSound.playLoop(); 
    }

    // @Jonathan
    // Method that gets called when the world starts
    public void started(){
        // Start the ambient sound in a loop when the game starts
        ambientSound.playLoop();
    }

    // @Jonathan
    // Method that gets called when the world stops / is paused
    public void stopped(){
        // Stop the ambient sound when the game is paused
        ambientSound.pause();
    }

    // Empty addObject method    
    public void addObject(Actor a){
        // This empty method prevents other addObject calls from being overridden
    }

    // @Mr.Cohen
    // Modified by @Jonathan
    public static int getActNumber() {
        // return a number from 0 - 59
        return actCounter % 5;
    }

    // @Logan
    // Main act method that runs on every frame of the game
    public void act(){
        if (acting){
            actCounter++;
            actCount++;         // The increment action count
            coinSpawnTimer++;   // Update coin spawn timer

            if(seagullTimer > 0){
                seagullTimer--;
            }
            else{
                spawnSeagull(); 
            }

            // If 30 seconds (900 ticks) have passed, spawn 5 coins
            if (coinSpawnTimer >= 450){  // 450 ticks = 15 seconds
                coinSpawnTimer = 0;  // Reset coin spawn timer after spawning coins
            }

            handleWaves();

            scroller.scroll(getWidth() / 2 - player.getX(), getHeight() / 2 - player.getY(), this, (ArrayList<Effects>) (getObjects(Effects.class)));
        }

        // Check if the pause button is clicked
        if (pauseButton.checkClicked()){
            acting = !acting;  // Toggle the game state between active and paused
            if (acting){
                ambientSound.playLoop();  // Resume ambient sound
            } else{
                ambientSound.pause();  // Pause ambient sound
            }
        }

        if (shopButton.checkClicked()){
            shopToggled = !shopToggled; // Flip state 
            shop.showShop(shopToggled);
        }

        if (actCounter % 3000 == 0){ // redistribute every 3000 acts ( to even out bug pathings per act
            Enemy.resetActDistribution();
            for (Enemy E : getObjects(Enemy.class)){
                E.refreshActNumber();
            }
        }
        
        ambientSound.setVolume((int)(slider.getPercent()));
    }

    // @Logan
    // Increament the wave counter based on if the wave is cleared
    private void handleWaves(){
        // Begin with first wave always
        if(firstWave){
            //System.out.println("Current wave: " + waveCount);
            startWave(0);
            firstWave = false;
        }
        else if(!firstWave && this.getObjects(Enemy.class).isEmpty() && waveCount < FINAL_WAVE){
            if(waveTimer == 0){
                // Increment wave count to change wave
                waveCount++;
                startWave(waveCount);
                waveClearImage.getImage().setTransparency(0);
                waveLabel.setText("Wave " + (waveCount + 1));
                waveTimer = WAVE_IDLE_TIME;
            }
            else if(waveTimer > 0){
                waveTimer--;
                waveClearImage.getImage().setTransparency(255);
                //System.out.println("Wave timer: " + waveTimer);
            }
        }   
    }

    // @Logan
    // Spawns a wave depending on the entered parameters and waveCount
    private void startWave (int waveCount){ 
        // Spawn different enemies in different waves

        // Edit the values in spawnEnemies parameter to change spawn amount of each enemy
        // @Param 
        // Bass, lionfish, shark, whale, swordfish, manatee
        switch(waveCount){

            case 0:
                spawnEnemies(5, 0, 0, 0, 0, 0); // Easy introduction with Bass
                break;
            case 1:
                spawnEnemies(3, 0, 0, 2, 0, 0); // Introduce Lionfish
                break;
            case 2:
                spawnEnemies(6, 2, 0, 2, 0, 0); // Increase Bass and Lionfish
                break;
            case 3:
                spawnEnemies(0, 0, 5, 3, 0, 0); // Introduce Shark
                break;
            case 4:
                spawnEnemies(6, 4, 1, 1, 0, 0); // Mix of Bass and Lionfish
                break;
            case 5:
                spawnEnemies(15, 0, 0, 0, 0, 0); // Slight Shark increment
                break;
            case 6:
                spawnEnemies(5, 5, 5, 5, 2, 0); // Introduce Whale
                break;
            case 7:
                spawnEnemies(7, 2, 2, 2, 5, 2); // Whale becomes a challenge
                break;
            case 8:
                spawnEnemies(5, 5, 5, 5, 5, 5); // Continued balance scaling
                break;
            case 9:
                ambientSound.stop();
                spawnKraken(); 
                break;

                // If you beat the last wave, switch to winning screen

            case 10:
                Greenfoot.setWorld(new WinningScreen());
        }        
    }

    // @Logan
    // Spawns enemies at the center of the world
    // params determine amount of each spawned
    private void spawnEnemies(int numBass, int numLionfish, int numShark, int numWhale, int numSwordfish, int numManatee){
        for (int i = 0; i < numBass; i++){
            int spawnX = WIDTH / 2 + getScroller().getScrolledX() + (int) ((Math.random() < 0.5 ? 1 : -1)*(Math.random() * 11));
            int spawnY = HEIGHT / 2 + getScroller().getScrolledY() + (int) ((Math.random() < 0.5 ? 1 : -1)*(Math.random() * 11));
            addObject(new Bass(diffMulti), spawnX, spawnY);
        }
        for (int i = 0; i < numLionfish; i++){
            int spawnX = WIDTH / 2 + getScroller().getScrolledX() + (int) ((Math.random() < 0.5 ? 1 : -1)*(Math.random() * 11)) ;
            int spawnY = HEIGHT / 2 + getScroller().getScrolledY() + (int) ((Math.random() < 0.5 ? 1 : -1)*(Math.random() * 11));
            addObject(new Lionfish(diffMulti), spawnX, spawnY);
        }
        for (int i = 0; i < numShark; i++){
            int spawnX = WIDTH / 2 + getScroller().getScrolledX() + (int) ((Math.random() < 0.5 ? 1 : -1)*(Math.random() * 11));
            int spawnY = HEIGHT / 2 + getScroller().getScrolledY() + (int) ((Math.random() < 0.5 ? 1 : -1)*(Math.random() * 11)) ;
            addObject(new Shark(diffMulti), spawnX, spawnY);
        }
        for (int i = 0; i < numWhale; i++){
            int spawnX = WIDTH / 2 + getScroller().getScrolledX() + (int) ((Math.random() < 0.5 ? 1 : -1)*(Math.random() * 11));
            int spawnY = HEIGHT / 2 + getScroller().getScrolledY() + (int) ((Math.random() < 0.5 ? 1 : -1)*(Math.random() * 11));
            addObject(new Whale(diffMulti), spawnX, spawnY);
        }
        for (int i = 0; i < numSwordfish; i++){
            int spawnX = WIDTH / 2 + getScroller().getScrolledX() + (int) ((Math.random() < 0.5 ? 1 : -1)*(Math.random() * 11)) ;
            int spawnY = HEIGHT / 2 + getScroller().getScrolledY() + (int) ((Math.random() < 0.5 ? 1 : -1)*(Math.random() * 11));
            addObject(new Swordfish(diffMulti), spawnX, spawnY);
        }
        for (int i = 0; i < numManatee; i++){
            int spawnX = WIDTH / 2 + getScroller().getScrolledX() + (int) ((Math.random() < 0.5 ? 1 : -1)*(Math.random() * 11)) ;
            int spawnY = HEIGHT / 2 + getScroller().getScrolledY() + (int) ((Math.random() < 0.5 ? 1 : -1)*(Math.random() * 11));
            addObject(new Manatee(diffMulti), spawnX, spawnY);
        }

    }

    // @Logan
    // Add a method to spawn the Kraken
    private void spawnKraken(){
        // Create a Kraken actor at a random position
        Kraken kraken = new Kraken(diffMulti);  // Instantiate the Kraken actor
        int spawnX = WIDTH/2 + getScroller().getScrolledX();
        int spawnY = HEIGHT/2 + getScroller().getScrolledY();
        //System.out.println("Added new kraken @ (" + spawnX + ", " + spawnY + ")");
        addObject(kraken, spawnX, spawnY);
    }

    // @Logan
    // Spawn ambient seagulls
    private void spawnSeagull(){
        seagullTimer = SEAGULL_SPAWN_TIME;

        int spawnSide = Greenfoot.getRandomNumber(2) * 2 - 1;
        int spawnY = Greenfoot.getRandomNumber(HEIGHT - 500) +  getScroller().getScrolledY();
        //System.out.println("Added new SEAGULL @ (" + (spawnSide * WIDTH + getScroller().getScrolledX())  + ", " + spawnY + ")");
        addObject(new Seagull(true, -spawnSide, 2), spawnSide * WIDTH + getScroller().getScrolledX() , spawnY);

    }

    // Logan
    // Method to check if the Kraken is defeated
    private boolean isKrakenDefeated(){
        return getObjects(Kraken.class).isEmpty();  // Return true if the Kraken has been defeated
    }
    // Transition to losing screen
    public void losingScreen(){
        // Transition logic for winning the game after defeating the Kraken in wave 10
        ambientSound.stop();
        Greenfoot.setWorld(new LosingScreen(waveCount + 1));
    }

    // Transition to the winning screen
    private void winningScreen(){
        // Transition logic for winning the game after defeating the Kraken in wave 20
        Greenfoot.setWorld(new WinningScreen());
    }

    // Override to add objects with precise coordinates (not currently used)
    public void addObject(Actor object, double x, double y){
        super.addObject(object, (int) (x + 0.5), (int) (y + 0.5));
    }

    // @Jonathan
    public void updateCoins(int num)
    {
        coinDisplay = num;
        removeObject(displayCoins);
        addObject(displayCoins = new ImageDisplay(new GreenfootImage(Integer.toString(coinDisplay), 50, Color.WHITE, null)), 215, 100);
    }

    // @Darius
    public void updateHP(int hp){
        removeObject(heartCounter);
        addObject(heartCounter = new ImageDisplay(new GreenfootImage(Integer.toString(hp), 50, Color.WHITE, null)), 215, 30);
    }

    // @Andrew
    // Static method to calculate the distance between two actors
    public static double getDistance(Actor a, Actor b){
        return Math.hypot(a.getX() - b.getX(), a.getY() - b.getY());
    }

    // @Jonathan
    // Static method to check if the game is acting (active)
    public static boolean isActing(){
        return acting;
    }

    // @Andrew
    // Method to get the scroller instance for background movement
    // For scrollable world
    public Scroller getScroller(){
        return scroller;
    }
}
