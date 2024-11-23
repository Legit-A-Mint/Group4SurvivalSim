import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

/**
 * Write a description of class Enemies here.
 * 
 * @lumilk
 * @Andrew
 * @1.0.2
 */
public abstract class Enemy extends Effects
{
    protected GreenfootImage[] img;
    protected int hp;
    protected int damage;
    protected int direction; 
    protected int attackCooldown;
    protected int attackTimer;
    protected double speed;
    protected boolean isMovable;

    // Once per instance
    protected Player player;
    private boolean foundPlayer;
    private EnemyHitbox hitbox;
    private boolean createdHitbox = true;
    private double slow;
    private double minSpeed;

    // Path-blocked flags
    private boolean upBlocked;
    private boolean downBlocked;
    private boolean leftBlocked;
    private boolean rightBlocked;

    SimulationWorld world;
    protected boolean removeMe;

    public Enemy(){
        minSpeed = 0.5;
        slow = 0.35;
        attackTimer = 0;
        removeMe = false;
        createdHitbox = false;
    }

    protected abstract void attack();

    protected abstract void attackAnimation();

    public void act(){
        if(!createdHitbox){
            //createHitbox();
        }

        if (SimulationWorld.isActing())
        {
            //System.out.println(this + " myHp: " + hitpoints);

            // Find object player
            if(!foundPlayer){
                if(getWorld().getObjects(Player.class) != null){
                    player = getWorld().getObjects(Player.class).get(0);
                }
            }

            if(this.hp <= 0){
                removeMe = true;;
            }

            if(isMovable){

                lookForTarget();
                repel();
                // Check for path blockages
                checkForBlockages();

                // Try-catch-finally for movement handling
                try {
                    if (!upBlocked && !downBlocked && !leftBlocked && !rightBlocked) {
                        moveDiagonally();
                    } else if (upBlocked && downBlocked) {
                        // Change direction to horizontal (left or right)
                        setRotation(180); // or 0, depending on the desired horizontal direction
                        moveHorizontally();
                    } else if (leftBlocked && rightBlocked) {
                        // Change direction to vertical (up or down)
                        setRotation(90); // or 270, depending on the desired vertical direction
                        moveVertically();
                    }
                } catch (Exception e) {
                    System.out.println("Error in movement: " + e.getMessage());
                } finally {
                    // Optionally place any cleanup code here if needed
                }
            }

            if(removeMe){
                getWorld().removeObject(hitbox);
                getWorld().removeObject(this);
            }
        }
    }

    protected void createHitbox(){
        hitbox = new EnemyHitbox(getImage().getWidth() - 30, getImage().getHeight()/2, 0, 0, this, 2.5, false);
        getWorld().addObject(hitbox, this.getX(), this.getY());
        createdHitbox = true;
    }

    protected boolean getPlayerCollision(){
        try{
            if(player.getHitbox() != null){
                return(this.intersects(player.getHitbox()));   
            }
        }
        catch(Exception e){}

        return false;
    }

    public void lookForTarget(){
        try{
            if(!getWorld().getObjects(Player.class).isEmpty()){
                player = getWorld().getObjects(Player.class).get(0);

                turnTowards(player.getX(), player.getY());
                move(speed);

                if(this.getX() < player.getX()){
                    direction = 1;
                }else{
                    direction = 2;
                }
            }
        }catch(Exception e){}
    }

    public void damageMe(int damage){
        this.hp -= damage*world.diffMulti;
    }

    public void slowMe(){
        if(speed >= minSpeed){
            this.speed -= slow;
        }
    }

    // Modified repel method
    public void repel(){
        ArrayList<Enemy> enemies = (ArrayList<Enemy>)getIntersectingObjects(Enemy.class);
        ArrayList<Actor> actorsTouching = new ArrayList<Actor>();

        for (Enemy e : enemies) {
            if(e.getMovable()){
                actorsTouching.add(e);  
            }   
        }
        pushAwayFromObjects(actorsTouching, 4);
    }

    public void pushAwayFromObjects(ArrayList<Actor> nearbyObjects, double minDistance) {
        int currentX = getX();
        int currentY = getY();

        for (Actor object : nearbyObjects) {
            int objectX = object.getX();
            int objectY = object.getY();
            int objectWidth = object.getImage().getWidth();
            int objectHeight = object.getImage().getHeight();

            double distance = Math.sqrt(Math.pow(currentX - objectX, 2) + Math.pow(currentY - objectY, 2));

            double thisRadius = Math.max(getImage().getWidth() / 4.0, getImage().getHeight() / 4.0);
            double objectRadius = Math.max(objectWidth / 4.0, objectHeight / 4.0);

            if (distance < (thisRadius + objectRadius + minDistance)) {
                int deltaX = objectX - currentX;
                int deltaY = objectY - currentY;

                double length = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
                double unitX = deltaX / length;
                double unitY = deltaY / length;

                double pushAmount = (thisRadius + objectRadius + minDistance) - distance;

                object.setLocation(objectX + (int)(pushAmount * unitX), objectY + (int)(pushAmount * unitY));
            }
        }
    }

    private void moveDiagonally() {
        try {
            if (!upBlocked && !downBlocked) {
                move(speed);  // Diagonal movement if no blockage
            }
        } catch (Exception e) {
            System.out.println("Error moving diagonally: " + e.getMessage());
        }
    }

    private void moveHorizontally() {
        try {
            move(speed);
        } catch (Exception e) {
            System.out.println("Error moving horizontally: " + e.getMessage());
        }
    }

    private void moveVertically() {
        try {
            move(speed);
        } catch (Exception e) {
            System.out.println("Error moving vertically: " + e.getMessage());
        }
    }

    private void checkForBlockages() {
        // Placeholder logic for checking path blockages
        // You can replace this with actual logic to detect blockages
        upBlocked = isBlockedInDirection("up");
        downBlocked = isBlockedInDirection("down");
        leftBlocked = isBlockedInDirection("left");
        rightBlocked = isBlockedInDirection("right");
    }

    private boolean isBlockedInDirection(String direction) {
        // Placeholder for actual blockage checking logic
        // Implement the method that checks if the given direction is blocked by walls, obstacles, etc.
        return false; // Example return, change based on actual game logic
    }

    public EnemyHitbox getHitbox(){
        return hitbox;
    }

    public boolean getMovable(){
        return isMovable;
    }

    protected Player getPlayer(){
        return player;
    }
}
