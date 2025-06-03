package Obsolete;

import Data.Dimention;

import java.awt.*;

public class Parallelogram extends Shape {
    private Dimention cornerOne;
    private Dimention cornerTwo;
    private Dimention cornerThree;

    public Parallelogram(Dimention cornerOne, Dimention cornerTwo, Dimention cornerThree) {
        this.cornerOne = cornerOne;
        this.cornerTwo = cornerTwo;
        this.cornerThree = cornerThree;
    }
    public void draw(Graphics g, Panel panel) {
        Dimention cornerFour = new Dimention(
                cornerOne.x() - cornerTwo.x() + cornerThree.x(),
                cornerOne.y() - cornerTwo.y() + cornerThree.y(),
                cornerOne.z() - cornerTwo.z() + cornerThree.z(),
                cornerOne.w() - cornerTwo.w() + cornerThree.w()
        );
        Dimention modifiedOne = panel.modifyCoordinates(cornerOne);
        Dimention modifiedTwo = panel.modifyCoordinates(cornerTwo);
        Dimention modifiedThree = panel.modifyCoordinates(cornerThree);
        Dimention modifiedFour = panel.modifyCoordinates(cornerFour);

        if (modifiedOne.isVisible() && modifiedTwo.isVisible() && modifiedThree.isVisible() && modifiedFour.isVisible()) {
            Graphics2D g2 = (Graphics2D) g;
            int[] xPoints = {(int) modifiedOne.x(), (int) modifiedTwo.x(), (int) modifiedThree.x(), (int) modifiedFour.x()};
            int[] yPoints = {(int) modifiedOne.y(), (int) modifiedTwo.y(), (int) modifiedThree.y(), (int) modifiedFour.y()};
            g2.drawPolygon(xPoints, yPoints, xPoints.length);
        }
    }

    public void move(int distance, Dimention direction) {
        cornerOne = cornerOne.move(distance, direction);
        cornerTwo = cornerTwo.move(distance, direction);
        cornerThree = cornerThree.move(distance, direction);
    }
}
