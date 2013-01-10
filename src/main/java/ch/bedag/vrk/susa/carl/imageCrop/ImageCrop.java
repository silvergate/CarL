package ch.bedag.vrk.susa.carl.imageCrop;

import ch.bedag.vrk.susa.carl.ImageCanvas;
import ch.bedag.vrk.susa.carl.TransformUtil;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 * Buran.
 *
 * @author: ${USER} Date: 09.01.13 Time: 21:32
 */
public class ImageCrop extends ImageCanvas {

    Rectangle2D.Float borderShape = new Rectangle2D.Float();

    private boolean mouseIn;

    private final Selector selector;

    private final ImageCropSettings settings;

    /* Selection in picture */
    private final Point2D selectionInImage1 = new Point2D.Float();
    private final Point2D selectionInImage2 = new Point2D.Float();

    /* Selection on Window */
    private final Point2D selectionOnScreen1 = new Point2D.Float();
    private final Point2D selectionOnScreen2 = new Point2D.Float();

    public ImageCrop(BufferedImage image, ImageCropSettings settings) {
        super(image);
        this.settings = settings;
        this.selector = new Selector(this, settings, this.transform);

        selectionInImage1.setLocation(20, 20);
        selectionInImage2.setLocation(getImageWidth() - 50, getImageHeight() - 50);

        this.borderShape.setRect(20, 20, 200, 200);
        this.addMouseMotionListener(this.mouseMotionListener);
        this.addMouseListener(this.mouseListener);
        this.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                System.out.println("Gained Focus");
            }

            @Override
            public void focusLost(FocusEvent e) {
                System.out.println("Lost Focus");
            }
        });
    }

    @Override
    protected void beforePaint(Graphics2D g2d) {
        TransformUtil.scaledWithInsetsKeepAspect(1, 1, 1, 1, getImageWidth(), getImageHeight(),
                getWidth(), getHeight(), this.transform);
    }

    @Override
    protected void afterPaint(Graphics2D g2d) {
        this.transform.transform(this.selectionInImage1, this.selectionOnScreen1);
        this.transform.transform(this.selectionInImage2, this.selectionOnScreen2);

        this.borderShape
                .setFrame(this.selectionOnScreen1.getX(), this.selectionOnScreen1.getY(), 20, 20);
        //System.out.println(this.selectionOnScreen1.getX());

        this.selector.paint(g2d);

        //if (this.mouseIn) {
        //g2d.setColor(new Color(0.5f, 0, 0, 0.5f));
        //g2d.fill(this.borderShape);
        //}
    }

    private final MouseMotionListener mouseMotionListener = new MouseMotionAdapter() {
        @Override
        public void mouseMoved(MouseEvent e) {
           /* if ((e.getX()>50) && (e.getX()<250) && (e.getY()>50) && (e.getY()<250)) {
                if (ImageCrop.this.getCursor().getType()!=Cursor.CROSSHAIR_CURSOR) {
                setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
                }
            } else {
                if (ImageCrop.this.getCursor().getType()!=Cursor.DEFAULT_CURSOR) {
                    setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                }
            }*/
        }

    };

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

        @Override
        public void mouseClicked(MouseEvent e) {
            ImageCrop.this.requestFocusInWindow();
        }
    };

    @Override
    public boolean contains(Point p) {
        return true;
    }
}
