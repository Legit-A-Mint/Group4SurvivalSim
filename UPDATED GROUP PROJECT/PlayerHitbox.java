import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class PlayerHitbox here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PlayerHitbox extends Hitbox
{
    private boolean follow;
    public PlayerHitbox(int h, int w, int offsetX, int offsetY, Actor owner, double boundingFactor, boolean follow){
        super(h, w, offsetX, offsetY, owner, boundingFactor);
        this.follow = follow;
    }
    
    public void act()
    {
        if(follow){
            super.act();
        }else{
            //
        }
    }
    
    public void hitWall(){
        //
    }
}