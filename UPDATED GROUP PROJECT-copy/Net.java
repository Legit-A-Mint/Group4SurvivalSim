import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Net here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Net extends Projectile
{
    private boolean removeMe;
    
    public Net(){
        speed = 2;
        damage = 1;
        attackSpeed = 75;
        lifeSpan = 250;
        removeMe = false;
    }

    public void act()
    {
        super.act();
        hitSomething();
        if(removeMe){
            getWorld().removeObject(this);
        }
    }

    public void hitSomething(){
        if(getOneIntersectingObject(Enemy.class) != null){
            hitEnemy = (Enemy) getOneIntersectingObject(Enemy.class);
            hitEnemy.damageMe(damage);
            hitEnemy.slowMe();
            removeMe = true;
            if(getOneIntersectingObject(IslandHitbox.class) != null){
                speed = 0;
            }
        }
    }
}
