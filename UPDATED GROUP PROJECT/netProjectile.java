import greenfoot.*;

/**
 * NetProjectile - A projectile with wide range but minimal damage.
 */
public class netProjectile extends Projectile {
    public netProjectile() {
        speed = 1.5;
        damage = 1;
        lifeSpan = 3;
    }

    public void act() {
        super.act();
    }
}
