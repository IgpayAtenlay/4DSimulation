package Scenes;

import Entities.Eye;
import Entities.Mesh4D;

import java.util.ArrayList;

public abstract class Scene4D {
    protected final ArrayList<Mesh4D> shapes;
    private final Eye eye;
    public Scene4D() {
        shapes = new ArrayList<>();
        eye = new Eye();
    }
    public Eye getEye() {
        return eye;
    }

    public ArrayList<Mesh4D> getShapes() {
        return shapes;
    }
}
