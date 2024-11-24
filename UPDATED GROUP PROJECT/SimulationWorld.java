import greenfoot.*;  // Import Greenfoot classes for world, actors, images, and sound
import java.util.*;  // Import utility classes such as List

/**
 * Full SimulationWorld
 * @Darius
 * @Logan
 * @Jonathan
 */

public class SimulationWorld extends World{
    // Declaration of instance variables
    public Scroller scroller; // The scroll controller to manage the background
    private Player player; // This is the player actor
    private Lives lives; // This displays the amount of lives you have
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
    private Button pauseButton; // The pause button for the gameplay
    private Slider slider; // The slider shown on the bottom left
    private static boolean acting; // Used to check if the game is active and running

    private int coinSpawnTimer;  // Timer to track coin spawning time
    private int coinDisplay; // Used to track how many coins to display

    // Background ambient sound
    public static GreenfootSound ambientSound = new GreenfootSound("gentle_Ocean.mp3");

    private Label waveLabel; // Label to display the current wave number
    private boolean createdKraken; // The flag for Kraken 

    // Variables for handling waves
    private int waveCount;
    private boolean firstWave;
    private static final int FINAL_WAVE = 20;

    // Ambient class spawnage
    private int seagullTimer;
    private static final int SEAGULL_SPAWN_TIME = 500;

    // Constructor for the world, initializes objects
    public SimulationWorld(String playerModel, int maxLives, double speed, double difficulty){
        super(1024, 576, 1, false); // Create world with size 1024x576 pixels

        // Initialize game variables
        waveCount = 0;  // Start at wave 0
        firstWave = true; // Ensure start at first wave
        actCount = 0; // Start at action count 0
        spawnOnce = true; // The flag for spawning 
        delay = 30; // The delay variable

        acting = true; // Set the game to be active and running

        coinSpawnTimer = 0;  // Initialize coin spawn timer

        diffMulti = difficulty; // Set difficulty multiplier

        // Add scroller for background movement
        addObject(scroller = new Scroller(this, new GreenfootImage("water.png"), WIDTH, HEIGHT));

        // Add player to the center of the screen
        addObject(player = new Player(playerModel, speed, lives), this.getWidth() / 2, this.getHeight() / 2);

        // Add border hitboxes to prevent the player from going outside the world
        addObject(new CollisionHitbox(WIDTH, 100, 2.5), WIDTH / 2, HEIGHT); // Bottom border
        addObject(new CollisionHitbox(WIDTH, 100, 2.5), WIDTH / 2, 0); // Top border
        addObject(new CollisionHitbox(100, HEIGHT, 2.5), WIDTH, HEIGHT / 2); // Right border
        addObject(new CollisionHitbox(100, HEIGHT, 2.5), 0, HEIGHT / 2); // Left border

        // Add islands to the world
        addObject(new Island(new GreenfootImage("island.png")), 500 - getScroller().getScrolledX(), 500 - getScroller().getScrolledY());
        addObject(new Island(new GreenfootImage("island.png")), 600 - getScroller().getScrolledX(), 2000 - getScroller().getScrolledY());
        addObject(new Island(new GreenfootImage("island.png")), 1750 - getScroller().getScrolledX(), 900 - getScroller().getScrolledY());
        addObject(new Island(new GreenfootImage("island.png")), 1500 - getScroller().getScrolledX(), 250 - getScroller().getScrolledY());
        addObject(new Island(new GreenfootImage("island.png")), 1200 - getScroller().getScrolledX(), 1500 - getScroller().getScrolledY());
        addObject(new Island(new GreenfootImage("island.png")), 2000 - getScroller().getScrolledX(), 2000 - getScroller().getScrolledY());

        // Add GUI elements like pause button and slider
        addObject(pauseButton = new Button("PauseButton", new String[]{"db_1.png", "db_2.png", "db_3.png"}, true, 1), 55, 35);
        addObject(slider = new Slider("TestSlider.", "rail.png", "circle.png", 1, 130), 180, getHeight() - 50);  
        // Add a label to display the wave number
        // waveLabel = new Label("Wave " + (waveCount + 1), 40, 200, 25);  // Initialize label to show wave
        // addObject(waveLabel, 200, 25);  // Position the wave label on the screen

        addObject(lives = new Lives(), getWidth()/2 - 290, 30);
        setPaintOrder(Seagull.class, Lives.class, Interface.class, Projectile.class);

    }
    // Method that gets called when the world is added to the Greenfoot environment
    public void addedToWorld ()
    {
        // Plays the ambient noise in a loop
        ambientSound.playLoop(); 
    }

    // Method to get the scroller instance for background movement
    public Scroller getScroller(){
        return scroller;
    }

    // Method that gets called when the world starts
    public void started(){
        // Start the ambient sound in a loop when the game starts
        ambientSound.playLoop();
    }

    // Method that gets called when the world stops / is paused
    public void stopped(){
        // Stop the ambient sound when the game is paused
        ambientSound.pause();
    }

    // Empty addObject method    
    public void addObject(Actor a){
        // This empty method prevents other addObject calls from being overridden
    }

