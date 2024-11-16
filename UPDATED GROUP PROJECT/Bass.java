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

    public Bass(){
        img = new GreenfootImage[1];
        speed = 2;
        hp = 3;
        damage = 1;
        
        // vfx
        img[0] = new GreenfootImage("shark.png");
        
        //if we want to use animation turn this on and set array size to 6
        /**
        for(int i = 0; i < img.length; i++){
            img[i] = new GreenfootImage("SharkF" + (i+1) + ".png");
        }
        */
        
        setImage(img[0]);
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
