import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Item class 
 */
public class Item extends Actor
{
    private String name;
    private int price;

    public Item(String name, int price)
    {
        this.name = name;
        this.price = price;
         
        setImage(new GreenfootImage(name + ".png")); 
    }

     
    public void purchase()
    {
        System.out.println(name + " purchased for " + price + " coins!");
    }

     
    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
}
