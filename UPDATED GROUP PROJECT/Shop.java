import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Shop extends SuperSmoothMover
{
    private GreenfootImage shopBackground;
    private Item[] items; 

    public Shop()
    {    
        shopBackground = new GreenfootImage("shop.png");  
        setImage(shopBackground);
        displayItems();
    }

    public void displayItems()
    {
        
    }
}