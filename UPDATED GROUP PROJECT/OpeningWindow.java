import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class OpeningWindow here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class OpeningWindow extends World
{

    /**
     * Constructor for objects of class OpeningWindow.
     * 
     */
    public OpeningWindow()
    {    
        //create an unbounded world
        super(1024, 576, 1, false); 
        addObject(new StartScreen(), 512, 288);
        addObject(new Title(), 512, 180);
        addObject(new StartButton(), 512, 300);
    }
}
