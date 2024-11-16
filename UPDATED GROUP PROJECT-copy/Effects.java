import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Effects here.
 * 
 * @Andrew Xu
 * @version (a version number or a date)
 */
public class Effects extends SuperSmoothMover
{    
    protected int actsLeft;
    protected int fadeLength;
    protected boolean first;
    protected int currentFrameIndex;
    
    protected int frameCounter = 0;
    protected boolean checkOnce = false;
    protected GreenfootImage[] flip; 
    
    
    protected void fade (Actor a, int actsLeft, int fadeLength){
        double percent = (double)actsLeft / fadeLength;
        if (percent > 1.0){
            return;
        }
        int trans = (int)(percent * 255);
        
        GreenfootImage image = a.getImage();
        
        getImage().setTransparency(trans);
    }
    
    public void animate(Actor actor, GreenfootImage[] image, int width, int height, int FPS, int direction)
    {   
        frameCounter++;
        
        // This ensures that the image only plays based on the fps
        currentFrameIndex = frameCounter/FPS;
        
        /**
         * Loop
         */
        if (frameCounter >= (image.length) * FPS) {
            frameCounter = 0;
            return;
        }
        
        GreenfootImage[] currentImages = image;
        flip = new GreenfootImage[image.length];
        
        for(int i = 0; i < image.length; i++){
            flip[i] = new GreenfootImage(image[i]);
            //flip[i].mirrorHorizontally();
            flip[i].mirrorVertically();
        }

        if(direction == 1){
            currentImages[currentFrameIndex].scale(width, height);
            actor.setImage(currentImages[currentFrameIndex]);
        }else{
            flip[currentFrameIndex].scale(width, height);
            actor.setImage(flip[currentFrameIndex]);
        }
    }
    
}
