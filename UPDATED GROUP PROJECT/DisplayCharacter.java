import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * Write a description of class DisplayCharacter here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class DisplayCharacter extends CharacterSelect
{
    protected ArrayList<String> listOfCharacters = new ArrayList<String>();
    protected int characterNum = 0;
    public DisplayCharacter ()
    {
        listOfCharacters.add("boy.png");
        listOfCharacters.add("girl.png");
        listOfCharacters.add("sus.png");
    }
    public void act()
    {
        setDisplay(characterNum);
    }
    public void setDisplay(int num)
    {
        if (num > 2)
            num = 0;
        if (num < 0)
            num = 2;
        setImage(listOfCharacters.get(num));
        getImage().scale(300, 300);
    }
}
