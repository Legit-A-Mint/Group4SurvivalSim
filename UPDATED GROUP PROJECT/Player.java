import java.util.List;
import java.util.ArrayList;
import greenfoot.*;

/**
 * @lumilk
 * @andrew
 * @1.0.0
 */

public class Player extends Effects {
    // Manual movement mode : true -- AI movement: false
    private static final boolean DEBUGMODE = true;

    // One second --> modify with * as needed
    private static final int ONE_SECOND = 60;

    // Vfx
    private GreenfootImage[] playerImage = new GreenfootImage[1];
    private GreenfootImage[] floatyImage = new GreenfootImage[3];

    // Price of Items
    private static final int POTION_COST = 4;
    private static final int NET_COST = 8;
    private static final int SHURIKEN_COST = 15;
    private static final int HARPOON_COST = 25;
    private static final int RAFT_COST = 15;

    // Item variables
    private boolean netBought;
    private boolean shurikenBought;
    private boolean harpoonBought;
    private boolean allRaftUpgradesBought;
    private boolean doneUpgrades;

    private int weaponIndex;

    private int[] weaponCDList = new int[4];

    // Instance variables
    private double speed;
    private int hp, maxHp;
    private int cooldown;
    private int coinsStored;
    private int buyCooldown;
    private int direction; /** This will affect vfx */
    
    private long rotaCont;
    private long storeRota;
    private boolean resetRotaCont;

    // Effects
    private int poisonCounter;
    private int poisonDamage;
    private boolean poisoned;
    private static final int POISONTICKS = 60;

    // Hitbox variables
    private PlayerHitbox hitbox;
    private boolean createdHitbox;
    private int collisionCounter = 0;
    private final int MAX_COLLISION_ATTEMPTS = 3;

    private PlayerHitbox tempBox;

    // Which floating device is player using (0 = floaty, 1 = wood raft, 2 = metal boat)
    private GreenfootImage playerImg;
    private GreenfootImage tempImg;
    private int floatyNum = 0;

    // Other classes
    private SimulationWorld world;
    private Lives lives;
    private Actor target;
    protected ArrayList<Enemy> enemies;
    protected Enemy enemy;
    private Projectile projectile;
    private Fov fov;

    private int dx, dy;
    private boolean createdFovHitbox;

    public Player(String playerModel, int choosenSpeed, Lives lives) {
        // Vfx
        floatyImage[0] = new GreenfootImage("floaty.png");
        floatyImage[1] = new GreenfootImage("wood.png");
        floatyImage[2] = new GreenfootImage("metal.png");
        playerImg = new GreenfootImage(playerModel);
        setRaft(0);

        // Instance variables
        // speed = choosenSpeed;
        speed = 3;
        lives = lives;
        coinsStored = 0;
        hp = maxHp;
        resetRotaCont = true;

        weaponCDList[0] = 15;
        weaponCDList[1] = 15;
        weaponCDList[2] = 15;
        weaponCDList[3] = 15;

        createdHitbox = false;
    }

    /** try to make this added to world, if doesnt work keep it as is */
    // Create the player's hitbox
    private void createHitbox() {
        hitbox = new PlayerHitbox(playerImage[0].getWidth() - 30, playerImage[0].getHeight() / 2, 0, 0, this, 2.5);
        getWorld().addObject(hitbox, getX(), getY());
        createdHitbox = true;
    }
    
    private void createFov() {
        fov = new Fov(playerImage[0].getWidth()*6  , (int) (((double)playerImage[0].getHeight())*4.5), 0, 0, this, 2.5);
        getWorld().addObject(fov, getX() + 55, getY() + 55);
        createdFovHitbox = true;
    }

