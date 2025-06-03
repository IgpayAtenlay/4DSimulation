package Obsolete;

import Data.Dimention;
import Entities.Eye;
import Controls.Keys;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Panel extends JPanel {
    public static int CROSSHAIR_LENGTH = 12;
    public static int SPEED = 50;
    private final ArrayList<Shape> shapes;
    private final Eye eye;
    public Panel() {
        super();
        shapes = new ArrayList<>();
        this.eye = new Eye();
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for(Shape shape : shapes) {
            shape.draw(g, this);
        }
        g.drawLine(getWidth() / 2 - CROSSHAIR_LENGTH / 2, getHeight() / 2, getWidth() / 2 + CROSSHAIR_LENGTH / 2, getHeight() / 2);
        g.drawLine(getWidth() / 2, getHeight() / 2 - CROSSHAIR_LENGTH / 2, getWidth() / 2, getHeight() / 2 + CROSSHAIR_LENGTH / 2);
    }
    public void add(Shape shape) {
        shapes.add(shape);
    }
    public void tick() {
        if (Keys.isKeyPressed(KeyEvent.VK_W) && shapes.size() > 0) {
            shapes.get(0).move(SPEED, new Dimention(0, 1, 0, 0));
        }
        if (Keys.isKeyPressed(KeyEvent.VK_S) && shapes.size() > 0) {
            shapes.get(0).move(SPEED, new Dimention(0, -1, 0, 0));
        }
        if (Keys.isKeyPressed(KeyEvent.VK_A) && shapes.size() > 0) {
            shapes.get(0).move(SPEED, new Dimention(-1, 0, 0, 0));
        }
        if (Keys.isKeyPressed(KeyEvent.VK_D) && shapes.size() > 0) {
            shapes.get(0).move(SPEED, new Dimention(1, 0, 0, 0));
        }
        if (Keys.isKeyPressed(KeyEvent.VK_I) && shapes.size() > 0) {
            shapes.get(0).move(SPEED / 2, new Dimention(0, 0, 1, 0));
        }
        if (Keys.isKeyPressed(KeyEvent.VK_K) && shapes.size() > 0) {
            shapes.get(0).move(SPEED / 2, new Dimention(0, 0, -1, 0));
        }
        if (Keys.isKeyPressed(KeyEvent.VK_J) && shapes.size() > 0) {
            shapes.get(0).move(SPEED / 2, new Dimention(0, 0, 0, -1));
        }
        if (Keys.isKeyPressed(KeyEvent.VK_L) && shapes.size() > 0) {
            shapes.get(0).move(SPEED / 2, new Dimention(0, 0, 0, 1));
        }

        repaint();
    }
    public Dimention modifyCoordinates(Dimention dimention) {
        Dimention result = eye.modifyCoordinates(dimention);
        return new Dimention(result.x() + (double) getWidth() / 2, result.y() * -1 + (double) getHeight() / 2, result.z(), result.w());
    }
    public Eye getEye() {
        return eye;
    }
}
