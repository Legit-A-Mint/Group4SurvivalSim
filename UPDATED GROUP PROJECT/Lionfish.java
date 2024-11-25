import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Lionfish here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Lionfish extends Enemy
{
    public Lionfish(){
        super();
        img = new GreenfootImage[1];
        speed = 2;
        hp = 2;
        damage = 2;
        attackCooldown = 20;
        isMovable = true;
        // vfx
        img[0] = new GreenfootImage("Lionfish.png");

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
        getPlayer().poisonMe(damage, 10);
    }

    public void attackAnimation(){
        animate(this, img, img[0].getWidth(), img[0].getHeight(), attackCooldown, direction);
    }

    public boolean checkForCollision(){
        return false; 
    }
}
