import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class hitScan here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Fov extends Hitbox
{
    Player owner;
    private double[] cords = new double[2];
    private double[] size = new double[2];
    private int[] baseSize = new int[2];
    
    public Fov(int h, int w, int offsetX, int offsetY, Player owner, double boundingFactor){
        super(h, w, offsetX, offsetY, owner, boundingFactor);
        this.owner = owner;
        
        baseSize[0] = w;
        baseSize[1] = h;
    }
    
    public void act(){
        calcTrig();
        moveWithOwner();
        scaleFov();
    }
    
    public boolean enemyDetected(){
        if(this.getIntersectingObjects(Enemy.class).size() == 0){
            return true;
        }
        return false;
    }
    
    private void scaleFov(){
        box.scale((int)size[0],(int) size[1]);
    }
    
    private void moveWithOwner(){
        setLocation(cords[0], cords[1]);
    }
    
    private void calcTrig(){
        cords[0] = owner.getX() + 
        ((double) (Math.cos(Math.toRadians(owner.getRotation()))) * (owner.getImage().getWidth())*3.745);
        
        cords[1] = owner.getY() + 
        ((double) (Math.sin(Math.toRadians(owner.getRotation()))) * (owner.getImage().getHeight())*3.69);
        
        
        size[0] = baseSize[0]*(0.25+((Math.abs(Math.cos(Math.toRadians(owner.getRotation())))))*1.3);
        
        size[1] = baseSize[0]*(0.25+((Math.abs(Math.sin(Math.toRadians(owner.getRotation())))))*1.2);
        
    }
}
