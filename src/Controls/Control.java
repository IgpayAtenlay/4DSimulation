package Controls;

import Data.Dimention;
import Entities.Entity;
import Entities.Mesh4D;
import Scenes.Scene4D;
import Visuals.Frame;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class Control {
    private static Scene4D scene;
    private static Entity focus;
    private static Frame frame;

    public static void initialize(Scene4D scene) {
        Control.scene = scene;
        focus = scene.getEye();
        frame = new Frame();
        Timer timer = new Timer(1, e -> {
            tick();
            frame.panel.tick();
        });
        timer.start();
    }

    public static void tick() {
        if ( focus != null) {
            if (Keys.isKeyPressed(KeyEvent.VK_W)) {
                focus.move(Settings.getHorizontalSpeed(), new Dimention(0, 0, 1, 0));
            }
            if (Keys.isKeyPressed(KeyEvent.VK_S)) {
                focus.move(Settings.getHorizontalSpeed(), new Dimention(0, 0, -1, 0));
            }
            if (Keys.isKeyPressed(KeyEvent.VK_A)) {
                focus.move(Settings.getHorizontalSpeed(), new Dimention(-1, 0, 0, 0));
            }
            if (Keys.isKeyPressed(KeyEvent.VK_D)) {
                focus.move(Settings.getHorizontalSpeed(), new Dimention(1, 0, 0, 0));
            }
            if (Keys.isKeyPressed(KeyEvent.VK_I)) {
                focus.move(Settings.getForwardsSpeed(), new Dimention(0, 1, 0, 0));
            }
            if (Keys.isKeyPressed(KeyEvent.VK_K)) {
                focus.move(Settings.getForwardsSpeed(), new Dimention(0, -1, 0, 0));
            }
            if (Keys.isKeyPressed(KeyEvent.VK_J)) {
                focus.move(Settings.getAnaSpeed(), new Dimention(0, 0, 0, -1));
            }
            if (Keys.isKeyPressed(KeyEvent.VK_L)) {
                focus.move(Settings.getAnaSpeed(), new Dimention(0, 0, 0, 1));
            }
            if (Keys.isKeyPressed(KeyEvent.VK_NUMPAD4)) {
                // turn left
                focus.turn(Settings.getxTurn(), new Dimention(-1, 0, 0, 0));
            }
            if (Keys.isKeyPressed(KeyEvent.VK_NUMPAD6)) {
                focus.turn(Settings.getxTurn(), new Dimention(1, 0, 0, 0));
            }
            if (Keys.isKeyPressed(KeyEvent.VK_NUMPAD8)) {
                focus.turn(Settings.getyTurn(), new Dimention(0, 1, 0, 0));
            }
            if (Keys.isKeyPressed(KeyEvent.VK_NUMPAD2)) {
                focus.turn(Settings.getyTurn(), new Dimention(0, -1, 0, 0));
            }
        }
    }

    public static Scene4D getScene() {
        return scene;
    }
    public void add(Mesh4D shape) {
        scene.getShapes().add(shape);
    }
}
