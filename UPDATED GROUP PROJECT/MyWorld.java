import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

/**
 * Write a description of class MyWorld here.
 * 
 * @Andrew
 * @Darius
 * @Jonarahn
 * @Logan
 *
 * @1.2.6
 */
public class MyWorld extends World
{
    public Scroller scroller; // scroll controller
    private Player player; // main actor
    private Lives lives;
    private int waveCount, actCount;

    private static final int MAX_SPAWN_DISTANCE = 100;
    private boolean spawnOnce;

    private static final int WIDTH = 2000, height = 2000;
    
    private double exactY, exactX;
    public MyWorld()
    {
        super(1024, 576, 1, false);
        // settings
        
        waveCount = 0;
        actCount = 0;
        spawnOnce = true;


        addObject(scroller = new Scroller(this, new GreenfootImage("water.png"), WIDTH, height));
        addObject(player = new Player(), this.getWidth()/2, this.getHeight()/2);
        
        //border hitbox
        addObject(new Hitbox(WIDTH, 100, 2.5), WIDTH/2, height);
        addObject(new Hitbox(WIDTH, 100, 2.5), WIDTH/2, 0);
        addObject(new Hitbox(100, height, 2.5), WIDTH, height/2);
        addObject(new Hitbox(100, height, 2.5), 0, height/2);
        
        addObject(new Island(new GreenfootImage("island.png")), 500 - getScroller().getScrolledX(), 500 - getScroller().getScrolledY());
        //addObject(new Hitbox(200, 200), 275, 400);
        //addObject(new Hitbox(200, 200), 600, 900);
        
        
        Slider slider = new Slider("TestSlider", "rail.png", "circle.png", 1, 130, 155, 540);
        addObject(slider, 155, 540);
        //addObject(lives = new Lives(), 50, 20);

        // addObject(new MiniMap(), 30, 370);
        setPaintOrder(Hitbox.class, SliderObject.class, Slider.class);
    }

    public void addObject(Actor a){

    }

    public void act()
    {
        actCount++;

        if(actCount == 300){
            spawnOnce = true;
            waveCount++;
            actCount = 0;
        }

        switch(waveCount) {
                case(0):

                // Manual wave simulator

                if(spawnOnce){
                    spawnOnce = false;
                    for(int i = 0; i < 3; i++){
                        addObject(new Bass(3), Greenfoot.getRandomNumber (player.getX() + MAX_SPAWN_DISTANCE) + (player.getX() - MAX_SPAWN_DISTANCE), 
                            Greenfoot.getRandomNumber (player.getY() + MAX_SPAWN_DISTANCE) + (player.getY() - MAX_SPAWN_DISTANCE));
                    }

                }
                break;

        }

        scroller.scroll(getWidth()/2-player.getX(), getHeight()/2-player.getY(), this, (ArrayList<SuperSmoothMover>)(getObjects(SuperSmoothMover.class)));
    }

    /** returns the lives counter object */
    public Lives getLivesCounter()
    {
        return lives;
    }

    public Scroller getScroller(){
        return scroller;
    }
     
    public static void zSort (ArrayList<SuperSmoothMover> actorsToSort, World world){
        ArrayList<ActorContent> acList = new ArrayList<ActorContent>();
        // Create a list of ActorContent objects and populate it with all Actors sent to be sorted
        for (SuperSmoothMover a : actorsToSort){
            acList.add (new ActorContent (a, a.getPreciseX(), a.getPreciseY()));
        }    
        // Sort the Actor, using the ActorContent comparitor (compares by y coordinate)
        Collections.sort(acList);
        
        //System.out.println(acList.toString());
        // Replace the Actors from the ActorContent list into the World, inserting them one at a time
        // in the desired paint order (in this case lowest y value first, so objects further down the 
        // screen will appear in "front" of the ones above them).
        for (ActorContent a : acList){
            Actor actor  = a.getActor();
            world.removeObject(actor);
            //System.out.println(a.getPreciseX());
            world.addObject(actor, a.getX(), a.getY());
        }
    }
    
    public void addObject(Actor object, double x, double y){
        super.addObject(object, (int)(x + 0.5), (int)(y + 0.5));
    }

    /** prevents restarting after game over (called by greenfoot framework) 
    
    public void started()
    {
    if (!getObjects(GameOver.class).isEmpty()) Greenfoot.stop();
    }
    
    */
    
    public static double getDistance (Actor a, Actor b)
    {
        return Math.hypot (a.getX() - b.getX(), a.getY() - b.getY());
    }

    public double getPreciseY() 
    {
        return exactY;
    }

    public double exactY(){
        return exactY;
    }
    
    public double getPreciseX() 
    {
        return exactY;
    }

    public double exactX(){
        return exactY;
    }
}

/**
class ActorContent implements Comparable <ActorContent> {
    
    
    private Actor actor;
    private double xx, yy;
    
    private int x, y;
    
    public ActorContent(Actor actor, double xx, double yy){
        this.actor = actor;
        this.xx = xx;
        this.yy = yy;
    }

    public void setLocation (double x, double y){
        xx = x;
        yy = y;
    }

    public double getPreciseX() {
        return xx;
    }

    public double getPreciseY() {
        return yy;
    }
    
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Actor getActor(){
        return actor;
    }

    public String toString () {
        return "Actor: " + actor + " at " + xx + ", " + yy;
    }
    
    @Override
    public int compareTo (ActorContent a){
        return Double.compare(this.getPreciseX(), a.getPreciseY());
    }
    
    public double preciseCompareTo (ActorContent a){
        return this.getPreciseX() - a.getPreciseY();
    }
}

*/