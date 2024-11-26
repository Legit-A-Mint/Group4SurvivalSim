import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Bass here.
 * 
 * @lumilk
 * @version (a version number or a date)
 */
public class Swordfish extends Enemy
{
    private int speedCooldown;
    private static final int speedCooldownMax = 300;
    private boolean stopped;
    public Swordfish(double diffMulti){
        super();
        img = new GreenfootImage[1];
        speed = 8;
        hp = (int)(3*diffMulti);
        damage = (int)(25*diffMulti);
        attackCooldown = 60;
        speedCooldown = 0;
        isMovable = true;

        img[0] = new GreenfootImage("SwordFish.png");

        
        // Added the method use it when you have the images drawn
        /**
        for(int i = 0; i < img.length; i++){
            img[i] = new GreenfootImage("SwordfishF" + (i+1) + ".png");
        }
        */

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
        Greenfoot.playSound("Bite_1.mp3");
    }

    public void attackAnimation(){
        animate(this, img, img[0].getWidth(), img[0].getHeight(), 6, direction);
    }

    public boolean checkForCollision(){
        return false; 
    }
}
