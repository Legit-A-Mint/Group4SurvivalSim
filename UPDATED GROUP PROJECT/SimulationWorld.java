import greenfoot.*;  // Import Greenfoot classes for world, actors, images, and sound
import java.util.*;  // Import utility classes such as List

/*
 * Full SimulationWorld
 * @Darius
 * @Logan
 * @Jonathan
 */

public class SimulationWorld extends World {
    // Declaration of instance variables
    public Scroller scroller; // The scroll controller to manage the background
    private Player player; // This is the player actor
    private Lives lives; // This displays the amount of lives you have
    private int waveCount, actCount; // Tracks and counts the number of waves and actions
    private boolean spawnOnce, countOnce; // Used for one-time actions
    public static int killCount; // Counts for the kills across the game
    public static double diffMulti; // This multiplies the difficulty of the simulation
    // Used for the world size and maximum spawn distance
    private static final int MAX_SPAWN_DISTANCE = 200;
    private static final int WIDTH = 2000, height = 2000;
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

    // Constructor for the world, initializes objects
    public SimulationWorld(String playerModel, int maxLives, int speed, double difficulty) {
        super(1024, 576, 1, false); // Create world with size 1024x576 pixels

        // Initialize game variables
        waveCount = 0;  // Start at wave 0
        actCount = 0; // Start at action count 0
        spawnOnce = true; // The flag for spawning 
        delay = 30; // The delay variable

        acting = true; // Set the game to be active and running

        coinSpawnTimer = 0;  // Initialize coin spawn timer

        diffMulti = difficulty; // Set difficulty multiplier

        // Add lives display at top of screen
        addObject(lives = new Lives("Heart", 512, 60, maxLives), WIDTH / 2, 100);
        // Add scroller for background movement
        addObject(scroller = new Scroller(this, new GreenfootImage("water.png"), WIDTH, height));
        // Add player to the center of the screen
        addObject(player = new Player(playerModel, speed, lives), this.getWidth() / 2, this.getHeight() / 2);

        // Add border hitboxes to prevent the player from going outside the world
        addObject(new Hitbox(WIDTH, 100, 2.5), WIDTH / 2, height); // Bottom border
        addObject(new Hitbox(WIDTH, 100, 2.5), WIDTH / 2, 0); // Top border
        addObject(new Hitbox(100, height, 2.5), WIDTH, height / 2); // Right border
        addObject(new Hitbox(100, height, 2.5), 0, height / 2); // Left border

        // Add islands to the world
        addObject(new Island(new GreenfootImage("island.png")), 500 - getScroller().getScrolledX(), 500 - getScroller().getScrolledY());
        addObject(new Island(new GreenfootImage("island.png")), 600 - getScroller().getScrolledX(), 1750 - getScroller().getScrolledY());
        addObject(new Island(new GreenfootImage("island.png")), 1750 - getScroller().getScrolledX(), 900 - getScroller().getScrolledY());
        addObject(new Island(new GreenfootImage("island.png")), 1500 - getScroller().getScrolledX(), 250 - getScroller().getScrolledY());
        addObject(new Island(new GreenfootImage("island.png")), 1200 - getScroller().getScrolledX(), 1500 - getScroller().getScrolledY());

        // Add GUI elements like pause button and slider
        addObject(pauseButton = new Button("PauseButton", new String[] {"db_1.png", "db_2.png", "db_3.png"}, true, 1, 55, 35), 55, 35);
        addObject(slider = new Slider("TestSlider", "rail.png", "circle.png", 1, 130, 155, 540), 155, 540);

        // Add a label to display the wave number
        waveLabel = new Label("Wave " + (waveCount + 1), 40);  // Initialize label to show "Wave 1"
        addObject(waveLabel, 200, 25);  // Position the wave label on the screen
    }

    // Method to get the scroller instance for background movement
    public Scroller getScroller() {
        return scroller;
    }

    // Method that gets called when the world is added to the Greenfoot environment
    public void addedToWorld() {
        // Start the ambient sound in a loop when the world is active
        ambientSound.playLoop();
    }

    // Method that gets called when the world starts
    public void started() {
        // Start the ambient sound in a loop when the game starts
        ambientSound.playLoop();
    }

    // Method that gets called when the world stops / is paused
    public void stopped() {
        // Stop the ambient sound when the game is paused
        ambientSound.pause();
    }

    // Empty addObject method
    public void addObject(Actor a) {
        // This empty method prevents other addObject calls from being overridden
    }

