package Entities;

import Data.Dimention;
import Data.RotationDirection;

public class TriangularPyramid extends Entity {
    public Dimention cornerOne;
    public Dimention cornerTwo;
    public Dimention cornerThree;
    public Dimention cornerFour;
    public Triangle[] triangles;

    public TriangularPyramid(Dimention cornerOne, Dimention cornerTwo, Dimention cornerThree, Dimention cornerFour) {
        this.cornerOne = cornerOne;
        this.cornerTwo = cornerTwo;
        this.cornerThree = cornerThree;
        this.cornerFour = cornerFour;
        this.triangles = new Triangle[4];
        updateTriangles();
    }

    public void move(int distance, Dimention direction) {
        cornerOne = cornerOne.move(distance, direction);
        cornerTwo = cornerTwo.move(distance, direction);
        cornerThree = cornerThree.move(distance, direction);
        cornerFour = cornerFour.move(distance, direction);
    }
    @Override
    public void turn(double degree, RotationDirection direction) {
    }
    public Triangle[] getTriangles() {
        return triangles;
    }
    public void updateTriangles() {
        triangles[0] = new Triangle(cornerOne, cornerTwo, cornerThree);
        triangles[1] = new Triangle(cornerOne, cornerTwo, cornerFour);
        triangles[2] = new Triangle(cornerOne, cornerThree, cornerFour);
        triangles[3] = new Triangle(cornerTwo, cornerThree, cornerFour);
    }
}
