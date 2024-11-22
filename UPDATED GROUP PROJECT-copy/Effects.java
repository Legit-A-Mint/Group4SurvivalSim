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
    protected GreenfootImage[] flipVert, flipHori, flipDiag; 

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
        if (SimulationWorld.isActing())
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
            flipVert = new GreenfootImage[image.length];
            flipHori = new GreenfootImage[image.length];
            flipDiag = new GreenfootImage[image.length];

            for(int i = 0; i < image.length; i++){
                flipVert[i] = new GreenfootImage(image[i]);
                flipVert[i].mirrorVertically();
            }

            for(int i = 0; i < image.length; i++){
                flipHori[i] = new GreenfootImage(image[i]);
                flipHori[i].mirrorHorizontally();
            }

            for(int i = 0; i < image.length; i++){
                flipDiag[i] = new GreenfootImage(image[i]);
                flipDiag[i].mirrorHorizontally();
                flipDiag[i].mirrorVertically();
            }

            switch(direction){
                    case(1):
                    currentImages[currentFrameIndex].scale(width, height);
                    actor.setImage(currentImages[currentFrameIndex]);
                    break;

                    case(2):
                    flipVert[currentFrameIndex].scale(width, height);
                    actor.setImage(flipVert[currentFrameIndex]);
                    break;

                    case(3):
                    flipHori[currentFrameIndex].scale(width, height);
                    actor.setImage(flipHori[currentFrameIndex]);
                    break;

                    case(4):
                    flipDiag[currentFrameIndex].scale(width, height);
                    actor.setImage(flipDiag[currentFrameIndex]);
                    break;
            }
        }
    }
}
