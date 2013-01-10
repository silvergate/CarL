package ch.bedag.vrk.susa.carl.imageCrop;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * Created with IntelliJ IDEA. User: rcsc Date: 10.01.13 Time: 09:41 To change this template use
 * File | Settings | File Templates.
 */
public class Resizer {

    private final Rectangle2D.Float shape = new Rectangle2D.Float();
    private final Point2D pointInScreen = new Point2D.Float();

    private final AffineTransform transform;
    private final ImageCropSettings settings;

    private final Point2D pointInImage = new Point2D.Float();

    private final Component component;

    private final IResizerCallback callback;

    private final boolean second;

    public Resizer(AffineTransform transform, ImageCropSettings settings, Component component,
            IResizerCallback callback, boolean second) {
        this.transform = transform;
        this.settings = settings;
        this.component = component;
        this.callback = callback;
        this.second = second;

        setPointInImage(20, 20);

        this.component.addMouseListener(this.mouseListener);
        this.component.addMouseMotionListener(this.mouseListener);
    }

    public void setPointInScreen(float x, float y) {
        setPointInScreen(x, y, false);
    }

    private void setPointInScreen(float x, float y, boolean dontSetInImage) {
        this.pointInScreen.setLocation(x, y);
        if (this.second) {
            float w = this.settings.getResizerSize().width;
            float h = this.settings.getResizerSize().height;
            this.shape.setFrame(x, y, w, h);
        } else {
            float w = this.settings.getResizerSize().width;
            float h = this.settings.getResizerSize().height;
            this.shape.setFrame(x - w, y - h, w, h);
        }

        /* To point in image */
        if (!dontSetInImage) {
            try {
                this.transform.inverseTransform(this.pointInScreen, this.pointInImage);
            } catch (NoninvertibleTransformException e) {
                e.printStackTrace();
            }
        }
    }

    public Point2D getPointInImage() {
        return this.pointInImage;
    }

    public Point2D getPointInScreen() {
        return this.pointInScreen;
    }

    public void setPointInImage(int x, int y) {
        this.pointInImage.setLocation(x, y);
        fromPointInImageToPointInScreen();
    }

    private void fromPointInImageToPointInScreen() {
        Point2D pointInScreen = this.transform.transform(this.pointInImage, null);
        setPointInScreen((float) pointInScreen.getX(), (float) pointInScreen.getY(), true);
    }

    public void paint(Graphics2D g2d, boolean paint) {
        fromPointInImageToPointInScreen();
        if (paint) {
            g2d.setColor(new Color(0f, 0, 0, 0.5f));
            g2d.fill(this.shape);
        }
    }

    public boolean isOnResizer(float screenX, float screenY) {
        final boolean inside = Resizer.this.shape.contains(screenX, screenY);
        return inside;
    }

    private MouseAdapter mouseListener = new MouseAdapter() {

        private boolean isDrag;
        private Point2D dragDelta = new Point2D.Float();

        @Override
        public void mousePressed(MouseEvent e) {
            final boolean inside = Resizer.this.shape.contains(e.getX(), e.getY());
            if (inside) {
                isDrag = true;
                this.dragDelta.setLocation(e.getX() - Resizer.this.pointInScreen.getX(),
                        e.getY() - Resizer.this.pointInScreen.getY());
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            this.isDrag = false;
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if (this.isDrag) {
                Resizer.this.callback
                        .requestMove(Resizer.this, (float) (e.getX() - this.dragDelta.getX()),
                                (float) (e.getY() - this.dragDelta.getY()));
            }
        }
    };
}
