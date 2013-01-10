package ch.bedag.vrk.susa.carl;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

/**
 * Buran.
 *
 * @author: ${USER} Date: 09.01.13 Time: 19:51
 */
public class ImageCanvas extends JComponent {

    public static final int CANVAS_WIDTH = 640;
    public static final int CANVAS_HEIGHT = 480;

    protected AffineTransform transform;

    protected final Point2D imgPos1 = new Point2D.Float();
    protected final Point2D imgPos2 = new Point2D.Float();

    private BufferedImage image;
    private int imageWidth;
    private int imageHeight;

    private final Point2D zero = new Point2D.Float(0, 0);
    private final Point2D imageSize = new Point2D.Float();

    public ImageCanvas(BufferedImage image) {
        setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
        this.transform = new AffineTransform(1f, 0f, 0f, 1f, 0, 0);
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
        this.imageSize.setLocation(this.imageWidth, this.imageHeight);
    }

    protected final void setImage(BufferedImage image) {
        setImageInternal(image);
        this.repaint();
    }

    private void calculateImgPos() {
        this.transform.transform(this.zero, this.imgPos1);
        this.transform.transform(this.imageSize, this.imgPos2);
    }

    protected void beforePaint(Graphics2D g2d) {
    }

    protected void afterPaint(Graphics2D g2d) {
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        calculateImgPos();

        Graphics2D g2d = (Graphics2D) graphics;
        beforePaint(g2d);

        setBackground(Color.WHITE);
        super.paintComponent(graphics);

        int x = 0;
        int y = 0;
        g2d.drawImage(this.image, this.transform, null);

        afterPaint(g2d);
    }

    public float getImgPosX1() {
        return (float) this.imgPos1.getX();
    }

    public float getImgPosX2() {
        return (float) this.imgPos2.getX();
    }

    public float getImgPosY1() {
        return (float) this.imgPos1.getY();
    }

    public float getImgPosY2() {
        return (float) this.imgPos2.getY();
    }
}
