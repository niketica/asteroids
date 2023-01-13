package nl.aniketic.asteroids.input;

import java.awt.event.KeyEvent;

public enum GameKey {

    UP(KeyEvent.VK_W),
    DOWN(KeyEvent.VK_S),
    LEFT(KeyEvent.VK_A),
    RIGHT(KeyEvent.VK_D),
    SPACE(KeyEvent.VK_SPACE);

    private final int keyCode;
    private boolean pressed;

    GameKey(int keyCode) {
        this.keyCode = keyCode;
    }

   public static GameKey getKey(int keyCode) {
        for (GameKey key : GameKey.values()) {
            if (key.keyCode == keyCode) {
                return key;
            }
        }
        return null;
   }

    public boolean isPressed() {
        return pressed;
    }

    public void setPressed(boolean pressed) {
        this.pressed = pressed;
    }
}
