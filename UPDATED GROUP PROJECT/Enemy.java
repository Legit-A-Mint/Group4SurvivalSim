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
    protected Scroller scroller;
    
    protected double relativeX, speedX, relativeY, speedY;
    
    private static final int MAX_SPAWN_DISTANCE = 350;
    private static final int MIN_SPAWN_DISTANCE = 100;
    
    public Enemy(MyWorld thisWorld){
        scroller = thisWorld.getScroller();
        
        if(Greenfoot.getRandomNumber (2) % 2 == 0){
            spawnX  = Greenfoot.getRandomNumber (MAX_SPAWN_DISTANCE) + MIN_SPAWN_DISTANCE;
            spawnY = Greenfoot.getRandomNumber (MAX_SPAWN_DISTANCE) + MIN_SPAWN_DISTANCE;
        }else{
            spawnX  = - Greenfoot.getRandomNumber (MAX_SPAWN_DISTANCE) + MIN_SPAWN_DISTANCE;
            spawnY = - Greenfoot.getRandomNumber (MAX_SPAWN_DISTANCE) + MIN_SPAWN_DISTANCE;
        }
        
        coordinates[0] = spawnX;
        coordinates[1] = spawnY;
    }
    
    
    public void act()
    {
        //turnTowards(scroller.getScrolledX(), scroller.getScrolledY());
        
        
        move(1);
        
        moveWithWorld();
        System.out.println(coordinates[0]);
        System.out.println(coordinates[1]);

    }
    
    public void moveWithWorld(){
        if(!getWorld().getObjects(Scroller.class).isEmpty()){
            Scroller sw = getWorld().getObjects(Scroller.class).get(0);
            
            System.out.println("scrolled X" + sw.getScrolledX());
            System.out.println("scrolled Y" + sw.getScrolledY());
            
            coordinates[0] += sw.getScrolledX();
            coordinates[1] += sw.getScrolledY();
        }
        
        lookForTarget();
        //setLocation(coordinates[0], coordinates[1]);
    }
    
    
    public void lookForTarget(){
        if(!getWorld().getObjects(Player.class).isEmpty()){
            player = getWorld().getObjects(Player.class).get(0);
            turnTowards(player.getX(), player.getY());
            move(speed);
            
            // OLD CODE ^^ SIMPLE VERSION ABOVE
            /**
            if(player.getX() < this.getX() && player.getY() < this.getY()){
                System.out.println("Quadrant 2 relative to player");
                coordinates[0] -= speed;
                coordinates[1] -= speed;
            }else if (player.getX() > this.getX() && player.getY() < this.getY()){
                System.out.println("Quadrant 1 relative to player");
                coordinates[0] = speed;
                coordinates[1] -= speed;
            }else if(player.getX() < this.getX() && player.getY() > this.getY()){
                System.out.println("Quadrant 3 relative to player");
                coordinates[0] -= speed;
                coordinates[1] = speed;
            }else{
                System.out.println("Quadrant 4 relative to player");
                coordinates[0] = speed;
                coordinates[1] = speed;
            }
            */
        }
    }
}
