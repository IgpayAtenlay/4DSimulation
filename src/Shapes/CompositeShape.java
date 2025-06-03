package Shapes;

import Data.Dimention;
import Visuals.Panel;

import java.awt.*;
import java.util.ArrayList;

public abstract class CompositeShape extends Shape {
    public final ArrayList<Triangle> mesh;
    public CompositeShape() {
        this.mesh = new ArrayList<>();
    }

    public void draw(Graphics g, Panel panel) {
        mesh.forEach(e -> e.draw(g, panel));
    }
    public void move(int distance, Dimention direction) {
        mesh.forEach(e -> e.move(distance, direction));
    }
}
