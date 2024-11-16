import greenfoot.*;

/**
 * NetProjectile - A projectile with wide range but minimal damage.
 */
public class netProjectile extends Projectile {
    public netProjectile() {
        super(3);
        initialize(5, 3, 200); // Damage: 5, Speed: 3, Flight Duration: 200
    }

    public void act() {
        super.act();
    }
}
