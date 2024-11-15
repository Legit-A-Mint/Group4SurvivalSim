import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Counter here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
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
        this.name = name;
        myImage = image;
        imgBase = new GreenfootImage(image);

        imgBase.scale((int)(imgBase.getWidth() * scale), (int)(imgBase.getHeight() * scale)); 
        setImage(imgBase);
        this.myX = myX;
        this.myY = myY;
    }

    public void setCount(double count){
        this.count = count;
        updateImage();
    }

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
