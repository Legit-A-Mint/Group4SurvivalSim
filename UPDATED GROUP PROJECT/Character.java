import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Caracter here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Character extends World
{
    Image display;
    StatsLabel characterLabel;
    Button textBack;
    
    Button left;
    Button right;
    Button next;
    
    private String[] listOfCharacters = {"boy.png","girl.png","sus.png"};
    protected static int characterNum = 0;
    public Character()
    {    
        //create an unbounded world 
        super(1024, 576, 1, false);
        addObject(new Background(), 512, 288);
        addObject(characterLabel = new StatsLabel(new GreenfootImage("CHARACTER", 100, Color.WHITE, null)), getWidth()/2, 100);
        addObject(display = new Image(getPlayerModel(), 300, 300), 512, 288);
        addObject(left = new Button("left", new String[]{"charArror.png","charArror.png","charArror.png"}, false, 5), 362, 288);
        addObject(right = new Button("right", new String[]{"charArror.png","charArror.png","charArror.png"}, false, 5), 662, 288);
        addObject(next = new Button("next", new String[]{"PlayButton.png","PlayButton.png","PlayButton.png"}, false, 0.5), 512, 475);
    }
    
    public void act()
    {
        if (Greenfoot.mouseClicked(left))
        {
            characterNum--;
            setDisplay();
        }
        if (Greenfoot.mouseClicked(right))
        {
            characterNum++;
            setDisplay();
        }
        if (Greenfoot.mouseClicked(next))
        {
            Greenfoot.setWorld(new StatsEditor(getPlayerModel()));
        }
        display.changeImg(getPlayerModel(), 300, 300);
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
        return listOfCharacters[characterNum];
    }
}
