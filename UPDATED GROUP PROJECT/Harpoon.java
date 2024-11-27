import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Harpoon here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Harpoon extends Projectile
{
    //Harpoon shooting sound effect
    private static final GreenfootSound fireSfx = new GreenfootSound("Harpoon.mp3");
    
    private static final int PIERCE_CAP = 3;
    private int hitCount;
    
    public Harpoon(){
        speed = 4.75;
        damage = 3;
        attackSpeed = 90;
        lifeSpan = 300;
        
        hitCount = 0;
        
        playHarpoonSound();
    }
    
    public void act()
    {
        hitSomething();
        super.act();
    }
    
    public void hitSomething(){
        if(getOneIntersectingObject(Enemy.class) != null){
            hitEnemy = (Enemy) getOneIntersectingObject(Enemy.class);
            hitEnemy.damageMe(damage);
            hitEnemy.slowMe();
            hitCount++;
            if(hitCount >= PIERCE_CAP){
                removeMe = true;
            }
            if(getOneIntersectingObject(CollisionHitbox.class) != null){
                speed = 0;
            }
        }
    }
    
    // Play the firing sound effect
    private void playHarpoonSound() {
        fireSfx.play();
    }
}
