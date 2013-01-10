package ch.bedag.vrk.susa.carl;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

/**
 * Buran.
 *
 * @author: ${USER} Date: 09.01.13 Time: 20:12
 */
public class ImgUtils {
    public static BufferedImage getImageFromClasspath(String path) {
        BufferedImage img = null;
        URL imgUrl = ImgUtils.class.getClassLoader().getResource(path);
        if (imgUrl == null) {
            System.err.println("Couldn't find file: " + path);
        } else {
            try {
                img = ImageIO.read(imgUrl);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return img;
    }
}
