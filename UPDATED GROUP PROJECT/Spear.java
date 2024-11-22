import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Spear here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Spear extends Projectile
{
    public Spear(){
        speed = 3;
        damage = 10000;
        attackSpeed = 45;
        lifeSpan = 250;
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
}
