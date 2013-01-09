package ch.bedag.vrk.susa.carl;

import java.awt.geom.AffineTransform;

/**
 * Buran.
 *
 * @author: ${USER} Date: 09.01.13 Time: 20:46
 */
public class TransformUtil {
    public static void scaledWithInsets(int ix, int iy, int ix2, int iy2, int imageWidth,
            int imageHeight, int width, int height,
            AffineTransform transform) {
        float tx = (float)(width - ix - ix2) / (float)imageWidth;
        float ty = (float)(height -iy - iy2) / (float)imageHeight;
        transform.setTransform(tx, 0f, 0f, ty, ix,iy);
    }

    public static void scaledWithInsetsKeepAspect(int ix, int iy, int ix2, int iy2, int imageWidth,
            int imageHeight, int width, int height,
            AffineTransform transform) {
        float widthSpace =  (width - ix - ix2);
        float heightSpace = (height -iy - iy2);

        float tx = widthSpace / (float)imageWidth;
        float ty = heightSpace / (float)imageHeight;
        final float t = Math.min(tx, ty);
        transform.setTransform(t, 0f, 0f, t, ix,iy);
    }
}
