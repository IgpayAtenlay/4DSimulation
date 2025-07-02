package Scenes;

import Data.Dimention;

public class Cube4D extends Scene4D {
    public Cube4D() {
        super();
        shapes.add(new Entities.Cube4D(new Dimention(-500, 500, 2000, 500),500));
    }
}
