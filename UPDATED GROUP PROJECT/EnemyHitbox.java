import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
import java.util.*;

/**
 * Full hitbox class
 * 
 * @Andrew Xu
 * @1.0.0
 */
public class EnemyHitbox extends Hitbox
{   
    public EnemyHitbox(int h, int w, int offsetX, int offsetY, Actor owner, double boundingFactor, boolean visable){
        super(h, w, offsetX, offsetY, owner, boundingFactor);
        
        this.boundingFactor = boundingFactor;
        this.type = type;
        box = new GreenfootImage(h, w);
        box.setColor(Color.RED);
        box.setTransparency(55);
        //if(visable){
            box.fill();
        //}
        setImage(box);
        this.owner = owner;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }
}
