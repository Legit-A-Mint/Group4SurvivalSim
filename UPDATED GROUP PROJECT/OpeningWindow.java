import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class OpeningWindow here.
 * 
 * @author (your name) 
 * 2.04
 */
public class OpeningWindow extends World
{
    NextButton start;
    
    public OpeningWindow()
    {    
        //create an unbounded world
        super(1024, 576, 1, false); 
        addObject(new Background(), 512, 288);
        addObject(new Title(), 512, 180);
        addObject(start = new NextButton(0), 512, 300);
    }
    
    public void act()
    {
        if (Greenfoot.mouseClicked(start))
        {
            Greenfoot.setWorld(new Character());
        }
    }
}
