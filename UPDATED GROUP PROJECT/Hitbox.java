import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
import java.util.*;

/**
 * Full hitbox class
 * 
 * @Logan
 * @1.0.0
 */
public abstract class Hitbox extends Effects
{
    //image variables
    protected GreenfootImage box;
    protected Actor owner;
    protected String type;
    private static final boolean visible = true;
    
    protected int offsetX, offsetY;
    protected double boundingFactor;
    public Hitbox(int h, int w, double boundingFactor){
        this.boundingFactor = boundingFactor;
        this.type = type;
        box = new GreenfootImage(h, w);
        box.setColor(Color.RED);
        box.setTransparency(55); //less distracting when turned on
        if(visible){
            box.fill();
        }
        setImage(box);
    }
    
    public Hitbox(int h, int w, int offsetX, int offsetY, Actor owner, double boundingFactor){
        this.boundingFactor = boundingFactor;
        this.type = type;
        box = new GreenfootImage(h, w);
        box.setColor(Color.RED);
        box.setTransparency(55);
        if(visible){
            box.fill();
        }
        setImage(box);
        this.owner = owner;
        
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }
    
    // Ensure hitbox always stays with owner
    public void act(){
        if(owner != null){
            moveWithOwner();
        }
        repelEnemies();
    }  

    private void moveWithOwner(){
        setLocation(owner.getX() + offsetX, owner.getY() + offsetY);
    }
    
    // Return a list of all hitboxes that intersect this hitbox
    public List<Hitbox> getIntersectingHitboxes() {
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
    
    public void repelEnemies() {
        ArrayList<Enemy> enemies = (ArrayList<Enemy>)getIntersectingObjects(Enemy.class);
        ArrayList<Actor> actorsTouching = new ArrayList<Actor>();

        for (Enemy e : enemies) {
            if(e.getMovable()){
                actorsTouching.add(e);  
            }   
        }
        pushAwayFromObjects(actorsTouching, 4);
    }

    /**
     * New repel method! Seems to work well. Can be used in both directions, but for now
     * commented out movement on x so players are only "repelled" in a y-direction.
     * 
     * @author Mr Cohen
     * @since February 2023
     */
    public void pushAwayFromObjects(ArrayList<Actor> nearbyObjects, double minDistance) {
        // Get the current position of this actor
        int currentX = getX();
        int currentY = getY();

        // Iterate through the nearby objects
        for (Actor object : nearbyObjects) {
            // Get the position and bounding box of the nearby object
            int objectX = object.getX();
            int objectY = object.getY();
            int objectWidth = object.getImage().getWidth();
            int objectHeight = object.getImage().getHeight();

            // Calculate the distance between this actor and the nearby object's bounding oval
            double distance = Math.sqrt(Math.pow(currentX - objectX, 2) + Math.pow(currentY - objectY, 2));

            // Calculate the effective radii of the bounding ovals
            double thisRadius = Math.max(getImage().getWidth() / boundingFactor, getImage().getHeight() / boundingFactor);
            double objectRadius = Math.max(objectWidth / boundingFactor, objectHeight / boundingFactor);

            // Check if the distance is less than the sum of the radii
            if (distance < (thisRadius + objectRadius + minDistance)) {
                // Calculate the direction vector from this actor to the nearby object
                int deltaX = objectX - currentX;
                int deltaY = objectY - currentY;

                // Calculate the unit vector in the direction of the nearby object
                double length = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
                double unitX = deltaX / length;
                double unitY = deltaY / length;

                // Calculate the amount by which to push the nearby object
                double pushAmount = (thisRadius + objectRadius + minDistance) - distance;

                // Update the position of the nearby object to push it away

                // 2d version, allows pushing on x and y axis, commented out for now but it works, just not the
                // effect I'm after:
                object.setLocation(objectX + (int)(pushAmount * unitX), objectY + (int)(pushAmount * unitY));
            }
        }
    }
    
    public boolean checkIntersection(Actor other) {
        return this.intersects(other);
    }
    
    public String getType(){
        return type;
    }
}