    public void act() {
        // Make a hitbox
        if (!createdHitbox){
            createHitbox();
        }
        
        if (!createdFovHitbox){
            createFov();
        }
        

        if (SimulationWorld.isActing()) {
            // pre action handling
            handleCooldowns();

            if(!DEBUGMODE){
                determineWhatToBuy();
                if(!getWorld().getObjects(Enemy.class).isEmpty()){
                    //findClosestEnemy();
                }
                if(!doneUpgrades && !getWorld().getObjects(Coins.class).isEmpty()){
                    lookForCoins();
                }

                // Movement Action

                //System.out.println("Wall ahead: " + checkForWall());
                if(!checkForWall()){
                    //if(distanceToClosestTarget(Enemy.class, 0, 100, 300) > 250){
                    if(fov.enemyDetected()){
                        resetRotaCont = true;
                        move(speed);
                    }else{
                        resetRotaCont = false;
                        rotaCont -= 2;
                        setRotation(rotaCont);
                        move(speed-1);
                    }
                }
                else{
                    //turn(15);
                }
                if(cooldown <= 0){
                    spawnProjectile(weaponIndex);
                    cooldown = weaponCDList[weaponIndex];
                }
            }
            else if(DEBUGMODE){
                handleMovement();

                if(cooldown <= 0){
                    if(Greenfoot.isKeyDown("space")){
                        spawnProjectile(weaponIndex);
                        cooldown = weaponCDList[weaponIndex];
                    }
                }
            }

            // End Action
            collectCoins();
            checkEffects();

        }
    }

    private void handleCooldowns(){
        if(cooldown > 0){
            cooldown--;
        }
    }

    private void determineWhatToBuy(){
        if((coinsStored > POTION_COST && buyCooldown <= 0) && hp < maxHp){
            buyHealthPotion();
            buyCooldown = ONE_SECOND;
            return;
        }

        // skip other ifs (optimization)
        if(!doneUpgrades){
            if(!netBought && (coinsStored > NET_COST && buyCooldown <= 0)){
                weaponIndex++;
                netBought = true;
                buyCooldown = ONE_SECOND;
                return;
            }

            if(!shurikenBought && (coinsStored > SHURIKEN_COST && buyCooldown <= 0)){
                weaponIndex++;
                shurikenBought = true;
                buyCooldown = ONE_SECOND;
                return;
            }

            if(!harpoonBought && (coinsStored > HARPOON_COST && buyCooldown <= 0)){
                weaponIndex++;
                harpoonBought = true;
                buyCooldown = ONE_SECOND;
                return;
            }
        }

        if(harpoonBought && shurikenBought && netBought && allRaftUpgradesBought){
            doneUpgrades = true;
        }
    }

    // Buy Heal
    public void buyHealthPotion() {
        coinsStored -= POTION_COST;
        hp ++; 
    }

    public void findClosestEnemy(){
        if(!getWorld().getObjects(Enemy.class).isEmpty()){
            if(findClosestTarget(Enemy.class, 150, 200, 2500) != null){
                turnTowards(findClosestTarget(Enemy.class, 150, 200, 2500));
            }
        }
    }

    public void lookForCoins(){
        if(!getWorld().getObjects(Coins.class).isEmpty()){
            if(findClosestTarget(Coins.class, 150, 200, 2500) != null){
                turnTowards(findClosestTarget(Coins.class, 150, 200, 2500));
            }
        }
    }

    public boolean checkForWall(){
        double nextX = getPreciseX() + (double) Math.round(Math.cos(Math.toRadians(getRotation()))) * speed + getImage().getWidth()
        ;
        double nextY = getPreciseY() + (double) Math.round(Math.sin(Math.toRadians(getRotation()))) * speed + getImage().getHeight()
        ;

        nextX += getImage().getWidth();
        nextY += getImage().getHeight();

        tempBox = new PlayerHitbox(playerImage[0].getWidth() - 30, playerImage[0].getHeight() / 2, 0, 0, this, 2.5);
        getWorld().addObject(tempBox,  (int)(nextX), (int)(nextY));

        Actor wall = (Actor) findClosestTarget(Hitbox.class, 0, 200, 750);

        if(wall != null && tempBox.checkIntersection(wall)){
            getWorld().removeObject(tempBox);
            return true;
        }
        getWorld().removeObject(tempBox);
        return false;
    }

    private void spawnProjectile(int type){
        switch(type){
                case(0):

                getWorld().addObject(new Spear(), getX(), getY());
                break;

                case(1):

                getWorld().addObject(new Net(), getX(), getY());
                break;

                case(2):

                getWorld().addObject(new Shuriken(), getX(), getY());
                break;

                case(3):

                getWorld().addObject(new Harpoon(), getX(), getY());
                break;

        }
    }

