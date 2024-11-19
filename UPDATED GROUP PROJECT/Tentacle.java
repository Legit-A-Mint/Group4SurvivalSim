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
    private boolean createdHitbox;
    public Tentacle(){
        hp = 50;
        damage = 50;
        img = new GreenfootImage[3];
        createdHitbox = false;

        //Set the array size to 5 so animation works
        for(int i = 0; i < img.length; i++){
            img[i] = new GreenfootImage("TentacleF" + (i+1) + ".png");
        }        
    }

    public void act()
    {
        createHitbox();
        animate(this, img, img[0].getWidth(), img[0].getHeight(), 24, 1);
        if(getPlayerCollision()){
            getPlayer().damageMe(damage);
            System.out.println("test");
        }

        if(this.hp <= 0){
            getWorld().removeObject(hitbox);
            getWorld().removeObject(this);
            return;
        }
    }

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
        System.out.println("damaged for: " + damage);
    }
}
