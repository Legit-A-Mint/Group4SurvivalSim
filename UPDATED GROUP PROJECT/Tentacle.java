import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Tentacle here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Tentacle extends Kraken
{
    private GreenfootImage[] img;
    private EnemyHitbox hitbox;
    private boolean foundPlayer;
    private boolean createdHitbox;
    public Tentacle(){
        hp = 50;
        damage = 500;
        attackCooldown = 30;
        img = new GreenfootImage[3];
        createdHitbox = false;

        for(int i = 0; i < img.length; i++){
            img[i] = new GreenfootImage("TentacleF" + (i+1) + ".png");
        }        
    }

    public void act()
    {
        // Create a hitbox if I haven't already
        createHitbox();
        
        // Get player object
        if(!foundPlayer){
            if(getWorld().getObjects(Player.class) != null){
                player = getWorld().getObjects(Player.class).get(0);
            }
        }
        
        // Idle animation
        animate(this, img, img[0].getWidth(), img[0].getHeight(), 24, 1);
        
        // Count down attacKTimer when not 0
        if(this.attackTimer > 0){
            attackTimer--;
        }
        
        // If colliding with player and attackTimer is 0, attack for a certain damage
        if(getPlayerCollision()){
            if(attackTimer == 0){
                getPlayer().damageMe(damage);
                attackTimer = attackCooldown;
            }
        }

        // Remove myself and my hitbox from world if I dont have anymore HP
        if(this.hp <= 0){
            getWorld().removeObject(hitbox);
            getWorld().removeObject(this);
            return;
        }
    }

    //Override methods from superclass
    @Override
    public EnemyHitbox getHitbox(){
        return hitbox;
    }

    @Override
    protected void createHitbox(){
        if(!createdHitbox){
            hitbox = new EnemyHitbox(img[0].getWidth()/2, img[0].getHeight(), 0, 0, this, 2.5);
            getWorld().addObject(hitbox, 0, 0);
            createdHitbox = true;
        }   
    }

    @Override
    public void damageMe(int damage){
        this.hp -= damage;
    }

    @Override
    protected boolean getPlayerCollision(){
        try{
            if(player.getHitbox() != null){
                return(this.intersects(player.getHitbox()));   
            }
        }
        catch(Exception e){}

        return false;
    }
}
