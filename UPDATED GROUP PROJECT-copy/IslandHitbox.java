import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
import java.util.*;

/**
 * Full hitbox class
 * 
 * @Logan
 * @1.0.0
 */
public class IslandHitbox extends Hitbox
{
    public IslandHitbox(int h, int w, int offsetX, int offsetY, Actor owner, double boundingFactor){
        super(h, w, offsetX, offsetY, owner, boundingFactor);
    }
    
    public IslandHitbox(int h, int w, double boundingFactor){
        super(h, w, boundingFactor);
    }
    
}
