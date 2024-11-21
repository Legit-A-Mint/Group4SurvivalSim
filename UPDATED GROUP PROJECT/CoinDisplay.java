import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class CoinDisplay extends Actor
{
    private Player player;
    private GreenfootImage image;

    public CoinDisplay(Player player)
    {
        this.player = player;
        image = new GreenfootImage(200, 50);
        setImage(image);
    }

    public void update()
    {
        image.clear();  // Clear the previous display
        image.setColor(Color.WHITE);
        image.drawString("Coins: " + player.getCoins(), 10, 30);  // Draw current coin count
    }
}
