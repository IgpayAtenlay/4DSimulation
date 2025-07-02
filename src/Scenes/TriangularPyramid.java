package Scenes;

import Data.Dimention;

public class TriangularPyramid extends Scene4D {
    public TriangularPyramid() {
        super();
        shapes.add(new Entities.TriangularPyramidMesh(new Dimention(-500, 500, 2000, 500),500));
    }
}
