import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Chararacter select Window
 * Allow the user to change and modify their character
 * No effect on gameplayer
 * @Jonathan
 * 
 */
public class Character extends World
{
    // Images and lables
    Image display;
    ImageDisplay characterLabel;
    Button textBack;
    private String[] listOfCharacters = {"boy.png","girl.png","sus.png"};
    protected static int characterNum = 0;
    
    // UI
    Button left;
    Button right;
    Button next;
    
    
    public Character()
    {    
        //create an unbounded world 
        super(1024, 576, 1, false);
        
        // Cache images
        addObject(new Image("PixelOceanStart.png", 1024, 576), 512, 288);
        addObject(characterLabel = new ImageDisplay(new GreenfootImage("CHARACTER", 100, Color.WHITE, null)), getWidth()/2, 100);
        addObject(display = new Image(getPlayerModel(), 300, 300), 512, 288);
        addObject(left = new Button("left", new String[]{"charArrow1.png","charArrow2.png","charArrow3.png"}, false, 2 , -1, false), 362, 288);
        addObject(right = new Button("right", new String[]{"charArrow1.png","charArrow2.png","charArrow3.png"}, false, 2, 1, false), 662, 288);
        addObject(next = new Button("next", new String[]{"PlayButton.png","PlayButton.png","PlayButton.png"}, false, 0.5, 1, false), 512, 475);
    }
    
    public void act()
    {
        // switch the the highest index character, other than this
        if (Greenfoot.mouseClicked(left))
        {
            characterNum--;
            setDisplay();
        }
        // swtich to the lowest index character, other than this
        if (Greenfoot.mouseClicked(right))
        {
            characterNum++;
            setDisplay();
        }
        // move onto the next world
        if (Greenfoot.mouseClicked(next))
        {
            Greenfoot.setWorld(new StatsEditor(getPlayerModel()));
        }
        display.changeImg(getPlayerModel(), 300, 300);
    }
    
    // loop the index
    public void setDisplay()
    {
        if (characterNum > 2)
            characterNum = 0;
        if (characterNum < 0)
            characterNum = 2;
    }
    
    // return the character image that should be displayed
    public String getPlayerModel()
    {
        return listOfCharacters[characterNum];
    }
}
