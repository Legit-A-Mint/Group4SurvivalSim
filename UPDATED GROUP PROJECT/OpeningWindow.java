import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Title screen
 * @Jonathan
 * 2.08
 */
public class OpeningWindow extends World
{
    // UI
    Button start;
    
    public OpeningWindow()
    {    
        //create an unbounded world
        super(1024, 576, 1, false); 
        addObject(new Image("PixelOceanStart.png", 1024, 576), 512, 288);
        addObject(new Image("OceanSurvival.png"), 512, 180);
        addObject(start = new Button("start",new String[]{"PlayButton.png","PlayButton.png","PlayButton.png"}, false, 0.5, 1, false), 512, 340);
    }
    
    public void act()
    {
        if (Greenfoot.mouseClicked(start))
        {
            Greenfoot.setWorld(new Character());
        }
    }
}
