import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Harpoon here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Harpoon extends Projectile
{
    public Harpoon(){
        speed = 2.5;
        damage = 4;
        attackSpeed = 90;
        lifeSpan = 600;
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
            if(getOneIntersectingObject(IslandHitbox.class) != null){
                speed = 0;
            }
        }
    }
}
