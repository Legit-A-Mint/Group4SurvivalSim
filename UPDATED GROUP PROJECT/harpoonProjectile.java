import greenfoot.*;

/**
 * harpoonProjectile - A balanced projectile with moderate damage and speed.
 */

/** All subclasses require a Speed, lifeSpan, Damage */

public class harpoonProjectile extends Projectile {
    // set it up in constructor 
    public harpoonProjectile() {
        speed = 1.25;
    }

    public void act() {
        
        // super act always goes last n case of null pointer exception
        super.act();
    }
    
    // Specific effects of weapons go under here
}
