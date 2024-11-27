import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * This weapon is what the player starts off with
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Spear extends Projectile
{
    private static final GreenfootSound fireSfx = new GreenfootSound("Spear.mp3");
    
    public Spear(){
        speed = 4;
        damage = 2;
        attackSpeed = 45;
        lifeSpan = 250;
         
        //Plays the spear sfx
        playSpearSound();
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
            removeMe = true;
            if(getOneIntersectingObject(CollisionHitbox.class) != null){
                speed = 0;
            }
        }
    }
    
    private void playSpearSound(){
        fireSfx.play();
    }
}