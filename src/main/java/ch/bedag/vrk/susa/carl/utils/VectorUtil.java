package ch.bedag.vrk.susa.carl.utils;

import java.awt.geom.Point2D;

/**
 * Buran.
 *
 * @author: ${USER} Date: 10.01.13 Time: 18:22
 */
public class VectorUtil {
    public static float getLength(float x1, float y1, float x2, float y2) {
        float x = x2 - x1;
        float y = y2 - y1;
        return (float) Math.sqrt((float) (Math.pow(x, 2) + Math.pow(y, 2)));
    }

    public static float getLength(Point2D p1, Point2D p2) {
        return getLength((float) p1.getX(), (float) p1.getY(), (float) p2.getX(),
                (float) p2.getY());
    }

    public static Point2D normalize(float direction) {
        float len = getLength(0, 0, direction, 1);
        Point2D vec = new Point2D.Float();
        vec.setLocation(direction / len, 1f / len);
        return vec;
    }

    public static void scale(Point2D vect, float scale) {
        vect.setLocation(vect.getX() * scale, vect.getY() * scale);
    }
}
