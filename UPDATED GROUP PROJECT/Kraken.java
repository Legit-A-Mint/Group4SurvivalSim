import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * Write a description of class Kraken here.
 * 
 * @lumilk
 * @Loziq
 * @1.0.0
 */
public class Kraken extends Enemy
{
    private boolean createdHitbox;
    private Hitbox hitbox;

    // Set max amount of krakites available to spawn
    private static final int MAX_KRAKITE_SPAWN = 10;

    //Boss music
    private GreenfootSound bossMusic;

    private double diffMulti;

    private int myActNumber;

    // Static
    private static int nextActNumber = -1;

    public Kraken(double diffMulti){
        super();
        this.diffMulti = diffMulti;
        img = new GreenfootImage[12];
        createdHitbox = false;
        hp = (int)(40*diffMulti);
        damage = (int)(10*diffMulti);
        attackCooldown = 100;
        attackTimer = 0;

        // Inherited from superclass, ensure to not get pushed around or track player
        isMovable = false;

        bossMusic = new GreenfootSound("Kraken.mp3");
        bossMusic.playLoop();

        // Set images for each position in the array
        img[0] = new GreenfootImage("KrakenF1.png");
        for(int i = 0; i < img.length; i++){
            img[i] = new GreenfootImage("KrakenF" + (i+1) + ".png");
        }

        myActNumber = getNextActNumber();
    }

    public void act()
    {
        if(attackTimer > 0){
            attackTimer--;
        }
        // Create a hitbox for me if haven't already
        createHitbox();

        // Attack radius of kraken

        if(SimulationWorld.getActNumber() == myActNumber){
            if(!getObjectsInRange(450, Player.class).isEmpty()){
                if(attackTimer == 0){
                    doRandomAttack();
                    attackTimer = attackCooldown;
                }
            }
        }

        // Remove me and related objects when I reach 0 or less HP
        if(this.hp <= 0){

            // Remove any remaining tentacles by creating an arraylist and iterating through each object
            ArrayList<Tentacle> tentacles = (ArrayList<Tentacle>)(getWorld().getObjects(Tentacle.class));
            for(Tentacle t: tentacles){
                t.damageMe(999999);
            }
            // Stops music
            bossMusic.pause();

            // Remove related objects to me
            getWorld().removeObject(hitbox);
            getWorld().removeObject(this);
        }

        // Idle animation
        animate(this, img, img[0].getWidth(), img[0].getHeight(), 24, 1);
    }

    public void tentacleAttack(){
        int maxCreatedTentacles = Greenfoot.getRandomNumber(6) + 3; // Min 3 max 8
        for(int i = 0; i < maxCreatedTentacles; i ++){

            // Generate a random angle in radians
            double angle = Math.random() * 2 * Math.PI;

            // Inner and outer spawn radius
            double distance = 100 + (Math.random() * (175));

            // Get spawnlocations seperatly for X and Y
            int spawnX = getX() + (int)(distance * Math.cos(angle));
            int spawnY = getY() + (int)(distance * Math.sin(angle));

            // Spawn in tentacle
            Tentacle tentacle = new Tentacle(diffMulti);
            getWorld().addObject(tentacle, spawnX, spawnY);
        }
    }

    public void doRandomAttack(){
        int randomNumber = Greenfoot.getRandomNumber(3); // Generate a number from 0 - 2 (inclusive)
        boolean doDoubleAttack = Greenfoot.getRandomNumber(100) < 50; // 50% chance for double attack

        // Handle each case seperatly
        switch(randomNumber) {
            case 0:

                if (getWorld().getObjects(Tentacle.class).isEmpty()) {
                    tentacleAttack();
                    if (doDoubleAttack) {
                        performAnotherAttack(1, 2); // Perform a second attack (summon or aoe)
                    }
                }
                break;

            case 1:

                summonAttack();
                if (doDoubleAttack) {
                    performAnotherAttack(0, 2); // Perform a second attack (tentacle or aoe)
                }
                break;

            case 2:

                aoeAttack();
                if (doDoubleAttack) {
                    performAnotherAttack(0, 1); // Perform a second attack (tentacle or summon)
                }
                break;

        }
    }

    // Can only execute a method that has not been executed in this attack sequence
    private void performAnotherAttack(int option1, int option2) {
        int secondaryAttack = Greenfoot.getRandomNumber(2) == 0 ? option1 : option2;
        switch (secondaryAttack) {
            case 0:

                if (getWorld().getObjects(Tentacle.class).isEmpty()) {
                    tentacleAttack();
                }
                break;

            case 1:

                summonAttack();
                break;

            case 2:

                aoeAttack();
                break;
        }
    }

    private static int getNextActNumber () {
        if (nextActNumber == -1){
            nextActNumber = 1;
        }
        if (nextActNumber > 4){
            nextActNumber = 1; // goes back to 1 - Zero (0) is reserved for UI refresh
        }
        return nextActNumber++;
    }

    public void refreshActNumber() {
        myActNumber = getNextActNumber();
    }

    public static void resetActDistribution(){
        nextActNumber = 1;
    }

    public void summonAttack(){
        int numEnemies = Greenfoot.getRandomNumber(2) + 2; // Randomly summon 3 to 5 enemies
        int summonRadius = 200;

        // Only summon if there are less than 20 Krakite instances
        if(getWorld().getObjects(Krakite.class).size() < MAX_KRAKITE_SPAWN){
            for (int i = 0; i < numEnemies; i++) {

                // Generate a random angle in radians
                double angle = Math.random() * 2 * Math.PI;

                // Maximum summon radius
                double distance = Math.random() * summonRadius;

                // Get spawnlocation seperatly for X and Y
                int spawnX = getX() + (int)(distance * Math.cos(angle));
                int spawnY = getY() + (int)(distance * Math.sin(angle));

                // Spawn in enemies
                Enemy enemy = new Krakite(); 
                getWorld().addObject(enemy, spawnX, spawnY);

            }  
        }

    }

    public void aoeAttack(){
        // Create the AOE Circle and add it to the world
        AOECircle aoe = new AOECircle(0, 500, 5, 30); 
        getWorld().addObject(aoe, getX() - 7, getY());
    }

    // Override superclass methods
    @Override
    protected void createHitbox(){
        if(!createdHitbox){
            hitbox = new CollisionHitbox((int)(img[0].getWidth()/2.5), (int)(img[0].getHeight()/2.5), 2.5);
            getWorld().addObject(hitbox, this.getX() - 7, this.getY());
            createdHitbox = true;
        }
    }

    @Override
    public void attack(){}

    @Override
    public void attackAnimation(){}

    @Override
    public void repel(){}

    @Override
    public void pushAwayFromObjects(ArrayList<Actor> nearbyObjects, double minDistance){}
}
