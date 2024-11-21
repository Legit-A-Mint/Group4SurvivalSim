import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class shopUI extends UI
{
    private Player player;
    private int healCost = 50;
    private int spearCost = 100;
    private int raftCost = 200;

    // UI elements (button images and item images)
    private GreenfootImage healImage, spearImage, raftImage, soldOutImage;
    private GreenfootImage healButton, spearButton, raftButton;

    private boolean healBought = false;
    private boolean spearBought = false;
    private boolean raftBought = false;

    public shopUI(Player player) {
        this.player = player;

        // Load images for the items
        healImage = new GreenfootImage("healUI.png"); // Image for heal item
        spearImage = new GreenfootImage("spearUI.png"); // Image for spear item
        raftImage = new GreenfootImage("raftUI.png"); // Image for raft item
        soldOutImage = new GreenfootImage("sold_out.png"); // Image to indicate item is sold out

        // Load button images (for the purchase button)
        healButton = new GreenfootImage("db_1.png"); 
        spearButton = new GreenfootImage("db_2.png"); 
        raftButton = new GreenfootImage("db_3.png");
    }

    public void act() {
        // Display the shop background, items, and coins
        drawShopUI();

        // Display the player's coin count on the shop UI
        showCoins();

        // Check if the player wants to buy the heal item
        if (!healBought && player.getHp() < 500 && Greenfoot.mouseClicked(this) && isMouseOverButton(100, 150, healButton)) {
            buyItem("heal", healCost);
        }

        // Check if the player wants to buy the spear item
        if (!spearBought && player.getCoins() >= spearCost && Greenfoot.mouseClicked(this) && isMouseOverButton(100, 250, spearButton)) {
            buyItem("spear", spearCost);
        }

        // Check if the player wants to buy the raft item
        if (!raftBought && player.getCoins() >= raftCost && Greenfoot.mouseClicked(this) && isMouseOverButton(100, 350, raftButton)) {
            buyItem("raft", raftCost);
        }
    }

    private void drawShopUI() {
        // Draw the UI background
        getWorld().getBackground().drawImage(healButton, 100, 150);  // Draw heal button
        getWorld().getBackground().drawImage(spearButton, 100, 250);  // Draw spear button
        getWorld().getBackground().drawImage(raftButton, 100, 350);   // Draw raft button

        // Draw items if they are available
        if (player.getHp() < 500 && !healBought) {
            getWorld().getBackground().drawImage(healImage, 250, 150);  // Show heal item
        } else if (healBought) {
            getWorld().getBackground().drawImage(soldOutImage, 250, 150);  // Cross out heal item when bought
        }

        if (player.getCoins() >= spearCost && !spearBought) {
            getWorld().getBackground().drawImage(spearImage, 250, 250);  // Show spear item
        } else if (spearBought) {
            getWorld().getBackground().drawImage(soldOutImage, 250, 250);  // Cross out spear item when bought
        }

        if (player.getCoins() >= raftCost && !raftBought) {
            getWorld().getBackground().drawImage(raftImage, 250, 350);  // Show raft item
        } else if (raftBought) {
            getWorld().getBackground().drawImage(soldOutImage, 250, 350);  // Cross out raft item when bought
        }
    }

    private void showCoins() {
        // Create a new image with the coin count text
        GreenfootImage coinText = new GreenfootImage("Coins: " + player.getCoins(), 50, Color.WHITE, Color.BLACK);
        // Position the coin text on the screen
        getWorld().getBackground().drawImage(coinText, 100, 100);
    }

    private boolean isMouseOverButton(int x, int y, GreenfootImage buttonImage) {
        MouseInfo mouse = Greenfoot.getMouseInfo();
        if (mouse != null) {
            int mouseX = mouse.getX();
            int mouseY = mouse.getY();
            if (mouseX >= x && mouseX <= x + buttonImage.getWidth() && mouseY >= y && mouseY <= y + buttonImage.getHeight()) {
                return true;
            }
        }
        return false;
    }

    private void buyItem(String itemType, int cost) {
        if (player.getCoins() >= cost) {
            player.addCoins(-cost);  // Deduct coins
            System.out.println(itemType + " purchased!");

            // Implement the logic to give the player the item here
            if (itemType.equals("heal")) {
                healBought = true;
                // Logic to heal the player
                player.addHp(500); // Example of healing
            } else if (itemType.equals("spear")) {
                spearBought = true;
                // Logic to give the player the spear (e.g., add to inventory)
                player.addInventory("spear");
            } else if (itemType.equals("raft")) {
                raftBought = true;
                // Logic to give the player the raft (e.g., add to inventory)
                player.addInventory("raft");
            }
        } else {
            System.out.println("Not enough coins to buy " + itemType + "!");
        }
    }
}
