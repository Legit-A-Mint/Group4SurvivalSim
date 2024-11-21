import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Counter here.
 * 
 * @lumilk 
 * @1.0.0
 */

public class Counter extends Interface
{
    private GreenfootImage imgBase;
    private String myImage;
    private double count;
    private int myX, myY;
    private Actor refObj;

    public void act()
    {
        super.act();
        setCount();
    }

    public Counter(String name, String image, double scale, boolean isDouble, int myX, int myY){
        super(name, myX, myY);
        
        myImage = image;
        imgBase = new GreenfootImage(image);

        imgBase.scale((int)(imgBase.getWidth() * scale), (int)(imgBase.getHeight() * scale)); 
        setImage(imgBase);
    }
    
    // Increment by abritrary number
    public void setCount(double count){
        this.count = count;
        updateImage();
    }

    // Count increment by 1
    public void setCount(){
        this.count++;
        updateImage();
    }

    private void updateImage() {
        // Create a copy of the base image to avoid drawing over the original repeatedly
        imgBase = new GreenfootImage(myImage);

        // Set the font and color for the text
        imgBase.setFont(new Font("Monotype Sorts", true, false, 24));
        imgBase.setColor(Color.WHITE);
        imgBase.drawString(count + "", myX, myY);

        //reset image
        setImage(imgBase);
    }

    @Override
    protected boolean isUserInteracting() {
        return Greenfoot.mouseClicked(this);
    }
}
