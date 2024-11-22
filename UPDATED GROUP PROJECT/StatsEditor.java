import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Stats here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class StatsEditor extends World
{
    String playerModel;
    
    private int health = 100;
    private int speed = 10;
    private int difficulty = 1;
    
    // Multiplier for each difficulty
    private String[] diffName = {"EASY","MEDIUM","HARD","IMPOSSIBLE"};
    private double[] diffMulti = {0.3, 1, 1.3, 3};
    
    private GreenfootImage temp;
    StatsLabel healthTxt;
    StatsLabel speedTxt;
    StatsLabel diffTxt;

    LeftButton leftHealth;
    RightButton rightHealth;
    
    LeftButton leftSpeed;
    RightButton rightSpeed;
    
    LeftButton leftDifficulty;
    RightButton rightDifficulty;
    
    LeftButton leftCoins;
    RightButton rightCoins;
    
    NextButton start;
    
    public StatsEditor(String playerModel)
    {    
        super(1024, 576, 1, false);
        
        this.playerModel = playerModel;
        
        addObject(new Background(), 512, 288);
        addObject(leftHealth = new LeftButton(), getWidth()/4 - 200, 150);
        addObject(rightHealth = new RightButton(), getWidth()/4 + 200, 150);
        
        addObject(leftSpeed = new LeftButton(), getWidth()/4*3 - 200, 150);
        addObject(rightSpeed = new RightButton(), getWidth()/4*3 + 200, 150);
        
        addObject(leftDifficulty = new LeftButton(), getWidth()/4 - 200, 350);
        addObject(rightDifficulty = new RightButton(), getWidth()/4 + 200, 350);
        
        addObject(leftCoins = new LeftButton(), getWidth()/4*3 - 200, 350);
        addObject(rightCoins = new RightButton(), getWidth()/4*3 + 200, 350);
        
        addObject(healthTxt = new StatsLabel(new GreenfootImage("Health: " + Integer.toString(health), 50, Color.WHITE, null)), getWidth()/4, 150);
        addObject(speedTxt = new StatsLabel(new GreenfootImage("Speed: " + Integer.toString(speed), 50, Color.WHITE, null)), getWidth()/4*3, 150);
        addObject(diffTxt = new StatsLabel(new GreenfootImage(getDifficultyText(difficulty), 50, Color.WHITE, null)), getWidth()/4, 350);
        
        addObject(start = new NextButton(0), 512, 500);
    }
    
    public void act()
    {
        if (Greenfoot.mouseClicked(leftHealth)||Greenfoot.isKeyDown("left"))
        {
            if (health > 10)
                health -= 10;
            healthTxt.setImage(new GreenfootImage("Health: " + Integer.toString(health), 50, Color.WHITE, null));
        }
        if (Greenfoot.mouseClicked(rightHealth)||Greenfoot.isKeyDown("Right"))
        {
            if (health < 9990)
                health += 10;
            healthTxt.setImage(new GreenfootImage("Health: " + Integer.toString(health), 50, Color.WHITE, null));
        }
        if (Greenfoot.mouseClicked(leftSpeed))
        {
            if (speed > 0)
                speed--;
            speedTxt.setImage(new GreenfootImage("Speed: " + Integer.toString(speed), 50, Color.WHITE, null));
        }
        if (Greenfoot.mouseClicked(rightSpeed))
        {
            if (speed < 20)
                speed++;
            speedTxt.setImage(new GreenfootImage("Speed: " + Integer.toString(speed), 50, Color.WHITE, null));
        }
        if (Greenfoot.mouseClicked(leftDifficulty))
        {
            if (difficulty > 0)
                difficulty--;
            diffTxt.setImage(new GreenfootImage(getDifficultyText(difficulty), 50, Color.WHITE, null));
        }
        if (Greenfoot.mouseClicked(rightDifficulty))
        {
            if (difficulty < 3)
                difficulty++;
            diffTxt.setImage(new GreenfootImage(getDifficultyText(difficulty), 50, Color.WHITE, null));
        }
        if (Greenfoot.mouseClicked(start))
        {
            Greenfoot.setWorld(new SimulationWorld(playerModel, health, speed, diffMulti[difficulty]));
            SimulationWorld.ambientSound.playLoop();
        }
    }
    
    public String getDifficultyText(int difficulty)
    {
        return diffName[difficulty];
    }
}
