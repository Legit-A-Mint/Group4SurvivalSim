import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class EndScreen here.
 * 
 * @Jonathan
 */
public class WinningScreen extends World
{
    Button restart;
    ImageDisplay dead;
    
    public WinningScreen()
    {    
        super(1024, 576, 1, false);
        
        GreenfootImage background = new GreenfootImage(getWidth(), getHeight());
        background.setColor(Color.BLACK);
        background.fill();
        setBackground(background);
        
        addObject(dead = new ImageDisplay(new GreenfootImage("YOU WIN", 100, Color.WHITE, null)), getWidth()/2, 200);
        
        addObject(restart = new Button("restart",new String[]{"PlayButton.png","PlayButton.png","PlayButton.png"}, false, 0.5, 1, false), 512, 400);
    }
    
    public void act()
    {
        if (Greenfoot.mouseClicked(restart))
        {
            Greenfoot.setWorld(new OpeningWindow());
        }
    }
}