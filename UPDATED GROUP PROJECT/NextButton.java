import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class StartButton here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class NextButton extends UI
{
    // 0 = first screen, 1 = second ...cont...
    private String[] buttons = {"PlayButton.png","PlayButton.png","PlayButton.png"};
    
    public NextButton(int num)
    {
        setImage(buttons[num]);
        getImage().scale(393, 159);
    }
}
