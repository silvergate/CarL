package ch.bedag.vrk.susa.carl.imageCrop;

import ch.bedag.vrk.susa.carl.utils.CursorUtil;
import ch.bedag.vrk.susa.carl.utils.VectorUtil;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * Buran.
 *
 * @author: ${USER} Date: 10.01.13 Time: 16:37
 */
public class Selector {
    private final ImageCrop component;
    private final ImageCropSettings settings;
    private final AffineTransform transform;

    private final Resizer r1;
    private final Resizer r2;

    private final Rectangle2D.Float shape = new Rectangle2D.Float();

    private final float aspect = (float) 16 / (float) 9;

    private boolean mouseIsInside;

    private final Stroke stroke = new BasicStroke(2.0f);

    /* Outsides */
    private final Rectangle2D.Float shapeW = new Rectangle2D.Float();
    private final Rectangle2D.Float shapeE = new Rectangle2D.Float();
    private final Rectangle2D.Float shapeN = new Rectangle2D.Float();
    private final Rectangle2D.Float shapeS = new Rectangle2D.Float();

    public Selector(ImageCrop component, ImageCropSettings settings, AffineTransform transform) {
        this.component = component;
        this.settings = settings;
        this.transform = transform;

        this.component.addMouseListener(this.mouseListener);
        this.component.addMouseMotionListener(this.mouseListener);
        this.component.addKeyListener(this.keyListener);

        this.r1 = new Resizer(transform, settings, component, this.requestMoveTo, false);
        this.r2 = new Resizer(transform, settings, component, this.requestMoveTo, true);

        this.r1.setPointInImage(50, 50);
        this.r2.setPointInImage(150, 150);
    }

    private final IResizerCallback requestMoveTo = new IResizerCallback() {
        @Override
        public void requestMove(Resizer resizer, float screenX, float screenY) {
            Point2D p1;
            Point2D p2;
            if (resizer == Selector.this.r1) {
                p2 = Selector.this.r2.getPointInScreen();
                p1 = new Point2D.Float(screenX, screenY);
            } else {
                p1 = Selector.this.r1.getPointInScreen();
                p2 = new Point2D.Float(screenX, screenY);
            }

            float len = (float) p1.distance(p2);

            Point2D vector = VectorUtil.normalize(Selector.this.aspect);
            VectorUtil.scale(vector, len);

            if (resizer == Selector.this.r1) {
                resizer.setPointInScreen((float) (p2.getX() - vector.getX()),
                        (float) (p2.getY() - vector.getY()));
            } else {
                resizer.setPointInScreen((float) (p1.getX() + vector.getX()),
                        (float) (p1.getY() + vector.getY()));
            }

            Selector.this.component.repaint();
        }
    };

