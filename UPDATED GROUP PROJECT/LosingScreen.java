import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class EndScreen here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class LosingScreen extends World
{
    Button restart;
    ImageDisplay waveCount;
    ImageDisplay dead;
    
    public LosingScreen(int wave)
    {    
        super(1024, 576, 1, false);
        
        GreenfootImage background = new GreenfootImage(getWidth(), getHeight());
        background.setColor(Color.BLACK);
        background.fill();
        setBackground(background);
        
        addObject(dead = new ImageDisplay(new GreenfootImage("GAME OVER", 100, Color.WHITE, null)), getWidth()/2, 200);
        addObject(waveCount = new ImageDisplay(new GreenfootImage("Wave: " + wave, 50, Color.WHITE, null)), getWidth()/2, 300);
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
