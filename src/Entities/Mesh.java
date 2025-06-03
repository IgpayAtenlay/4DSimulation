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
}
