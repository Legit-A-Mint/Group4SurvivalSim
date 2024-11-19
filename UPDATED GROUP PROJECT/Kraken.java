import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * Write a description of class Kraken here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Kraken extends Enemy
{
    private boolean isInitialized;
    private boolean createdHitbox;
    private Hitbox hitbox;
    public Kraken(){
        super();
        img = new GreenfootImage[12];
        createdHitbox = false;
        isMovable = false;
        hp = 100;
        damage = 5;

        img[0] = new GreenfootImage("KrakenF1.png");
        //Set the array size to 5 so animation works
        for(int i = 0; i < img.length; i++){
            img[i] = new GreenfootImage("KrakenF" + (i+1) + ".png");
        }
        isInitialized = false;
    }

    public void act()
    {
        super.act();
        System.out.println(this.getX()+ ", " + this.getY());
        animate(this, img, img[0].getWidth(), img[0].getHeight(), 24, 1);
    }

    public void tentacleAttack(){
        int maxCreatedTentacles = Greenfoot.getRandomNumber(6) + 3; // Min 3 max 8
        for(int i = 0; i < maxCreatedTentacles; i ++){

            // Generate a random angle in radians
            double angle = Math.random() * 2 * Math.PI;

            double distance = 100 + (Math.random() * (175)); //Inner and outer spawn radius

            int spawnX = getX() + (int)(distance * Math.cos(angle));
            int spawnY = getY() + (int)(distance * Math.sin(angle));

            // Spawn in tentacle
            Tentacle tentacle = new Tentacle();
            getWorld().addObject(tentacle, spawnX, spawnY);
        }
    }

    private void summonAttack(){

    }

    private void aoeAttack(){

    }

    @Override
    protected void createHitbox(){
        if(!createdHitbox){
            hitbox = new Hitbox((int)(img[0].getWidth()/2.5), (int)(img[0].getHeight()/2.5), 2.5);
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
