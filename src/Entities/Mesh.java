package Entities;

import Data.Dimention;

import java.util.ArrayList;

public abstract class Mesh extends Entity {
    public final ArrayList<Triangle> mesh;
    public Mesh() {
        this.mesh = new ArrayList<>();
    }
    public void move(int distance, Dimention direction) {
        mesh.forEach(e -> e.move(distance, direction));
    }
    public void turn(double degree, Dimention direction) {
        // not implimented
    }
    public void addRectangle(Dimention middle, Dimention one, Dimention two) {
        Dimention opposite = one.move(middle, two);
        mesh.add(new Triangle(one, middle, two));
        mesh.add(new Triangle(one, opposite, two));
    }
}
