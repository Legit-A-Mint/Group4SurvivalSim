import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Enemies here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Enemy extends Effects
{
    protected int spawnX, spawnY, direction;
    protected double[] coordinates = new double[2];
    protected double speed;
    protected Player player;
    
    protected double relativeX, speedX, relativeY, speedY;
    
    private static final int MAX_SPAWN_DISTANCE = 350;
    private static final int MIN_SPAWN_DISTANCE = 100;
    
    public Enemy(){
        
    }
    
    
    public void act()
    {
        lookForTarget();
    }
    
    public void lookForTarget(){
        if(!getWorld().getObjects(Player.class).isEmpty()){
            player = getWorld().getObjects(Player.class).get(0);
            turnTowards(player.getX(), player.getY());
            move(speed);
        }
    }
}
