package Entities;

import Data.Dimention;
import Data.RotationDirection;

import java.util.ArrayList;

public abstract class Mesh extends Entity {
    public final ArrayList<TriangularPyramid> mesh;
    public Mesh() {
        this.mesh = new ArrayList<>();
    }
    public void move(int distance, Dimention direction) {
        mesh.forEach(e -> e.move(distance, direction));
    }
    public void turn(double degree, RotationDirection direction) {
        // not implimented
    }
    public void addRectangularPrism(Dimention middle, Dimention one, Dimention two, Dimention three) {
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
