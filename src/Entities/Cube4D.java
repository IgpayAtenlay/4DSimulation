package Entities;

import Data.Dimention;

public class Cube4D extends Mesh4D {
    public Cube4D(Dimention start, int sideLength) {
        super();
        Dimention one = start.move(sideLength, new Dimention(1, 0,0 , 0));
        Dimention two = start.move(sideLength, new Dimention(0, 1, 0, 0));
        Dimention three = start.move(sideLength, new Dimention(0, 0, 1, 0));

        addCube(start, one, two, three);
    }
}