    // Main act method that runs on every frame of the game
    public void act() {
        if (acting) {
            actCount++;  // The increment action count

            // Update coin spawn timer
            coinSpawnTimer++;

            // If 30 seconds (900 ticks) have passed, spawn 5 coins
            if (coinSpawnTimer >= 450) {  // 450 ticks = 15 seconds
                spawnCoins();
                coinSpawnTimer = 0;  // Reset coin spawn timer after spawning coins
            }

            // Handle enemy waves
            handleWaves();

            // Check if the player has enough coins to unlock new items (Spear or Raft)
            checkForPurchases();

            // Scroll the background to follow the player
            scroller.scroll(getWidth() / 2 - player.getX(), getHeight() / 2 - player.getY(), this, (ArrayList<SuperSmoothMover>) (getObjects(SuperSmoothMover.class)));
        }

        // Check if the pause button is clicked
        if (Greenfoot.mouseClicked(pauseButton)) {
            acting = !acting;  // Toggle the game state between active and paused
            if (acting) {
                ambientSound.playLoop();  // Resume ambient sound
            } else {
                ambientSound.pause();  // Pause ambient sound
            }
        }

        // Check if Kraken is defeated and the wave count is 14 or higher before transitioning to the winning screen
        if (isKrakenDefeated() && waveCount >= 3) {
            WinningScreen();  // Transition to the winning screen only if the Kraken is defeated after wave 14
        }
    }

    // Method to check if the player has enough coins to buy certain items
    private void checkForPurchases() {
        if (player.getCoins() >= 100 && !player.hasSpear()) {
            player.unlockSpearUI();  // Unlock the Spear UI if the player has 100 coins
        }
        if (player.getCoins() >= 200 && !player.hasRaft()) {
            player.unlockRaftUI();   // Unlock the Raft UI if the player has 200 coins
        }
        if (player.getHp() < 50 && player.getCoins() >= 50 && !player.hasHeal()) {
            player.buyHeal();  // Heal the player if they have less than 50 HP and at least 50 coins
        }
    }

    // Handle the spawning of enemies based on the wave count
    private void handleWaves() {
        // If no enemies are left, increment the wave count and spawn new enemies
        if (getObjects(Enemy.class).isEmpty()) {
            waveCount++;  // Increment the wave count
            spawnEnemiesForWave(waveCount);  // Spawn enemies based on the current wave count

            // Check if it's wave 15, and spawn the Kraken if it is
            if (waveCount == 3 && !createdKraken) {  // Wave 5 is waveCount 4 (0-based)
                spawnKraken();  // Spawn the Kraken
                createdKraken = true;  // Set flag to prevent Kraken from spawning again
            }
        }
    }

        // Add a method to spawn the Kraken
    private void spawnKraken() {
        // Create a Kraken actor at a random position
        Kraken kraken = new Kraken();  // Instantiate the Kraken actor
        int spawnX = Greenfoot.getRandomNumber(WIDTH);  // Random X position within world width
        int spawnY = Greenfoot.getRandomNumber(height);  // Random Y position within world height
        addObject(kraken, spawnX, spawnY);  // Add Kraken to the world at the random position
    }

    
    private void spawnEnemy(Class<? extends Actor> enemyClass) {
        // Generate random spawn coordinates within the world boundaries
        int spawnX = Greenfoot.getRandomNumber(WIDTH);  // Random X position within world width
        int spawnY = Greenfoot.getRandomNumber(height);  // Random Y position within world height
        
        try {
            // Instantiate the enemy class dynamically and add it to the world
            Actor enemy = enemyClass.getDeclaredConstructor().newInstance();
            addObject(enemy, spawnX, spawnY);  // Add the enemy to the world at the generated position
        } catch (Exception e) {
            e.printStackTrace();  // Handle any exceptions that occur during reflection or instantiation
        }
    }
    
    private void spawnEnemiesForWave(int waveCount) {
        spawnEnemy(Bass.class);
        spawnEnemy(Whale.class);
        spawnEnemy(Swordfish.class);
        spawnEnemy(Krakite.class);
        spawnEnemy(Shark.class);
    } 
    
    // Method to spawn coins
    private void spawnCoins() {
        int x = Greenfoot.getRandomNumber(WIDTH); // Random x-coordinate
        int y = Greenfoot.getRandomNumber(height); // Random y-coordinate
        addObject(new Coins(), x, y); // Add coin at the random position
    }

    // Method to check if the Kraken is defeated
    private boolean isKrakenDefeated() {
        return getObjects(Kraken.class).isEmpty();  // Return true if the Kraken has been defeated
    }

    // Transition to the winning screen
    private void WinningScreen() {
        // Transition logic for winning the game after defeating the Kraken in wave 14
        Greenfoot.setWorld(new WinningScreen());
    }

    // Helper method to create a new instance of a given class
    private Actor newInstance(Class<? extends Actor> clazz) {
        try {
            return clazz.getDeclaredConstructor().newInstance();  // Create a new instance using reflection
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    // Override to add objects with precise coordinates (not currently used)
    public void addObject(Actor object, double x, double y) {
        super.addObject(object, (int) (x + 0.5), (int) (y + 0.5));
    }

    // Static method to calculate the distance between two actors
    public static double getDistance(Actor a, Actor b) {
        return Math.hypot(a.getX() - b.getX(), a.getY() - b.getY());
    }

    // Static method to check if the game is acting (active)
    public static boolean isActing() {
        return acting;
    }

    // Increment the kill count and print a message
    public static void addkillCount() {
        System.out.println("+1");
        killCount++;
    }

    // Method to get the current kill count based on certain thresholds
    public static int getKillCount() {
        if (killCount < 5) {
            return 0;
        } else if (killCount < 25) {
            return 1;
        } else {
            return 2;
        }
    }
}
