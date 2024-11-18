import greenfoot.*; 

public class ShopUI extends Interface {
    private GreenfootImage bottomBar;
    private int harpoonPrice = 10;
    private int netPrice = 15;

    public ShopUI(String name, int width, int height, int myX, int myY) {
        super(name, myX, myY);

        bottomBar = new GreenfootImage(width, height);
        bottomBar.setColor(Color.BLACK);
        bottomBar.fill();
        setImage(bottomBar);
    }

    @Override
    public boolean isUserInteracting() {
        // Implement logic to check for user interaction with the shop UI.
        // In this case, assume interaction occurs if the mouse is clicked on the UI.
        return Greenfoot.mouseClicked(this);
    }

    public void act() {
        if (isUserInteracting()) {
            Player player = getPlayer();
            if (player != null) {
                handlePurchase(player);
            }
        }
    }

    private Player getPlayer() {
        if (!getWorld().getObjects(Player.class).isEmpty()) {
            return getWorld().getObjects(Player.class).get(0);
        }
        return null;
    }

    private void handlePurchase(Player player) {
        if (Greenfoot.isKeyDown("1")) { // Buy HarpoonProjectile
            if (player.getCoins() >= harpoonPrice) {
                player.addItem(new harpoonProjectile());
                player.spendCoins(harpoonPrice);
                Greenfoot.playSound("purchase.wav");
            } else {
                showMessage("Not enough coins for Harpoon!");
            }
        }

        if (Greenfoot.isKeyDown("2")) { // Buy NetProjectile
            if (player.getCoins() >= netPrice) {
                player.addItem(new netProjectile());
                player.spendCoins(netPrice);
                Greenfoot.playSound("purchase.wav");
            } else {
                showMessage("Not enough coins for Net!");
            }
        }
    }

    private void showMessage(String message) {
        System.out.println(message); // Optional: Replace with in-game display
    }
}
