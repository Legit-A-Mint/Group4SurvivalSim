import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class shopUI extends UI
{
    private Player player;
    private int itemCost1 = 100;
    private int itemCost2 = 200;
    private GreenfootImage shopBackground;
    private GreenfootImage buttonBuy1;
    private GreenfootImage buttonBuy2;

    public shopUI(Player player) {
        this.player = player;
        shopBackground = new GreenfootImage(400, 300);
        buttonBuy1 = new GreenfootImage("db_1.png"); // Placeholder image for buy button
        buttonBuy2 = new GreenfootImage("db_2.png"); // Placeholder image for buy button
    }

    public void act() {
        // Display the shop background and buttons
        drawShopUI();

        // Display the player's coin count on the shop UI
        showCoins();

        // Check if the player wants to buy the first item
        if (Greenfoot.mouseClicked(this) && isMouseOverButton(100, 150, buttonBuy1)) {
            buyItem(itemCost1);
        }

        // Check if the player wants to buy the second item
        if (Greenfoot.mouseClicked(this) && isMouseOverButton(100, 250, buttonBuy2)) {
            buyItem(itemCost2);
        }
    }

    private void drawShopUI() {
        // Draw the shop UI background and buttons
        getWorld().getBackground().drawImage(shopBackground, 100, 100);
        getWorld().getBackground().drawImage(buttonBuy1, 100, 150);  // Draw first buy button
        getWorld().getBackground().drawImage(buttonBuy2, 100, 250);  // Draw second buy button
    }

    private void showCoins() {
        // Create a new image with the coin count text
        GreenfootImage coinText = new GreenfootImage("Coins: " + player.getCoins(), 50, Color.WHITE, Color.BLACK);

        // Position the coin text on the screen (you can adjust this for better positioning)
        getWorld().getBackground().drawImage(coinText, 100, 100);  // Display in the top-right corner
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

    private void buyItem(int cost) {
        if (player.getCoins() >= cost) {
            player.addCoins(-cost);  // Deduct coins
            System.out.println("Item purchased!");
            // Implement the logic to give the player the item here
        } else {
            System.out.println("Not enough coins!");
        }
    }
}
