import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Bass here.
 * 
 * @lumilk
 * @1.0.0
 */
public class Krakite extends Enemy
{
    private int speedCooldown;
    private static final int speedCooldownMax = 200;
    private boolean stopped;

    public Krakite(){
        super();
        img = new GreenfootImage[1];
        speed = 6;
        hp = 300;
        damage = 3;
        attackCooldown = 200;
        isMovable = true;
        // vfx
        img[0] = new GreenfootImage("Krakite.png");
        
        // Added the method use it when you have the images drawn
        for(int i = 0; i < img.length; i++){
            img[i] = new GreenfootImage("KrakiteF" + (i+1) + ".png");
        }

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
            stopped = false;
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
