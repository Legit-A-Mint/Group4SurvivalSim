import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
/*
 * Full Coin Class
 * @Darius
 */
public class Coins extends Effects
{    
    private GreenfootImage[] img;
    private int despawnTimer;
    public Coins() {
        img = new GreenfootImage[4];
        
        // Adjust for faster/slower depsawn
        despawnTimer = 1000;
        img[0] = new GreenfootImage("coin.png");
        for(int i = 0; i < img.length; i++){
            img[i] = new GreenfootImage("CoinF" + (i+1) + ".png");
        }
    }
    
    public void act(){
        if(despawnTimer > 0){
            despawnTimer --;
        }
        animate(this, img, img[0].getWidth(), img[0].getHeight(), 10, 1);
        if(despawnTimer == 0){
            getWorld().removeObject(this);
        }
    }
}
