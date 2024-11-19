import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Bass here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Swordfish extends Enemy
{
    private int speedCooldown;
    private static final int speedCooldownMax = 300;
    private boolean stopped;
    public Swordfish(){
        super();
        img = new GreenfootImage[1];
        speed = 8;
        hp = 3;
        damage = 10;
        attackCooldown = 60;
        speedCooldown = 0;
        isMovable = true;
        // vfx
        img[0] = new GreenfootImage("SwordFish.png");

        setImage(img[0]);
    }

    public void act(){
        super.act();
        if(this.attackTimer > 0){
            attackTimer--;
        }
        if(stopped){
            speedCooldown--;
        }
        if(speedCooldown == 0){
            this.speed = 8;
        }

        if(getPlayerCollision()){
            attackAnimation();
            if(attackTimer == 0){
                attack();
                attackTimer = attackCooldown;

                this.speed = 0;
                stopped = true;
                speedCooldown = speedCooldownMax; 

            }
        }
        else{
            setImage(img[0]);
        }
    }

    public void attack(){
        getPlayer().damageMe(damage);
    }

    public void attackAnimation(){
        animate(this, img, img[0].getWidth(), img[0].getHeight(), 6, direction);
    }

    public boolean checkForCollision(){
        return false; 
    }
}