    private MouseAdapter mouseListener = new MouseAdapter() {

        private boolean isDrag;
        private Point2D dragDelta = new Point2D.Float();

        @Override
        public void mousePressed(MouseEvent e) {
            final boolean inside = Selector.this.isInDragArea(e.getX(), e.getY());
            if (inside) {
                isDrag = true;
                this.dragDelta.setLocation(e.getX() - Selector.this.r1.getPointInScreen().getX(),
                        e.getY() - Selector.this.r1.getPointInScreen().getY());
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            this.isDrag = false;
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if (this.isDrag) {
                float newX = (float) (e.getX() - this.dragDelta.getX());
                float newY = (float) (e.getY() - this.dragDelta.getY());
                float to2X = (float) (Selector.this.r2.getPointInScreen().getX() -
                        Selector.this.r1.getPointInScreen().getX());
                float to2Y = (float) (Selector.this.r2.getPointInScreen().getY() -
                        Selector.this.r1.getPointInScreen().getY());
                Selector.this.r1.setPointInScreen(newX, newY);
                Selector.this.r2.setPointInScreen(newX + to2X, newY + to2Y);
                Selector.this.component.repaint();
            }
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            onMouseMoved(e);
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            Selector.this.mouseIsInside = true;
            Selector.this.component.repaint();
        }

        @Override
        public void mouseExited(MouseEvent e) {
            Selector.this.mouseIsInside = false;
            Selector.this.component.repaint();
        }
    };

    private boolean isInDragArea(float x, float y) {
        /* Is not in drag-area if on resizer */
        if (this.r1.isOnResizer(x, y)) return false;
        if (this.r2.isOnResizer(x, y)) return false;
        boolean xCorrect =
                (x > this.r1.getPointInScreen().getX()) && (x < this.r2.getPointInScreen().getX());
        boolean yCorrect =
                (y > this.r1.getPointInScreen().getY()) && (y < this.r2.getPointInScreen().getY());
        return xCorrect && yCorrect;
    }

    private void onMouseMoved(MouseEvent e) {
        if (this.r1.isOnResizer(e.getX(), e.getY())) {
            CursorUtil.setCursorTo(Cursor.NW_RESIZE_CURSOR, this.component);
            return;
        }
        if (this.r2.isOnResizer(e.getX(), e.getY())) {
            CursorUtil.setCursorTo(Cursor.SE_RESIZE_CURSOR, this.component);
            return;
        }
        if (isInDragArea(e.getX(), e.getY())) {
            CursorUtil.setCursorTo(Cursor.HAND_CURSOR, this.component);
            return;
        }
        CursorUtil.setCursorTo(Cursor.DEFAULT_CURSOR, this.component);
    }

    public void paint(Graphics2D g2d) {
        Point2D sp1 = this.r1.getPointInScreen();
        Point2D sp2 = this.r2.getPointInScreen();

        float x = (float) sp1.getX();
        float y = (float) sp1.getY();
        float w = (float) (sp2.getX() - sp1.getX());
        float h = (float) (sp2.getY() - sp1.getY());

        this.shapeE.setRect(this.component.getImgPosX1(), this.component.getImgPosY1(), x - 1,
                this.component.getImgPosY2() - this.component.getImgPosY1());
        this.shapeW.setRect(x + w, this.component.getImgPosY1(),
                this.component.getImgPosX2() - (x + w),
                this.component.getImgPosY2() - this.component.getImgPosY1());

        this.shapeN.setRect(x, this.component.getImgPosY1(), w, y - this.component.getImgPosY1());

        this.shapeS.setRect(x, y + h + 1, w, this.component.getImgPosY2() - (y + h + 1));

        g2d.setColor(new Color(1f, 1f, 1f, 0.4f));

        g2d.fill(this.shapeE);
        g2d.fill(this.shapeW);
        g2d.fill(this.shapeS);
        g2d.fill(this.shapeN);

        this.shape.setRect(x - 1, y - 1, w + 2, h + 2);
        g2d.setColor(new Color(0.3f, 1f, 0.3f, 0.8f));
        g2d.setStroke(this.stroke);
        g2d.draw(this.shape);

        r1.paint(g2d, this.mouseIsInside);
        r2.paint(g2d, this.mouseIsInside);
    }

    private final KeyListener keyListener = new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent e) {
            onKey(e);
        }
    };

    private void move(Point2D delta) {
        float newX = (float) (this.r1.getPointInScreen().getX() + delta.getX());
        float newY = (float) (this.r1.getPointInScreen().getY() + delta.getY());
        float to2X = (float) (this.r2.getPointInScreen().getX() -
                Selector.this.r1.getPointInScreen().getX());
        float to2Y = (float) (this.r2.getPointInScreen().getY() -
                Selector.this.r1.getPointInScreen().getY());
        Selector.this.r1.setPointInScreen(newX, newY);
        Selector.this.r2.setPointInScreen(newX + to2X, newY + to2Y);
        Selector.this.component.repaint();
    }

    private void onKey(KeyEvent e) {
        Point2D delta = new Point2D.Float();
        boolean move = false;
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                delta.setLocation(-1, 0);
                move = true;
                break;
            case KeyEvent.VK_RIGHT:
                delta.setLocation(1, 0);
                move = true;
                break;
            case KeyEvent.VK_UP:
                delta.setLocation(0, -1);
                move = true;
                break;
            case KeyEvent.VK_DOWN:
                delta.setLocation(0, 1);
                move = true;
                break;
        }
        if (move) move(delta);
    }
}
