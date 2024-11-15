import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;

/**
 * Full hitbox class
 * 
 * @lumilk
 * @1.0.0
 */
public class Hitbox extends SuperSmoothMover
{
    //image variables
    private GreenfootImage box;
    private Actor owner;
    private static final boolean visible = true;
    
    private int offsetX, offsetY;
    
    public Hitbox(int h, int w){
        box = new GreenfootImage(h, w);
        box.setColor(Color.RED);
        box.setTransparency(100); //less distracting when turned on
        if(visible){
            box.fill();
        }
        setImage(box);
    }
    
    public Hitbox(int h, int w, int offsetX, int offsetY, Actor owner){
        box = new GreenfootImage(h, w);
        box.setColor(Color.RED);
        box.setTransparency(100);
        if(visible){
            box.fill();
        }
        setImage(box);
        this.owner = owner;
        
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }
    
    public void act()
    {
        if(owner != null){
            moveWithOwner();
        }
    }  

    private void moveWithOwner(){
        setLocation(owner.getX() + offsetX, owner.getY() + offsetY);
    }
    
    public List<Hitbox> getIntersectingHitboxes() {
            // Cast to Hitbox as we are specifically looking for hitboxes
            List<Hitbox> hitboxes = (List<Hitbox>) getIntersectingObjects(Hitbox.class);
            return hitboxes;
        }
        
    public boolean checkCollision(Hitbox otherHitbox) {
        //get boundaries of this hitbox
        int thisLeft = getX() - getImage().getWidth() / 2;
        int thisRight = getX() + getImage().getWidth() / 2;
        int thisTop = getY() - getImage().getHeight() / 2;
        int thisBottom = getY() + getImage().getHeight() / 2;

        //get boundaries of the other hitbox
        int otherLeft = otherHitbox.getX() - otherHitbox.getImage().getWidth() / 2;
        int otherRight = otherHitbox.getX() + otherHitbox.getImage().getWidth() / 2;
        int otherTop = otherHitbox.getY() - otherHitbox.getImage().getHeight() / 2;
        int otherBottom = otherHitbox.getY() + otherHitbox.getImage().getHeight() / 2;

        //check for overlap in both x and y directions
        boolean isTouchingX = thisRight > otherLeft && thisLeft < otherRight;
        boolean isTouchingY = thisBottom > otherTop && thisTop < otherBottom;

        return isTouchingX && isTouchingY;
    }
}
