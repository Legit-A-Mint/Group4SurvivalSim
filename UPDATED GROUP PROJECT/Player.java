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
    private static final boolean DEBUGMODE = false;

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
    private double turnSpeed;
    private int hp, maxHp;
    private int cooldown;
    private int coinsStored;
    private int buyCooldown;
    private int direction; /** This will affect vfx */

    private long rotaCont;
    // private long storeRota;
    private boolean resetRotaCont;
    private long coinRota;

    private long enemyRota;

    private int smartDodgeCounter;

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
    private Fov fov2;

    private int dx, dy;
    private boolean createdFovHitbox;
    private boolean lookingForCoins;
    private boolean smartDodge;
    private boolean foundEnemy;
    private int overSwing;
    private boolean commitTurn;
    private int turnOver;
    private boolean createdFovHitboxTwo;
    private long collisionHitboxRota;

    public Player(String playerModel, double speedMulti, Lives lives) {
        // Vfx
        floatyImage[0] = new GreenfootImage("floaty.png");
        floatyImage[1] = new GreenfootImage("wood.png");
        floatyImage[2] = new GreenfootImage("metal.png");
        playerImg = new GreenfootImage(playerModel);
        setRaft(0);

        // Instance variables
        // speed = speed*speedMulti;
        speed = 3.5;
        turnSpeed = 2.5;
        lives = lives;
        coinsStored = 0;
        hp = maxHp;
        resetRotaCont = true;
        smartDodgeCounter = -1;
        overSwing = -1;

        weaponCDList[0] = 45;
        weaponCDList[1] = 45;
        weaponCDList[2] = 45;
        weaponCDList[3] = 45;

        createdHitbox = false;
        enableStaticRotation();
    }

    /** try to make this added to world, if doesnt work keep it as is */
    // Create the player's hitbox
    private void createHitbox() {
        hitbox = new PlayerHitbox(playerImage[0].getWidth() - 30, playerImage[0].getHeight() / 2, 0, 0, this, 2.5, true);
        getWorld().addObject(hitbox, getX(), getY());
        createdHitbox = true;
    }

    private void createFov() {
        fov = new Fov(playerImage[0].getWidth()*6  , (int) (((double)playerImage[0].getHeight())*4.5), 0, 0, this, 2.5, 3.745, 3.69, 1.3, 1.2);
        getWorld().addObject(fov, getX() + 55, getY() + 55);
        createdFovHitbox = true;
    }
    
    private void createFov2() {
        fov2 = new Fov(playerImage[0].getWidth()*6  , (int) (((double)playerImage[0].getHeight())*4.5), 0, 0, this, 2.5, 1.2, 1.2, 0.2 , 0.2);
        getWorld().addObject(fov2, getX() + 55, getY() + 55);
        createdFovHitboxTwo = true;
    }

    public void act() {
        // Make a hitbox
        if (!createdHitbox){
            createHitbox();
        }

        if (!createdFovHitbox){
            createFov();
        }
        
        if (!createdFovHitboxTwo){
            createFov2();
        }

        if (SimulationWorld.isActing()) {
            // pre action handling
            handleCooldowns();
            turnOver--;

            if(smartDodgeCounter >= 0){
                smartDodge = true;
            }else{
                smartDodge = false;
            }

            if(!DEBUGMODE){
                determineWhatToBuy();

                if(!getWorld().getObjects(Enemy.class).isEmpty()){
                    //findClosestEnemy();
                }

                // Movement Action
                //System.out.println(checkForWall());

                if(!checkForWall()){
                    //if(distanceToClosestTarget(Enemy.class, 0, 100, 300) > 250){
                        
                    /**
                    if(fov.enemyNotDetected()){
                        smartDodgeCounter--;
                    }else{
                        smartDodgeCounter = ONE_SECOND/20;
                    }
                    */
                    
                    if(fov2.wallNotDetected()){
                        if(/*!smartDodge || */ !findClosestEnemy()){
                            if(!doneUpgrades && !getWorld().getObjects(Coins.class).isEmpty()){
                                lookForCoins();
                                if(lookingForCoins){
                                    long rotationDiff = coinRota - getRotation();
    
                                    //System.out.println("money"); 
    
                                    if(rotationDiff >= 360){
                                        rotationDiff =  0 + (rotationDiff - 360);
                                    }
    
                                    if(rotationDiff <= 0){
                                        rotationDiff =  0 + (rotationDiff + 360); 
                                    }
    
                                    //System.out.println(rotationDiff);
                                    //System.out.println("this rotation: " + rotaCont);
                                    if(rotationDiff > 0 && rotationDiff < 180){
                                        rotaCont += turnSpeed;
                                    }
                                    if(rotationDiff > 179 && rotationDiff <= 360){
                                        rotaCont -= turnSpeed;
                                    }
                                }
                            }
                            resetRota();
                            setRotation(rotaCont);
                            move(speed);
                        }else{
    
                            long rotationDiff = enemyRota - getRotation();
                            rotationDiff += 180;
    
                            if(rotationDiff >= 360){
                                rotationDiff =  0 + (rotationDiff - 360);
                            }
    
                            if(rotationDiff <= 0){
                                rotationDiff =  0 + (rotationDiff + 360); 
                            }
    
                            // System.out.println(rotationDiff);
    
                            if(rotationDiff > 0 && rotationDiff < 180){
                                rotaCont += turnSpeed;
                            }
                            if(rotationDiff > 179 && rotationDiff <= 360){
                                rotaCont -= turnSpeed;
                            }
    
                            resetRota();
                            setRotation(rotaCont);
                            move(speed-0.5);
                        }
                    }else{
                        rotaCont -= turnSpeed;
                        resetRota();
                        setRotation(rotaCont);
                        move(speed-0.5);
                    }
                }else{
                    if(false/*isThereACloseEnemy()*/){
                        
                        /*
                        System.out.println(commitTurn);
                        if(!commitTurn){
                            findClosestEnemy();
                        }
                        
                        
                        long rotationDiff = enemyRota - getRotation();
                        rotationDiff += 180;

                        if(rotationDiff >= 360){
                            rotationDiff =  0 + (rotationDiff - 360);
                        }

                        if(rotationDiff <= 0){
                            rotationDiff =  0 + (rotationDiff + 360); 
                        }

                        System.out.println(rotationDiff);
                        
                        if(rotationDiff > 0 && rotationDiff < 180 && !commitTurn){
                            overSwing = ONE_SECOND/15;
                            commitTurn = true;
                            turnOver = Math.abs(overSwing) + 7;
                        }
                        if(rotationDiff > 179 && rotationDiff <= 360 && !commitTurn){
                            overSwing = -ONE_SECOND/15;
                            commitTurn = true;
                            //rotaCont -= turnSpeed*2;
                            turnOver = Math.abs(overSwing) + 7;
                        }
                        
                        if(overSwing > 0){
                            rotaCont += turnSpeed*2;
                            overSwing--;
                        }else{
                            rotaCont -= turnSpeed*2;
                            overSwing++;
                        }
                        
                        if(turnOver <= 0){
                            commitTurn = false;
                        }
                        */
                        
                        
                        rotaCont += turnSpeed*2;
                        System.out.println("Current Rotation: " + rotaCont);
                        resetRota();
                        setRotation(rotaCont);
                    }else{
                        setCollisionRotation();
                        resetRota();
                        setRotation(rotaCont);
                        move(speed);
                    }
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
    
    private void setCollisionRotation(){
        long storeRota = getRotation();
        
        if(!getWorld().getObjects(CollisionHitbox.class).isEmpty()){
            if(findClosestTarget(CollisionHitbox.class, 0, 150, 550) != null){
                turnTowards(findClosestTarget(CollisionHitbox.class, 0, 200, 550));
                collisionHitboxRota = getRotation();

                if(collisionHitboxRota <= 0){
                    collisionHitboxRota = 0 + (collisionHitboxRota + 360);
                }
                if(collisionHitboxRota >= 360){
                    collisionHitboxRota = 0 + (collisionHitboxRota - 360);
                }
            }else{
                setRotation(storeRota);
                return;
            }            
        }
        if((collisionHitboxRota > 0 && collisionHitboxRota <= 45) || (collisionHitboxRota > 135 && collisionHitboxRota <= 180)){
            rotaCont = 270;
        }
        
        if((collisionHitboxRota > 180 && collisionHitboxRota <= 225) || (collisionHitboxRota > 315 && collisionHitboxRota <= 360)){
            rotaCont = 90;
        }
        
        if((collisionHitboxRota > 45 && collisionHitboxRota <= 90) || (collisionHitboxRota > 270 && collisionHitboxRota <= 315)){
            rotaCont = 180;
        }
        
        if((collisionHitboxRota > 90 && collisionHitboxRota <= 135) || (collisionHitboxRota > 225 && collisionHitboxRota <= 270)){
            rotaCont = 0;
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

    public void resetRota(){
        if(rotaCont >= 360){
            rotaCont =  0 + (rotaCont - 360);
        }

        if(rotaCont <= 0){
            rotaCont =  0 + (rotaCont + 360); 
        }
    }

    // Buy Heal
    public void buyHealthPotion() {
        coinsStored -= POTION_COST;
        hp ++; 
    }

    public boolean findClosestEnemy(){
        long storeRota = getRotation();

        if(!getWorld().getObjects(Enemy.class).isEmpty()){
            if(findClosestTarget(Enemy.class, 0, 150, 550) != null){
                turnTowards(findClosestTarget(Enemy.class, 0, 200, 550));
                enemyRota = getRotation();

                if(enemyRota <= 0){
                    enemyRota = 0 + (enemyRota + 360);
                }
                if(coinRota >= 360){
                    enemyRota = 0 + (enemyRota - 360);
                }
                foundEnemy = true;
            }else{
                foundEnemy = false;
            }
        }
        setRotation(storeRota);
        if(foundEnemy){
            return true;
        }
        return false;
    }
    
    public boolean isThereACloseEnemy(){
        if(!getWorld().getObjects(Enemy.class).isEmpty()){
            if(findClosestTarget(Enemy.class, 0, 70, 400) != null){
                return true;
            }else{
                return false;
            }
        }
        return false;
    }

    public void lookForCoins(){
        long storeRota = getRotation();

        if(!getWorld().getObjects(Coins.class).isEmpty()){
            if(findClosestTarget(Coins.class, 150, 200, 2500) != null){
                turnTowards(findClosestTarget(Coins.class, 150, 200, 2500));
                coinRota = getRotation();

                if(coinRota <= 0){
                    coinRota = 0 + (coinRota + 360);
                }
                if(coinRota >= 360){
                    coinRota = 0 + (coinRota - 360);
                }

                lookingForCoins = true;
            }
        }else{
            lookingForCoins = false;
        }

        setRotation(storeRota);
    }

    public boolean checkForWall(){
        double nextX = getPreciseX() + (double) Math.round(Math.cos(Math.toRadians(getRotation()))) * speed*2.1;
        double nextY = getPreciseY() + (double) Math.round(Math.sin(Math.toRadians(getRotation()))) * speed*2.1;

        tempBox = new PlayerHitbox(playerImage[0].getWidth() - 24, (int)(playerImage[0].getHeight() /1.9), (int) nextX, (int) nextY, this, 2.5, false);

        // System.out.println(nextX);
        // System.out.println(nextY);

        getWorld().addObject(tempBox,  (int)(nextX), (int)(nextY));

        // System.out.println(tempBox.getX());
        // System.out.println(tempBox.getY());

        Actor wall = (Actor) findClosestTarget(CollisionHitbox.class, 0, 400, 750);

        //System.out.println(tempBox.checkIntersection(wall));
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
