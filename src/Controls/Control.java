package Controls;

import Data.Dimention;
import Data.FacingDirection;
import Data.RotationDirection;
import Entities.Entity;
import Entities.Eye;
import Entities.Mesh;
import Scenes.Scene;
import Visuals.Frame;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class Control {
    private static Scene scene;
    private static Entity focus;
    private static Frame frame;

    public static void initialize(Scene scene) {
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
        if (focus instanceof Eye) {
            if (Keys.isKeyPressed(KeyEvent.VK_W)) {
                ((Eye) focus).move(Settings.getHorizontalSpeed(), FacingDirection.FORWARD_BACK);
            }
            if (Keys.isKeyPressed(KeyEvent.VK_S)) {
                ((Eye) focus).move(Settings.getHorizontalSpeed() * -1, FacingDirection.FORWARD_BACK);
            }
            if (Keys.isKeyPressed(KeyEvent.VK_A)) {
                ((Eye) focus).move(Settings.getHorizontalSpeed() * -1, FacingDirection.LEFT_RIGHT);
            }
            if (Keys.isKeyPressed(KeyEvent.VK_D)) {
                ((Eye) focus).move(Settings.getHorizontalSpeed(), FacingDirection.LEFT_RIGHT);
            }
            if (Keys.isKeyPressed(KeyEvent.VK_I)) {
                ((Eye) focus).move(Settings.getForwardsSpeed(), new Dimention(0, 1, 0, 0));
            }
            if (Keys.isKeyPressed(KeyEvent.VK_K)) {
                ((Eye) focus).move(Settings.getForwardsSpeed(), new Dimention(0, -1, 0, 0));
            }
            if (Keys.isKeyPressed(KeyEvent.VK_J)) {
                ((Eye) focus).move(Settings.getAnaSpeed() * -1, FacingDirection.ANA_KATA);
            }
            if (Keys.isKeyPressed(KeyEvent.VK_L)) {
                ((Eye) focus).move(Settings.getAnaSpeed(), FacingDirection.ANA_KATA);
            }
            if (Keys.isKeyPressed(KeyEvent.VK_NUMPAD4)) {
                focus.turn(Settings.getXZTurn(), RotationDirection.XZ);
            }
            if (Keys.isKeyPressed(KeyEvent.VK_NUMPAD6)) {
                focus.turn(Settings.getXZTurn() * -1, RotationDirection.XZ);
            }
            if (Keys.isKeyPressed(KeyEvent.VK_NUMPAD8)) {
                focus.turn(Settings.getYZTurn(), RotationDirection.YZ);
            }
            if (Keys.isKeyPressed(KeyEvent.VK_NUMPAD2)) {
                focus.turn(Settings.getYZTurn() * -1, RotationDirection.YZ);
            }
            if (Keys.isKeyPressed(KeyEvent.VK_NUMPAD7)) {
                focus.turn(Settings.getWZTurn(), RotationDirection.WZ);
            }
            if (Keys.isKeyPressed(KeyEvent.VK_NUMPAD9)) {
                focus.turn(Settings.getWZTurn() * -1, RotationDirection.WZ);
            }
        }
    }

    public static Scene getScene() {
        return scene;
    }
    public void add(Mesh shape) {
        scene.getShapes().add(shape);
    }
}
