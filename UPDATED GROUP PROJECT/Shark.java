import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Bass here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Shark extends Enemy
{
    public Shark(double diffMulti){
        super();
        img = new GreenfootImage[2];
        speed = 2.4;
        hp = (int)(5*diffMulti);
        damage = (int)(25*diffMulti);
        attackCooldown = 60;
        isMovable = true;
        // vfx
        img[0] = new GreenfootImage("SharkF1.png");
        
        //Set the array size to 5 so animation works
        for(int i = 0; i < img.length; i++){
            img[i] = new GreenfootImage("SharkF" + (i+1) + ".png");
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
        Greenfoot.playSound("Bite_2.mp3");
    }

    public void attackAnimation(){
        animate(this, img, img[0].getWidth(), img[0].getHeight(), 6, direction);
    }

    public boolean checkForCollision(){
        return false; 
    }
}
