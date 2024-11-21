import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
/*
 * Full Coin Class
 * @Darius
 */
public class Coins extends Effects
{
    protected static final int COIN_VALUE = 10; // Coins add 10 each when collected
    
    public Coins() {
        GreenfootImage coinImage = new GreenfootImage("coin.png"); // Placeholder for coin image
        setImage(coinImage);
    }

    public void act() {
        checkCollisionWithPlayer();
    }

    // Check if player collides with the coin
    private void checkCollisionWithPlayer() {
        Actor player = getOneIntersectingObject(Player.class);
        if (player != null) {
            Player p = (Player) player;
            p.addCoins(COIN_VALUE);  // Add coins to the player
            getWorld().removeObject(this);  // Remove the coin from the world
        }
    }
}
