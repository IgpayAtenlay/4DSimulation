package Shapes;

import Data.Dimention;

import java.util.ArrayList;

public abstract class CompositeShape {
    public final ArrayList<Triangle> mesh;
    public CompositeShape() {
        this.mesh = new ArrayList<>();
    }

    public void move(int distance, Dimention direction) {
        mesh.forEach(e -> e.move(distance, direction));
    }
}
