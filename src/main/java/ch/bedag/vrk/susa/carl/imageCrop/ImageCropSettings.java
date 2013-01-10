package ch.bedag.vrk.susa.carl.imageCrop;

import java.awt.*;

/**
 * Created with IntelliJ IDEA. User: rcsc Date: 10.01.13 Time: 09:38 To change this template use
 * File | Settings | File Templates.
 */
public class ImageCropSettings {
    private Dimension resizerSize = new Dimension(25, 25);

    public Dimension getResizerSize() {
        return resizerSize;
    }

    public void setResizerSize(Dimension resizerSize) {
        this.resizerSize = resizerSize;
    }
}
