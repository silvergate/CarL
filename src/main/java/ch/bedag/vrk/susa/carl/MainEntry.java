package ch.bedag.vrk.susa.carl;

import ch.bedag.vrk.susa.carl.imageCrop.ImageCrop;
import ch.bedag.vrk.susa.carl.imageCrop.ImageCropSettings;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Buran.
 *
 * @author: ${USER} Date: 09.01.13 Time: 19:54
 */
public class MainEntry {
    /**
     * The Entry main method
     */
    public static void main(String[] args) {
        // Run the GUI codes on the Event-Dispatching thread for thread safety
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                BufferedImage img = ImgUtils.getImageFromClasspath(
                        "ch/bedag/vrk/susa/carl/china-nail-house.jpg");

                JFrame frame = new JFrame("Ein titel");
                frame.getContentPane().setLayout(new BorderLayout());
                ImageCropSettings icSettings = new ImageCropSettings();
                frame.getContentPane().add(new ImageCrop(img, icSettings), BorderLayout.CENTER);
                frame.getContentPane().add(new JButton("asdas"), BorderLayout.SOUTH);

                frame.pack();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLocationRelativeTo(null); // center the application window
                frame.getGlassPane().setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
                frame.setVisible(true);
            }
        });
    }
}