    private void collectCoins() {
        Actor coin = getOneIntersectingObject(Coins.class);
        if (coin != null) {
            Coins c = (Coins) coin;
            coinsStored++;  // Add coins to player
            getWorld().removeObject(c);  // Remove coin from world
        }
    }

    public void checkEffects(){
        // Check if player is poisoned or not
        if(poisoned){
            // Decrease poison counter
            poisonCounter--;

            // Ensure to only poison if poison counter is > 0
            if(poisonCounter > 0){
                // Every 20th act inflict poison damage
                if(poisonCounter % POISONTICKS == 0){
                    damageMe(poisonDamage);
                }
            }
            else{
                // Set poison counter to unreachable state
                poisonCounter = -1;
                // Reset poison
                poisoned = false;
            }
        }
    }

    public void setRaft(int num) {
        if (floatyNum == 0)
        {
            // if you're not on a raft, the floaty has to be drawn on top of you
            tempImg = new GreenfootImage(playerImg);
            tempImg.drawImage(floatyImage[floatyNum], 0, 0);
            playerImage[0] = tempImg;
        }
        else
        {
            // otherwise draw player on top of raft
            tempImg = new GreenfootImage(floatyImage[num]);
            tempImg.drawImage(playerImg, 0, 0);
            playerImage[0] = tempImg;
        }
        setImage(playerImage[0]);
    }

    // MANUAL
    private void handleMovement() {
        dx = 0;
        dy = 0;

        // Input-based movement (for manual control)
        if (Greenfoot.isKeyDown("a")) {
            dx -= speed;
            direction = 3; // Left
        }
        if (Greenfoot.isKeyDown("d")) {
            dx += speed;
            direction = 1; // Right
        }
        if (Greenfoot.isKeyDown("w")) {
            dy -= speed;
        }
        if (Greenfoot.isKeyDown("s")) {
            dy += speed;
        }

        handleCollision(dx, dy);
    }

    // Handle movement with collision detection
    private void handleCollision(double dx, double dy) {
        double futureX = getX() + dx;
        double futureY = getY() + dy;

        // Handle horizontal movement
        hitbox.setLocation(futureX, getY());
        if (!isCollidingWithHitbox()) {
            setLocation(futureX, getY());
            collisionCounter = 0; // Reset collision counter
        } else {
            resetHitboxPosition();
            handleRepel("horizontal");
        }

        // Handle vertical movement
        hitbox.setLocation(getX(), futureY);
        if (!isCollidingWithHitbox()) {
            setLocation(getX(), futureY);
            collisionCounter = 0; // Reset collision counter
        } else {
            resetHitboxPosition();
            handleRepel("vertical");
        }
    }

    // Repel the player upon collision
    private void handleRepel(String direction) {
        collisionCounter++;
        if (collisionCounter >= MAX_COLLISION_ATTEMPTS) {
            if (direction.equals("horizontal")) {
                setLocation(getX() - dx * 2, getY());
            } else if (direction.equals("vertical")) {
                setLocation(getX(), getY() - dy * 2);
            }
            collisionCounter = 0; // Reset collision counter
        }
    }
    
    public double getSpeed(){
        return speed;
    }

    // Update the hitbox position to align with the player
    private void updateHitboxPosition() {
        hitbox.setLocation(getX(), getY());
    }

    // Reset hitbox position to match the player
    private void resetHitboxPosition() {
        hitbox.setLocation(getX(), getY());
    }
    
    public Hitbox getHitbox() {
        return this.hitbox;
    }

    // Check if the hitbox is colliding with other objects
    private boolean isCollidingWithHitbox() {
        for (Hitbox other : hitbox.getIntersectingHitboxes()) {
            if (other != hitbox && other.checkCollision(hitbox)) {
                return true;
            }
        }
        return false;
    }

    // For Enemy class

    public void damageMe(int damage) {
        if (hp > 0) {
            hp -= damage;
            lives.updateDisplay(); // Update the Lives display
        }
    }

    // For Kraken class

    public void poisonMe(int damage, int ticks){        
        poisonCounter = ticks * POISONTICKS;
        poisoned = true;
        poisonDamage = damage;
    }

    // For Lives class
    public int getHp() {
        return hp;
    }

    public int getMaxHp() {
        return maxHp;
    }
}
