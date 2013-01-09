package ch.bedag.vrk.susa.carl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 * Buran.
 *
 * @author: ${USER} Date: 09.01.13 Time: 21:32
 */
public class ImageCrop extends ImageCanvas {

    Rectangle2D.Float borderShape = new Rectangle2D.Float();
    private final JFrame frame;

    private boolean mouseIn;

    public ImageCrop(BufferedImage image, final JFrame frame) {
        super(image);
        this.borderShape.setRect(50, 50, 200, 200);
        this.frame = frame;
        this.addMouseMotionListener(this.mouseMotionListener);
        this.addMouseListener(this.mouseListener);

    }

    @Override
    protected void beforePaint(Graphics2D g2d) {
        TransformUtil.scaledWithInsetsKeepAspect(1, 1, 1, 1, getImageWidth(), getImageHeight(),
                getWidth(), getHeight(), this.transform);
    }

    @Override
    protected void afterPaint(Graphics2D g2d) {
        if (this.mouseIn) {
        g2d.setColor(new Color(0.5f, 0, 0, 0.5f));
        g2d.fill(this.borderShape);
        }
    }

    private final MouseMotionListener mouseMotionListener = new MouseMotionAdapter() {
        @Override
        public void mouseMoved(MouseEvent e) {
            if ((e.getX()>50) && (e.getX()<250) && (e.getY()>50) && (e.getY()<250)) {
                if (ImageCrop.this.getCursor().getType()!=Cursor.CROSSHAIR_CURSOR) {
                setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
                    System.out.println("Setting new cursor: Enabled: " + isEnabled() + ", " +
                            "showing: " + isShowing());

                }
            } else {
                if (ImageCrop.this.getCursor().getType()!=Cursor.DEFAULT_CURSOR) {
                    setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                    System.out.println("Back to default");
                }
            }
        }

    } ;

    private final MouseListener mouseListener = new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent e) {
            ImageCrop.this.mouseIn = true;
            ImageCrop.this.repaint();
        }

        @Override
        public void mouseExited(MouseEvent e) {
            ImageCrop.this.mouseIn = false;
            ImageCrop.this.repaint();
        }
    };

    @Override
    public boolean contains(Point p) {
        return true;
    }
}
