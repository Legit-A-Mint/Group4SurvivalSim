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
    
    public Harpoon(){
        speed = 4.75;
        damage = 4;
        attackSpeed = 90;
<<<<<<< Updated upstream
        lifeSpan = 300;
=======
        lifeSpan = 600;
        
        playHarpoonSound();
>>>>>>> Stashed changes
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
