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
    protected int dir;

    protected double relativeX, speedX, relativeY, speedY;

    private static final int MAX_SPAWN_DISTANCE = 350;
    private static final int MIN_SPAWN_DISTANCE = 100;

    private boolean createdHitbox;
    private Hitbox hitbox;

    public Enemy(){
        speed = 1;
    }

    public void act()
    {
        if(!createdHitbox){
            hitbox = new Hitbox(getImage().getWidth() - 30, getImage().getHeight()/2, 0, 0, this);
            getWorld().addObject(hitbox, 0, 0);
            createdHitbox = true;
        }

        updateHitboxPosition();
        lookForTarget();
    }

    public void lookForTarget(){
        if(!getWorld().getObjects(Player.class).isEmpty()){
            player = getWorld().getObjects(Player.class).get(0);
            turnTowards(player.getX(), player.getY());
            if(!isCollidingWithHitbox()){
                move(speed);  
            }
            else{
                setLocation(this.getX(), this.getY());
            }
        }
    }

    private void updateHitboxPosition() {
        //align hitbox with this
        hitbox.setLocation(getX(), getY());
    }

    private boolean isCollidingWithHitbox() {
        //check my hitbox is touching any other hitboxes
        for (Hitbox other : hitbox.getIntersectingHitboxes()) {
            return(other != hitbox && other.checkCollision(hitbox));
        }
        return false;
    }
}
