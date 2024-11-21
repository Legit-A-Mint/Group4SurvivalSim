import greenfoot.*;  
/**
 * Interface
 * 
 * @lumilk
 * @1.0.0
 */
public abstract class Interface extends SuperSmoothMover{

    private int transparency = 255;

    // Static creates a global timer for all subclasses
    private static int lastInteractionTime = 0;
    private final int MAX_IDLE_TIME = 200;

    // Inherited variables
    protected String name;
    protected int myX, myY;
    protected boolean fadesAway;

    // Every subclass must contain a check to see if the user interacts with object
    protected abstract boolean isUserInteracting();

    public Interface(String name, int myX, int myY){
        this.name = name;
        this.myX = myX;
        this.myY = myY;
    }

    public void act() {
        updatePos();
        if(isUserInteracting()){ 
            lastInteractionTime = 0;  // Reset global timer
            resetTransparency();
        } 
        else{
            if(lastInteractionTime > MAX_IDLE_TIME){  // Check shared idle time for all instances    
                fadeTransparency();                  
            } 
            else{
                resetTransparency();
            }
        }
        // ontinue to increase
        lastInteractionTime++;
    }

    // Handle transparency
    private void resetTransparency(){
        transparency = 255;
        // Gets image from subclass
        getImage().setTransparency(transparency);
    }

    private void fadeTransparency(){
        // Only decrease transparency if not already half
        if(transparency > 255/2 && fadesAway){ 
            transparency -= 3;
            // Gets image from subclass
            getImage().setTransparency(transparency);  // Reapply transparency
        }
    }

    // Updates position of any interface object, this is to allow movement of some of the objects (like slider)
    // Cannot override setlocation because movement would be disabled
    private void updatePos(){
        this.setLocation(myX, myY);
    }
}
