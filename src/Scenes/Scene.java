package Scenes;

import Entities.Mesh;
import Entities.Eye;

import java.util.ArrayList;

public abstract class Scene {
    protected final ArrayList<Mesh> shapes;
    private final Eye eye;
    public Scene() {
        shapes = new ArrayList<>();
        eye = new Eye();
    }
    public Eye getEye() {
        return eye;
    }

    public ArrayList<Mesh> getShapes() {
        return shapes;
    }
}
