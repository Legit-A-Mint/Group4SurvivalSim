import greenfoot.*;

/**
 * Write a description of class Scroller here.
 * 
 * @Andrew
 * @version (a version number or a date)
 */
public class Scroller extends SuperSmoothMover
{
    private World scrollWorld;
    private GreenfootImage[][] scrollImages;
    private int scrollWidth, scrollHeight;
    private int worldWidth, worldHeight;
    private int relativeX, relativeY;
    
    public Scroller(World scrThis, GreenfootImage scrImage)
    {
        this(scrThis, scrImage, scrImage.getWidth(), scrImage.getHeight());
    }
    
    public Scroller(World scrThis, GreenfootImage repImage, int wide, int high)
    {
        // the field values
        scrollWorld = scrThis;
        worldWidth = scrThis.getWidth();
        worldHeight = scrThis.getHeight();
        scrollWidth = wide;
        scrollHeight = high;
        // the scrolling image
        if (repImage == null) repImage = scrThis.getBackground();
        GreenfootImage scrImage = new GreenfootImage(wide, high);
        scrImage.setColor(Color.WHITE);
        scrImage.fill(); // ensures no transparent pixels in background
        for (int i=0; i<wide; i+=repImage.getWidth())
            for (int j=0; j<high; j+=repImage.getHeight())
                scrImage.drawImage(repImage, i, j);
        // the drawing panels (to help reduce lag by reducing the number of pixels drawn when scrolling)
        int x = 1+scrollWidth/worldWidth; // number of panels across
        int y = 1+scrollHeight/worldHeight; // number of panels down
        scrollImages = new GreenfootImage[y][x]; // creates the array
        for (int j=0; j<y; j++) for (int i=0; i<x; i++) // fills the array
        {
            scrollImages[j][i] = new GreenfootImage(worldWidth, worldHeight);
            scrollImages[j][i].drawImage(scrImage, -i*worldWidth, -j*worldHeight);
        }
        scrollBackground(); // sets initial world background image
    }
    /** sets the world background image determined by the current scroll values */
    private void scrollBackground()
    {
        int x = (-relativeX)/worldWidth; // panel x index
        int y = (-relativeY)/worldHeight; // panel y index
        int dx = -((-relativeX)%worldWidth); // drawing x offset
        int dy = -((-relativeY)%worldHeight); // drawing y offset
        GreenfootImage bg = scrollWorld.getBackground(); // gets local reference to world background
        bg.drawImage(scrollImages[y][x], dx, dy); // draw top-left image
        if (dx != 0) // draw top-right image if needed
            bg.drawImage(scrollImages[y][x+1], dx+worldWidth, dy);
        if (dy != 0) // draw bottom-left image if needed
            bg.drawImage(scrollImages[y+1][x], dx, dy+worldHeight);
        if (dx != 0 && dy != 0) // draw bottom-right image if needed
            bg.drawImage(scrollImages[y+1][x+1], dx+worldWidth, dy+worldHeight);
    }
    
    public void scroll(double dx, double dy)
    {
        // limit change values
        if (dx > 0 && relativeX +dx > 0) dx = -relativeX;
        if (dx < 0 && relativeX+dx <= worldWidth-scrollWidth)
            dx = (worldWidth-scrollWidth)-relativeX;
        if (dy > 0 && relativeY+dy > 0) dy = -relativeY;
        if (dy < 0 && relativeY+dy <= worldHeight-scrollHeight)
            dy = (worldHeight-scrollHeight)-relativeY;
        // change scrolling values
        relativeX += dx;
        relativeY += dy;
        // update world background
        scrollBackground();
        // keep actors in place with relation to background image
        for (Object obj : scrollWorld.getObjects(null))
        {
            SuperSmoothMover actor = (SuperSmoothMover) obj;
            actor.setLocation(actor.getPreciseX() + dx , actor.getPreciseY() + dy);
        }
    }
    
    /** returns the overall amount scrolled horizontally */
    public int getScrolledX()
    {
        return relativeX;
    }
    
    /** returns the overall amount scrolled vertically */
    public int getScrolledY()
    {
        return relativeY;
    }
    
    /** returns the width of the scrolling area */
    public int getScrollWidth()
    {
        return scrollWidth;
    }
    
    /** returns the height of the scrolling area */
    public int getScrollHeight()
    {
        return scrollHeight;
    }
    
}
