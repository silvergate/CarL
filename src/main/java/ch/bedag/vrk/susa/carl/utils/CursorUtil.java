package ch.bedag.vrk.susa.carl.utils;

import java.awt.*;

/**
 * Buran.
 *
 * @author: ${USER} Date: 10.01.13 Time: 19:08
 */
public class CursorUtil {
    public static void setCursorTo(int definedCursor, Component component) {
        if (component.getCursor().getType() != definedCursor) {
            component.setCursor(Cursor.getPredefinedCursor(definedCursor));
        }
    }
}
