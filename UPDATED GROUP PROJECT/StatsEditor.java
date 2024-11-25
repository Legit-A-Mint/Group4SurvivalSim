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
    private double speed = 1;
    private int difficulty = 1;
    private int coins = 0;
    
    // Multiplier for each difficulty
    private String[] diffName = {"EASY","MEDIUM","HARD","IMPOSSIBLE"};
    private String[] diffImg = {"easy.png", "normal.png", "insane.png", "impossible.png"};
    private double[] diffMulti = {0.3, 1, 1.3, 3};
    
    private GreenfootImage temp;
    StatsLabel healthTxt;
    StatsLabel speedTxt;
    StatsLabel diffTxt;
    StatsLabel coinsTxt;

    Button leftHealth;
    Button rightHealth;
    StatsLabel heartImg;
    
    Button leftSpeed;
    Button rightSpeed;
    StatsLabel speedImg;
    
    Button leftDifficulty;
    Button rightDifficulty;
    StatsLabel diffDisplay;
    
    Button leftCoins;
    Button rightCoins;
    StatsLabel coinsImg;
    
    Button start;
    
    public StatsEditor(String playerModel)
    {    
        super(1024, 576, 1, false);
        
        this.playerModel = playerModel;
        
        addObject(new Background(), 512, 288);
        addObject(leftHealth = new Button("leftHealth", new String[]{"charArror.png","charArror.png","charArror.png"}, false, 4), getWidth()/4 - 200, 180);
        addObject(rightHealth = new Button("rightHealth", new String[]{"charArror.png","charArror.png","charArror.png"}, false, 4), getWidth()/4 + 200, 180);
        
        addObject(leftSpeed = new Button("leftSpeed", new String[]{"charArror.png","charArror.png","charArror.png"}, false, 4), getWidth()/4*3 - 200, 180);
        addObject(rightSpeed = new Button("rightSpeed", new String[]{"charArror.png","charArror.png","charArror.png"}, false, 4), getWidth()/4*3 + 200, 180);
        
        addObject(leftDifficulty = new Button("leftDiff", new String[]{"charArror.png","charArror.png","charArror.png"}, false, 4), getWidth()/4 - 200, 380);
        addObject(rightDifficulty = new Button("rightDiff", new String[]{"charArror.png","charArror.png","charArror.png"}, false, 4), getWidth()/4 + 200, 380);
        
        addObject(leftCoins = new Button("leftCoins", new String[]{"charArror.png","charArror.png","charArror.png"}, false, 4), getWidth()/4*3 - 200, 380);
        addObject(rightCoins = new Button("rightCoins", new String[]{"charArror.png","charArror.png","charArror.png"}, false, 4), getWidth()/4*3 + 200, 380);
        
        addObject(healthTxt = new StatsLabel(new GreenfootImage("Health: " + Integer.toString(health), 50, Color.WHITE, null)), getWidth()/4, 180);
        addObject(speedTxt = new StatsLabel(new GreenfootImage("Speed: " + Double.toString(speed) + "x", 50, Color.WHITE, null)), getWidth()/4*3, 180);
        addObject(diffTxt = new StatsLabel(new GreenfootImage(getDifficultyText(difficulty), 50, Color.WHITE, null)), getWidth()/4, 380);
        addObject(coinsTxt = new StatsLabel(new GreenfootImage("Coins: " + Integer.toString(coins), 50, Color.WHITE, null)), getWidth()/4*3, 380);
        
        addObject(heartImg = new StatsLabel(new GreenfootImage("pixel_Heart.png"), 80, 80), getWidth()/4, 100);
        addObject(speedImg = new StatsLabel(new GreenfootImage("SpeedIcon.png"), 80, 80), getWidth()/4*3, 100);
        addObject(diffDisplay = new StatsLabel(new GreenfootImage(getDifficultyImage(difficulty)),80,80), getWidth()/4, 300);
        addObject(coinsImg = new StatsLabel(new GreenfootImage("coin.png"),80,80), getWidth()/4*3, 300);
        
        addObject(start = new Button("start",new String[]{"PlayButton.png","PlayButton.png","PlayButton.png"}, false, 0.5), 512, 500);
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
                speed -= 0.1;
            speed = Math.round(speed*100);
            speed = speed/100;
            speedTxt.setImage(new GreenfootImage("Speed: " + Double.toString(speed) + "x", 50, Color.WHITE, null));
        }
        if (Greenfoot.mouseClicked(rightSpeed))
        {
            if (speed < 2)
                speed += 0.1;
            speed = Math.round(speed*100);
            speed = speed/100;
            speedTxt.setImage(new GreenfootImage("Speed: " + Double.toString(speed) + "x", 50, Color.WHITE, null));
        }
        if (Greenfoot.mouseClicked(leftDifficulty))
        {
            if (difficulty > 0)
                difficulty--;
            diffTxt.setImage(new GreenfootImage(getDifficultyText(difficulty), 50, Color.WHITE, null));
            diffDisplay.setImage(new GreenfootImage(getDifficultyImage(difficulty)));
            diffDisplay.scale(80,80);
        }
        if (Greenfoot.mouseClicked(rightDifficulty))
        {
            if (difficulty < 3)
                difficulty++;
            diffTxt.setImage(new GreenfootImage(getDifficultyText(difficulty), 50, Color.WHITE, null));
            diffDisplay.setImage(new GreenfootImage(getDifficultyImage(difficulty)));
            diffDisplay.scale(80,80);
        }
        if (Greenfoot.mouseClicked(leftCoins))
        {
            if (coins > 0)
                coins--;
            coinsTxt.setImage(new GreenfootImage("Coins: " + Integer.toString(coins), 50, Color.WHITE, null));
        }
        if (Greenfoot.mouseClicked(rightCoins))
        {
            coins++;
            coinsTxt.setImage(new GreenfootImage("Coins: " + Integer.toString(coins), 50, Color.WHITE, null));
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
    
    public String getDifficultyImage(int difficulty)
    {
        return diffImg[difficulty];
    }
}
