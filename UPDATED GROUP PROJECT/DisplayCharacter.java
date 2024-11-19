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
    protected static int characterNum = 0;
    public DisplayCharacter ()
    {
        listOfCharacters.add("boy.png");
        listOfCharacters.add("girl.png");
        listOfCharacters.add("sus.png");
    }
    public void act()
    {
        setImage(listOfCharacters.get(characterNum));
        getImage().scale(300, 300);
    }
    public void setDisplay()
    {
        if (characterNum > 2)
            characterNum = 0;
        if (characterNum < 0)
            characterNum = 2;
    }
    public String getPlayerModel()
    {
        return listOfCharacters.get(characterNum);
    }
}
