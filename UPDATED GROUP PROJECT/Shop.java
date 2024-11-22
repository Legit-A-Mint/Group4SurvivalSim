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
        items = new Item[3];  
        items[0] = new Item("Heal", 50); 
        items[1] = new Item("Spear", 100); 
        items[2] = new Item("Harpoon", 200); 
 
        getWorld().addObject(items[0], 150, 200); 
        getWorld().addObject(items[1], 300, 200); 
        getWorld().addObject(items[2], 450, 200); 
    }
}