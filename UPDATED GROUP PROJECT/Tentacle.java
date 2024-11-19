import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Tentacle here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Tentacle extends Kraken
{
    private GreenfootImage[] img;
    public Tentacle(){
        hp = 50;
        damage = 50;
        img = new GreenfootImage[3];
        
        //Set the array size to 5 so animation works
        for(int i = 0; i < img.length; i++){
            img[i] = new GreenfootImage("TentacleF" + (i+1) + ".png");
        }        
    }
    public void act()
    {
        animate(this, img, img[0].getWidth(), img[0].getHeight(), 24, 1);
        if(getPlayerCollision()){
            getPlayer().damageMe(damage);
            
            System.out.println("test");
        }
    }
}
