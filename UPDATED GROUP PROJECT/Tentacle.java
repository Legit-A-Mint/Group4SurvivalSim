import greenfoot.*;

/**
 * Write a description of class Tentacle here.
 * 
 * @author (your name)
 * @version (a version number or a date)
 */
public class Tentacle extends Kraken {
    
    private GreenfootImage[] img;
    private EnemyHitbox hitbox;
    private boolean foundPlayer;
    private boolean createdHitbox;

    // Sound effect for attack
    private static final GreenfootSound attackSfx = new GreenfootSound("Tentacle.mp3");

    public Tentacle(double diffMulti) {
        super(diffMulti);
        hp = (int)(1*diffMulti);
        damage = (int)(10*diffMulti);
        attackCooldown = 30;
        img = new GreenfootImage[3];
        createdHitbox = false;

        for (int i = 0; i < img.length; i++) {
            img[i] = new GreenfootImage("TentacleF" + (i + 1) + ".png");
        }        
    }

    public void act() {
        // Create a hitbox if I haven't already
        createHitbox();
        
        // Get player object
        if (!foundPlayer) {
            if (getWorld().getObjects(Player.class) != null) {
                player = getWorld().getObjects(Player.class).get(0);
            }
        }
        
        // Idle animation
        animate(this, img, img[0].getWidth(), img[0].getHeight(), 24, 1);
        
        // Count down attackTimer when not 0
        if (this.attackTimer > 0) {
            attackTimer--;
        }
        
        // If colliding with player and attackTimer is 0, poison for a certain damage and # ticks
        if (getPlayerCollision()) {
            if (attackTimer == 0) {
                attackPlayer();
                attackTimer = attackCooldown;
            }
        }

        // Remove myself and my hitbox from the world if I don't have any more HP
        if (this.hp <= 0) {
            getWorld().removeObject(hitbox);
            getWorld().removeObject(this);
        }
    }

    private void attackPlayer() {
        // Apply poison to the player
        getPlayer().poisonMe(damage, 10);

        // Play the attack sound
        playAttackSound();
    }

    private void playAttackSound() {
        attackSfx.play();
    }

    // Override methods from superclass
    @Override
    public EnemyHitbox getHitbox() {
        return hitbox;
    }

    @Override
    protected void createHitbox() {
        if (!createdHitbox) {
            hitbox = new EnemyHitbox(img[0].getWidth() / 2, img[0].getHeight(), 0, 0, this, 2.5, false);
            getWorld().addObject(hitbox, 0, 0);
            createdHitbox = true;
        }   
    }

    @Override
    public void damageMe(int damage) {
        this.hp -= damage;
    }

    @Override
    protected boolean getPlayerCollision() {
        try {
            if (player.getHitbox() != null) {
                return (this.intersects(player.getHitbox()));   
            }
        } catch (Exception e) {}

        return false;
    }
}
