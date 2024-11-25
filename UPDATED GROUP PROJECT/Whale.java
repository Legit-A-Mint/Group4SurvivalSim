import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Bass here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Whale extends Enemy
{
    public Whale(){
        super();
        img = new GreenfootImage[2];
        speed = 0.8;
        hp = 35;
        damage = 10;
        attackCooldown = 60;
        isMovable = true;
        // vfx
        img[0] = new GreenfootImage("WhaleF1.png");
        
        for(int i = 0; i < img.length; i++){
            img[i] = new GreenfootImage("WhaleF" + (i+1) + ".png");
        }

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
    }

    public void attackAnimation(){
        animate(this, img, img[0].getWidth(), img[0].getHeight(), 6, direction);
    }

    public boolean checkForCollision(){
        return false; 
    }
}
