import greenfoot.*;
/**
 * Write a description of class StatsLabel here.
 * 
 * @Jonathan 
 * @1.0.0
 */
public class Label extends Interface {
    private GreenfootImage image;
    private String text;
    private int fontSize;

    public Label(String text, int fontSize, boolean canFade) {
        this.canFade = canFade;
        this.text = text;
        this.fontSize = fontSize;
        updateImage();
    }

    public void setText(String newText) {
        text = newText;
        updateImage();
    }

    private void updateImage() {
        image = new GreenfootImage(text, fontSize, Color.WHITE, new Color(0, 0, 0, 0)); 
        setImage(image);
    }
    
    @Override
    protected boolean isUserInteracting() {
        return Greenfoot.mouseMoved(this); // Detect if the mouse is moving over the health bar
    }
}
