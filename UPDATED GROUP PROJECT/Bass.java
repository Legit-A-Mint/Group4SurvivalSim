import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Bass here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Bass extends Enemy
{
    // Changed to animate method to stay upright
    private GreenfootImage[] img = new GreenfootImage[1];

    public Bass(){

        img[0] = new GreenfootImage("shark.png");
        
        setImage(img[0]);
        
        speed = 2;

    }
    
    public void act(){
        // takes dir from super class
        animate(this, img, img[0].getWidth(), img[0].getHeight(), 16, direction);
        super.act();
    }
    
    /* Bass has no other unique perks other then doing damage */
    public void attack(){
        if((Player) getOneIntersectingObject(Player.class) != null){
            
        }
    }
    
    public void damaged(){
        
    }
    
    public boolean checkForCollision(){
       return false; 
    }
}
