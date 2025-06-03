package Obsolete;

import Data.Dimention;

import java.awt.*;

public class Point extends Shape {
    public static int POINT_SIZE = 10;
    public Dimention location;
    public Point(Dimention location) {
        this.location = location;
    }
    public Point(int x, int y, int z, int w) {
        this(new Dimention(x, y, z, w));
    }

    public void draw(Graphics g, Panel panel) {
        Dimention modifiedCoordinates = panel.modifyCoordinates(location);
        if (modifiedCoordinates.isVisible()) {
            g.fillOval((int) (modifiedCoordinates.x() - POINT_SIZE / 2), (int) (modifiedCoordinates.y() - POINT_SIZE / 2), POINT_SIZE, POINT_SIZE);
        }
    }
    public void move(int distance, Dimention direction) {
        this.location = location.move(distance, direction);
    }
}
