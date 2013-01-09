package ch.bedag.vrk.susa.carl;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

/** Basic CursorToolkit that swallows mouseclicks */
public class CursorToolkitTwo implements Cursors {
    private final static MouseAdapter mouseAdapter =
            new MouseAdapter() {};

    private CursorToolkitTwo() {}

    /** Sets cursor for specified component to Wait cursor */
    public static void startWaitCursor(JComponent component) {
        component.setCursor(MOVE_CURSOR);

        /*RootPaneContainer root =
                ((RootPaneContainer) component.getTopLevelAncestor());
        root.getGlassPane().setCursor(WAIT_CURSOR);
        root.getGlassPane().addMouseListener(mouseAdapter);
        root.getGlassPane().setVisible(true); */
    }

    /** Sets cursor for specified component to normal cursor */
    public static void stopWaitCursor(JComponent component) {
        RootPaneContainer root =
                ((RootPaneContainer) component.getTopLevelAncestor());
        root.getGlassPane().setCursor(DEFAULT_CURSOR);
        root.getGlassPane().removeMouseListener(mouseAdapter);
        root.getGlassPane().setVisible(false);
    }

    public static void main(String[] args) {
        BufferedImage img = ImgUtils.getImageFromClasspath
                ("ch/bedag/vrk/susa/carl/china-nail-house.jpg");
        final JFrame frame = new JFrame("Test App");
        final JLabel label =   new JLabel("I'm a Frame");
        frame.getContentPane().add(
                label, BorderLayout.NORTH);
        final ImageCrop ic = new ImageCrop(img, frame);
        frame.getContentPane().add(ic, BorderLayout.CENTER);
        frame.getContentPane().add(
                new JButton(new AbstractAction("Wait Cursor") {
                    public void actionPerformed(ActionEvent event) {
                        System.out.println("Setting Wait cursor on frame");
                        startWaitCursor(ic);
                    }
                }), BorderLayout.SOUTH);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.show();
    }
}
