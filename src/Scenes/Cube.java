package Scenes;

import Data.Dimention;

public class Cube extends Scene {
    public Cube() {
        super();
        shapes.add(new Entities.Cube(new Dimention(-500, 500, 2000, 500),500));
    }
}
