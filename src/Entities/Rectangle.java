package Entities;

import Data.Dimention;

public class Rectangle extends Mesh {
    public Rectangle(Dimention cornerOne, Dimention cornerTwo, Dimention cornerThree) {
        super();
        mesh.add(new Triangle(cornerOne, cornerTwo, cornerThree));
        Dimention cornerFour = cornerOne.move((int) cornerTwo.distance(cornerThree), cornerTwo.direction(cornerThree));
        mesh.add(new Triangle(cornerOne, cornerFour, cornerThree));
    }
}
