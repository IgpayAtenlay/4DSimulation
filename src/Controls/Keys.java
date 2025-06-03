package Controls;

import java.awt.event.KeyEvent;
import java.util.HashMap;

public class Keys {
    private static final HashMap<Integer, Boolean> keyPressed = new HashMap<>();

    public static void keyPressed(KeyEvent e) {
        keyPressed.put(e.getKeyCode(), true);
    }
    public static boolean isKeyPressed(int key) {
        return keyPressed.getOrDefault(key, false);
    }
    public static void keyReleased(KeyEvent e) {
        keyPressed.put(e.getKeyCode(), false);
    }
}
