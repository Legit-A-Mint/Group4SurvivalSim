import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class SliderObject here.
 * 
 * @lumilk
 * @version 1.0.0
 */
public class SliderObject extends Interface
{
    private GreenfootImage img;
    private int maxOffset;
    private int refX, refY; //refers to the reference point of Slider

    public SliderObject(String name, int max, int refX, String image, double scale, int myX, int myY){
        super(name, myX, myY);
        
        img = new GreenfootImage(image);
        setImage(img);
        img.scale((int)(img.getWidth() * scale), 
            (int)(img.getHeight() * scale));
        maxOffset = max;
        this.refX = refX;
    }

    public void act(){
        super.act();
        if (Greenfoot.mouseDragged(this)){
            MouseInfo m = Greenfoot.getMouseInfo();

            //Set a boundary for the slider, x position cannot exceed the maxOffset
            if(this.getX() <= refX + maxOffset && this.getX() >= refX - maxOffset){
                myX = m.getX();
                setLocation(m.getX(), this.getY());
            }
            
            //If user goes too far above, set it down
            if(this.getX() >= refX + maxOffset){
                myX = refX + maxOffset - 1;
                setLocation(refX + maxOffset - 1, this.getY());
            }
            else if(this.getX() <= refX - maxOffset){
                myX = refX - maxOffset + 1;
                setLocation(refX - maxOffset + 1, this.getY());
            }
        }
    }

    public void setTransparency(double t){
        img.setTransparency((int)(255 * t));
    }

    protected int getMaxOffset(){
        return maxOffset;
    }

    @Override
    protected boolean isUserInteracting() {
        return Greenfoot.mouseMoved(this);
    }
}
