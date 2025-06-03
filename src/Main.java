import Data.Dimention;
import Shapes.CompositeRectangle;
import Visuals.Frame;

public class Main {
    public static void main(String[] args) {
        Frame frame = new Frame();
//        frame.panel.add(new Point(0, 0, 100, 0));
//        frame.panel.add(new Triangle(
//                new Dimention(0, 0, 100, 0),
//                new Dimention(100, 0, 100, 0),
//                new Dimention(100, 0, 0, 0)
//        ));
//        GenericGraph genericGraph = new GenericGraph();
//        genericGraph.edges.addEdge(new Dimention(0, 0, 100, 0), new Dimention(100, 0, 100, 0));
//        genericGraph.edges.addEdge(new Dimention(100, 0, 100, 0), new Dimention(100, 100, 100, 0));
//        genericGraph.edges.addEdge(new Dimention(100, 100, 100, 0), new Dimention(0, 0, 100, 0));
//        frame.panel.add(genericGraph);
        frame.panel.add(new CompositeRectangle(
                new Dimention(0, 0, 100, 0),
                new Dimention(500, 0, 100, 0),
                new Dimention(500, 500, 100, 100))
        );
    }
}
