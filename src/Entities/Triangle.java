package Entities;

import Data.Dimention;
import Data.RotationDirection;

public class Triangle extends Entity {
    public Dimention cornerOne;
    public Dimention cornerTwo;
    public Dimention cornerThree;

    public Triangle(Dimention cornerOne, Dimention cornerTwo, Dimention cornerThree) {
        this.cornerOne = cornerOne;
        this.cornerTwo = cornerTwo;
        this.cornerThree = cornerThree;
    }

    public void move(int distance, Dimention direction) {
        cornerOne = cornerOne.move(distance, direction);
        cornerTwo = cornerTwo.move(distance, direction);
        cornerThree = cornerThree.move(distance, direction);
    }
    @Override
    public void turn(double degree, RotationDirection direction) {
    }
}
