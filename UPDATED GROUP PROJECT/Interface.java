import greenfoot.*;  

public abstract class Interface extends SuperSmoothMover{

    private int transparency = 255;

    //static creates a global timer for all subclasses
    private static int lastInteractionTime = 0;
    private final int MAX_IDLE_TIME = 2400;

    //inherited variables
    protected String name;

    public void act() {
        if(isUserInteracting()){ 
            lastInteractionTime = 0;  //reset global timer
            resetTransparency();
        } 
        else{
            if(lastInteractionTime > MAX_IDLE_TIME){  // check shared idle time for all instances    
                fadeTransparency();                  
            } 
            else{
                resetTransparency();
            }
        }
        //continue to increase
        lastInteractionTime++;
    }
    //every subclass must contain a check to see if the user interacts with object
    protected abstract boolean isUserInteracting();

    //handle transparency
    private void resetTransparency(){
        transparency = 255;
        //gets image from subclass
        getImage().setTransparency(transparency);
    }

    private void fadeTransparency(){
        //only decrease transparency if not already half
        if(transparency > 255/2){ 
            transparency -= 3;
            //gets image from subclass
            getImage().setTransparency(transparency);  //reapply transparency
        }
    }
}
