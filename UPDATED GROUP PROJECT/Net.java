import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Net here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Net extends Projectile
{
    
    private static final GreenfootSound fireSfx = new GreenfootSound("Net.mp3");
    
    public Net(){
        speed = 3;
        damage = 4;
        attackSpeed = 70;
        lifeSpan = 250;
        removeMe = false;
        
        playNetSound();
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
            removeMe = true;
            if(getOneIntersectingObject(CollisionHitbox.class) != null){
                speed = 0;
            }
        }
    }
    
    // Play the firing sound effect
    private void playNetSound() {
        fireSfx.play();
    }
}
