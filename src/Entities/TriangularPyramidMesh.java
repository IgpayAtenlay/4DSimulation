package Entities;

import Data.Dimention;

public class TriangularPyramidMesh extends Mesh4D {
    public TriangularPyramidMesh(Dimention start, int sideLength) {
        super();
        Dimention one = start.move(sideLength, new Dimention(1, 0,0 , 0));
        Dimention two = start.move(sideLength, new Dimention(0, 1, 0, 0));
        Dimention three = start.move(sideLength * 100, new Dimention(0, 0, 1, 0));

        mesh.add(new TriangularPyramid(start, one, two, three));
    }
}
