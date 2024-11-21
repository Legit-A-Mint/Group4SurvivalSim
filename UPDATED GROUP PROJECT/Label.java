import greenfoot.*;

public class Label extends Actor {
    private GreenfootImage image;
    private String text;
    private int fontSize;

    public Label(String text, int fontSize) {
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
}
