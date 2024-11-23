import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Seagull here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Seagull extends Ambience
{
    private GreenfootImage[] imgflying;
    private GreenfootImage img;
    private boolean flying;
    private int direction;
    private double speed;

    public Seagull(boolean flying, int dir, double speed){        
        this.flying = flying;
        this.speed = speed;
        direction = dir;

        if(flying){
            imgflying = new GreenfootImage[6];
            for(int i = 0; i < imgflying.length; i++){
                imgflying[i] = new GreenfootImage("SeagullF" + (i+1) + ".png");
                if(dir == -1){
                    imgflying[i].mirrorHorizontally();
                }
            }
        }
        else{
            img = new GreenfootImage("Seagull.png");
            if(dir == -1){
                img.mirrorHorizontally();
            }
            setImage(img);
        }

    }

    public void act()
    {
        if(flying){
            animate(this, imgflying, imgflying[0].getWidth(), imgflying[0].getHeight(), 10, 1);
            move(direction * speed);
        }
    }
}
