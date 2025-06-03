package Shapes;

import Data.Dimention;

public class CompositeRectangle extends CompositeShape {
    public CompositeRectangle(Dimention cornerOne, Dimention cornerTwo, Dimention cornerThree) {
        super();
        mesh.add(new Triangle(cornerOne, cornerTwo, cornerThree));
        Dimention cornerFour = cornerOne.move((int) cornerTwo.distance(cornerThree), cornerTwo.direction(cornerThree));
        mesh.add(new Triangle(cornerOne, cornerFour, cornerThree));
    }
}
