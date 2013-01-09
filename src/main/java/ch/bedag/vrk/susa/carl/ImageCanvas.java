package ch.bedag.vrk.susa.carl;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.text.MessageFormat;

/**
 * Buran.
 *
 * @author: ${USER} Date: 09.01.13 Time: 19:51
 */
public class ImageCanvas extends JComponent {

    public static final int CANVAS_WIDTH = 640;
    public static final int CANVAS_HEIGHT = 480;

    protected AffineTransform transform;

    // Define an arrow shape using a polygon centered at (0, 0)
    int[] polygonXs = {-20, 0, +20, 0};
    int[] polygonYs = {20, 10, 20, -20};
    Shape shape = new Polygon(polygonXs, polygonYs, polygonXs.length);
    double x = 0, y = 0;  // (x, y) position of this Shape


    private BufferedImage image;
    private int imageWidth;
    private int imageHeight;

    public ImageCanvas(BufferedImage image) {
        setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
        this.transform = new AffineTransform(1f,0f,0f,1f,x,y);
        setImageInternal(image);
    }

    protected final BufferedImage getImage() {
        return image;
    }

    protected final int getImageWidth() {
        return imageWidth;
    }

    protected final int getImageHeight() {
        return imageHeight;
    }

    private void setImageInternal(BufferedImage image) {
        this.image = image;
        this.imageWidth = image.getWidth();
        this.imageHeight = image.getHeight();
    }

    protected final void setImage(BufferedImage image) {
        setImageInternal(image);
        this.repaint();
    }

    protected void beforePaint(Graphics2D g2d) {
    }

    protected void afterPaint(Graphics2D g2d) {
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        Graphics2D g2d = (Graphics2D) graphics;
        beforePaint(g2d);

        setBackground(Color.WHITE);
        super.paintComponent(graphics);

        int x = 0;
        int y = 0;
        g2d.drawImage(this.image, this.transform, null);

        afterPaint(g2d);

        /*g2d.setColor(Color.RED);
        g2d.draw(this.borderShape);

        g2d.drawString(MessageFormat.format("{0} x {1}", getWidth(), getHeight()), 15, 12); */
    }
}
