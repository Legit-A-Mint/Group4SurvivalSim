import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
/**
 * Write a description of class StatsLabel here.
 * 
 * @Jonathan 
 * @1.0.0
 */
public class Shop extends SuperSmoothMover
{
    private GreenfootImage shopBackground;
    HealthPotIcon health;
    raft1ShopIcon wood;
    raft2ShopIcon metal;
    NetShopIcon net;
    ShurikenShopIcon shuriken;
    HarpoonShopIcon harpoon;

    public Shop()
    {    
        shopBackground = new GreenfootImage("shop.png");
        setImage(shopBackground);
        displayItems(true);
    }

    public void displayItems(boolean show)
    {
        if (show)
        {
            if (getWorld() != null)
            {
                getWorld().addObject(health = new HealthPotIcon(), 525, 570);
                getWorld().addObject(wood = new raft1ShopIcon(), 600, 570);
                getWorld().addObject(metal = new raft2ShopIcon(), 675, 570);
                getWorld().addObject(net = new NetShopIcon(), 750, 570);
                getWorld().addObject(shuriken = new ShurikenShopIcon(), 825, 570);
                getWorld().addObject(harpoon = new HarpoonShopIcon(), 900, 570);
            }
        }
        else
        {
            if (getWorld() != null)
            {
                getWorld().removeObject(health);
                getWorld().removeObject(wood);
                getWorld().removeObject(metal);
                getWorld().removeObject(net);
                getWorld().removeObject(shuriken);
                getWorld().removeObject(harpoon);
            }
        }
    }

    public void showShop(boolean show){
        if(show){
            shopBackground.setTransparency(255);
            displayItems(true);
        }
        else{
            shopBackground.setTransparency(0);
            displayItems(false);
        }
    }
}