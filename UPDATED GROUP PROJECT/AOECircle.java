import greenfoot.*;
import java.util.List;

/**
 * Write a description of class Kraken here.
 * 
 * @lumilk
 * @1.0.0
 */

public class AOECircle extends Effects{
    private int maxRadius;
    private int currentRadius;
    private int growthSpeed;
    private int damage;
    private boolean exploded;

    public AOECircle(int initialRadius, int maxRadius, int growthSpeed, int damage){
        this.maxRadius = maxRadius;
        this.currentRadius = initialRadius;
        this.growthSpeed = Math.max(growthSpeed, 1);
        this.damage = damage;
        exploded = false;

        // Create an initial transparent image
        GreenfootImage image = new GreenfootImage(maxRadius * 2, maxRadius * 2);
        setImage(image);
    }
    public void act() {
        //System.out.println("Current Radius: " + currentRadius);
        if (!exploded) {
            // Expand the AOE circle until it reaches its max size
            if (currentRadius < maxRadius) {
                currentRadius = Math.min(currentRadius + growthSpeed, maxRadius); // Prevent overshooting the max radius
                updateImage();
            } else {
                // Once fully grown, deal damage and trigger explosion animation
                dealDamage();
                triggerExplosion();
            }
        } else {
            // Handle explosion animation
            performExplosionAnimation();
        }
    }

    private void updateImage() {
        GreenfootImage image = getImage();
        image.clear();
        image.setColor(new Color(255, 0, 0, 128));
        image.fillOval(maxRadius - currentRadius, maxRadius - currentRadius, currentRadius * 2, currentRadius * 2);
        setImage(image);
    }

    private void dealDamage() {
        // Find all actors within the radius
        List<Player> playersInRange = getObjectsInRange(currentRadius, Player.class);
        for (Player player : playersInRange) {
            player.damageMe(damage);
        }
    }

    private void triggerExplosion() {
        exploded = true;

        // Change the image to represent an explosion
        GreenfootImage explosionImage = new GreenfootImage(maxRadius * 2, maxRadius * 2);
        explosionImage.setColor(new Color(255, 165, 0, 200)); // Bright orange for the explosion
        explosionImage.fillOval(0, 0, maxRadius * 2, maxRadius * 2);
        setImage(explosionImage);
    }

    private void performExplosionAnimation() {
        GreenfootImage image = getImage();
        int transparency = image.getTransparency() - 10; // Gradually reduce transparency
        if (transparency <= 0) {
            // Remove the explosion when fully faded out
            getWorld().removeObject(this);
        } else {
            image.setTransparency(transparency);
            setImage(image);
        }
    }
}