    // Main act method that runs on every frame of the game
    public void act(){
        if (acting){
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
        if (Greenfoot.mouseClicked(pauseButton)){
            acting = !acting;  // Toggle the game state between active and paused
            if (acting){
                ambientSound.playLoop();  // Resume ambient sound
            } else{
                ambientSound.pause();  // Pause ambient sound
            }
        }
    }

    private void handleWaves(){
        // Begin with first wave always
        if(firstWave){
            //System.out.println("Current wave: " + waveCount);
            startWave(0);
            firstWave = false;
        }
        else if(!firstWave && this.getObjects(Enemy.class).isEmpty() && waveCount < FINAL_WAVE){
            // Increment wave count to change wave
            waveCount++;
            startWave(waveCount);
            //System.out.println("Current wave: " + waveCount);
        }
    }

    private void startWave (int waveCount){ 
        // Spawn different enemies in different waves

        // Edit the values in spawnEnemies parameter to change spawn amount of each enemy
        switch(waveCount){

                case(0):

                spawnKraken();
                spawnEnemies(3, 0, 0, 0);
                break;

                case(1):

                spawnEnemies(5, 1, 0, 0);
                break;

                case(2):

                spawnEnemies(7, 3, 0, 0);
                break;

                case(3):

                spawnEnemies(10, 3, 1, 0);
                break;

                case(4):

                spawnEnemies(15, 6, 3, 0);
                break;

                case(5):

                spawnEnemies(16, 8, 4, 0);
                break;

                case(6):

                spawnEnemies(4, 3, 2, 1);
                break;

                case(7):

                spawnEnemies(40, 0, 0, 0);
                break;

                case(8):

                spawnEnemies(1, 2, 3, 2);
                break;

                case(9):

                spawnEnemies(1, 2, 3, 4);
                break;

                case(10):

                spawnEnemies(10, 10, 10, 5);
                break;

                case(11):

                spawnEnemies(0, 0, 0, 15);
                break;

                // Boss fight
                case(FINAL_WAVE):

                spawnKraken();
                break;

        }        
    }

    private void spawnEnemies(int numBass, int numShark, int numWhale, int numSwordfish){
        for (int i = 0; i < numBass; i++){
            int spawnX = WIDTH / 2 + getScroller().getScrolledX();
            int spawnY = HEIGHT / 2 + getScroller().getScrolledY();
            addObject(new Bass(), spawnX, spawnY);
        }
        for (int i = 0; i < numShark; i++){
            int spawnX = WIDTH / 2 + getScroller().getScrolledX();
            int spawnY = HEIGHT / 2 + getScroller().getScrolledY();
            addObject(new Shark(), spawnX, spawnY);
        }
        for (int i = 0; i < numWhale; i++){
            int spawnX = WIDTH / 2 + getScroller().getScrolledX();
            int spawnY = HEIGHT / 2 + getScroller().getScrolledY();
            addObject(new Whale(), spawnX, spawnY);
        }
        for (int i = 0; i < numSwordfish; i++){
            int spawnX = WIDTH / 2 + getScroller().getScrolledX();
            int spawnY = HEIGHT / 2 + getScroller().getScrolledY();
            addObject(new Swordfish(), spawnX, spawnY);
        }
    }

    /*
    // Supplier to reduce redundancy
    // Spawn certain # enemies depending on the parameters (in order)
    private void spawnEnemies(int numBass, int numShark, int numWhale, int numSwordfish){
    // Pass each constructor as supplier
    spawnEnemyType(numBass, Bass::new);
    spawnEnemyType(numShark, Shark::new);
    spawnEnemyType(numWhale, Whale::new);
    spawnEnemyType(numSwordfish, Swordfish::new);
    }

    // Helper method for spawnEnemies
    private void spawnEnemyType(int count, Supplier<Enemy> enemySupplier) {
    for (int i = 0; i < count; i++){
    int spawnX = WIDTH / 2 + getScroller().getScrolledX();
    int spawnY = HEIGHT / 2 + getScroller().getScrolledY();
    // Gets the type of enemy from parameter
    addObject(enemySupplier.get(), spawnX, spawnY);
    }
    }*/

    // Add a method to spawn the Kraken
    private void spawnKraken(){
        // Create a Kraken actor at a random position
        Kraken kraken = new Kraken();  // Instantiate the Kraken actor
        int spawnX = WIDTH/2 + getScroller().getScrolledX();
        int spawnY = HEIGHT/2 + getScroller().getScrolledY();
        System.out.println("Added new kraken @ (" + spawnX + ", " + spawnY + ")");
        addObject(kraken, spawnX, spawnY);
    }

    // Spawn ambient seagulls
    private void spawnSeagull(){
        seagullTimer = SEAGULL_SPAWN_TIME;

        int spawnSide = Greenfoot.getRandomNumber(2) * 2 - 1;
        int spawnY = Greenfoot.getRandomNumber(HEIGHT - 500) +  getScroller().getScrolledY();
        System.out.println("Added new SEAGULL @ (" + (spawnSide * WIDTH + getScroller().getScrolledX())  + ", " + spawnY + ")");
        addObject(new Seagull(true, -spawnSide, 2), spawnSide * WIDTH + getScroller().getScrolledX() , spawnY);
        
    }

    
    // Method to check if the Kraken is defeated
    private boolean isKrakenDefeated(){
        return getObjects(Kraken.class).isEmpty();  // Return true if the Kraken has been defeated
    }

    // Transition to the winning screen
    private void WinningScreen(){
        // Transition logic for winning the game after defeating the Kraken in wave 20
        Greenfoot.setWorld(new WinningScreen());
    }

    // Override to add objects with precise coordinates (not currently used)
    public void addObject(Actor object, double x, double y){
        super.addObject(object, (int) (x + 0.5), (int) (y + 0.5));
    }

    // Static method to calculate the distance between two actors
    public static double getDistance(Actor a, Actor b){
        return Math.hypot(a.getX() - b.getX(), a.getY() - b.getY());
    }

    // Static method to check if the game is acting (active)
    public static boolean isActing(){
        return acting;
    }
}
