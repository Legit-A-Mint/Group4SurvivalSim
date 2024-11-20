import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Stats here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class StatsEditor extends World
{
    private int health = 10;
    private int speed = 10;
    private int difficulty = 1;
    
    // Multiplier for each difficulty
    private String[] diffName = {"EASY","MEDIUM","HARD","IMPOSSIBLE"};
    private double[] diffMulti = {0.7, 1, 1.3, 2};

    LeftButton left;
    RightButton right;
    
    public StatsEditor()
    {    
        super(1024, 576, 1, false);
        
        
    }
}
