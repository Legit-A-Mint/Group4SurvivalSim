import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Caracter here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SelectCharacter extends World
{
    public SelectCharacter()
    {    
        //create an unbounded world 
        super(1024, 576, 1, false);
        addObject(new CharacterSelect(), 512, 288);
        addObject(new DisplayCharacter(), 512, 288);
        addObject(new Left(), 412, 288);
        addObject(new Left(), 612, 288);
        addObject(new FinalButton(), 512, 500);
    }
}
