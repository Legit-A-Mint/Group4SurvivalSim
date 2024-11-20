import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class FinalButton here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class FinalButton extends DisplayCharacter
{
    public FinalButton ()
    {
        setImage("PlayButton.png");
        getImage().scale(393, 159);
    }
    public void act()
    {
        if (Greenfoot.mouseClicked(this))
        {
            Greenfoot.setWorld(new SimulationWorld(getPlayerModel(), 10));
            SimulationWorld.ambientSound.playLoop();
        }
    }
}
