import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * ShopScreen class   
 */
public class ShopScreen extends World
{
    private GreenfootImage shopBackground;
    private Item[] items; 

    public ShopScreen()
    {    

        super(600, 400, 1); 
        

        shopBackground = new GreenfootImage("shop.png");  
        setBackground(shopBackground);
         
        displayItems();
    }

    
    public void displayItems()
    { 
        items = new Item[3];  
        items[0] = new Item("Heal", 50); 
        items[1] = new Item("Spear", 100); 
        items[2] = new Item("Harpoon", 200); 
 
        addObject(items[0], 150, 200); 
        addObject(items[1], 300, 200); 
        addObject(items[2], 450, 200); 
    }

    
    public boolean isUserInteracting()
    {
        
        if (Greenfoot.mouseClicked(null)) {
            
            MouseInfo mouse = Greenfoot.getMouseInfo();
            if (mouse != null) {
                int mouseX = mouse.getX();
                int mouseY = mouse.getY();
                
                
                for (Item item : items) {
                    if (item.getX() - 50 < mouseX && mouseX < item.getX() + 50 &&
                        item.getY() - 50 < mouseY && mouseY < item.getY() + 50) {
                        
                        item.purchase();
                        return true;
                    }
                }
            }
        }
        return false; 
    }
}