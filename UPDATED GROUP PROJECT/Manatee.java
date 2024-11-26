import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Bass here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Manatee extends Enemy
{
    public Manatee(double diffMulti){
        super();
        img = new GreenfootImage[1];
        speed = 0.8;
        hp = (int)(100*diffMulti);
        damage = (int)(20*diffMulti);
        attackCooldown = 60;
        isMovable = true;
        // vfx
        img[0] = new GreenfootImage("Manatee.png");
        
        setImage(img[0]);
    }

    public void act(){
        super.act();
        if(this.attackTimer > 0){
            attackTimer--;
        }

        if(getPlayerCollision()){
            attackAnimation();
            if(attackTimer == 0){
                attack();
                attackTimer = attackCooldown;
            }
        }
        else{
            setImage(img[0]);
        }
    }

    public void attack(){
        getPlayer().damageMe(damage);
        Greenfoot.playSound("Bite_1.mp3");
    }

    public void attackAnimation(){
        animate(this, img, img[0].getWidth(), img[0].getHeight(), 6, direction);
    }

    public boolean checkForCollision(){
        return false; 
    }
}
