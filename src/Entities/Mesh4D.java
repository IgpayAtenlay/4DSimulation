package Entities;

import Data.Dimention;

import java.util.ArrayList;

public abstract class Mesh4D extends Entity {
    public final ArrayList<TriangularPyramid> mesh;
    public Mesh4D() {
        this.mesh = new ArrayList<>();
    }
    public void move(int distance, Dimention direction) {
        mesh.forEach(e -> e.move(distance, direction));
    }
    public void turn(double degree, Dimention direction) {
        // not implimented
    }
    public void addCube(Dimention middle, Dimention one, Dimention two, Dimention three) {
        Dimention oneTwo = one.move(middle, two);
        Dimention oneThree = one.move(middle, three);
        Dimention twoThree = two.move(middle, three);
        Dimention opposite = oneTwo.move(middle, three);
        mesh.add(new TriangularPyramid(middle, oneTwo, oneThree, twoThree));
        mesh.add(new TriangularPyramid(middle, one, oneTwo, oneThree));
        mesh.add(new TriangularPyramid(middle, two, oneTwo, twoThree));
        mesh.add(new TriangularPyramid(middle, three, oneThree, twoThree));
        mesh.add(new TriangularPyramid(opposite, oneTwo, oneThree, twoThree));
    }
}
