package Obsolete;

import Data.Dimention;
import Controls.Settings;

import java.awt.*;

public class Triangle extends Shape {
    public Dimention cornerOne;
    public Dimention cornerTwo;
    public Dimention cornerThree;

    public Triangle(Dimention cornerOne, Dimention cornerTwo, Dimention cornerThree) {
        this.cornerOne = cornerOne;
        this.cornerTwo = cornerTwo;
        this.cornerThree = cornerThree;
    }
    public void draw(Graphics g, Panel panel) {
        Color oldColor = g.getColor();
        Dimention modifiedOne = panel.modifyCoordinates(cornerOne);
        Dimention modifiedTwo = panel.modifyCoordinates(cornerTwo);
        Dimention modifiedThree = panel.modifyCoordinates(cornerThree);

        if (modifiedOne.isVisible() && modifiedTwo.isVisible() && modifiedThree.isVisible()) {
            Graphics2D g2 = (Graphics2D) g;
            g.setColor(getColor(modifiedOne, modifiedTwo, modifiedThree));
            int[] xPoints = {(int) modifiedOne.x(), (int) modifiedTwo.x(), (int) modifiedThree.x()};
            int[] yPoints = {(int) modifiedOne.y(), (int) modifiedTwo.y(), (int) modifiedThree.y()};
            g2.fillPolygon(xPoints, yPoints, xPoints.length);
        }
        g.setColor(oldColor);
    }
    public Color getColor(Dimention... dimentions) {
        double smallestW = Double.MAX_VALUE;
        boolean pos = false;

        for (Dimention dimention : dimentions) {
            if (Math.abs(dimention.w()) < smallestW) {
                smallestW = Math.abs(dimention.w());
                if (dimention.w() > 0) {
                    pos = true;
                }
            }
        }

        int blurValue = 0;
        if (smallestW <= Settings.getBlurRange() / 2) {
            blurValue = (int) ((1 - smallestW / (Settings.getBlurRange() / 2)) * 255);
        }

        if (smallestW <= Settings.getSolidRange() / 2) {
            return new Color(0, 0, 0, blurValue);
        } else if (smallestW <= Settings.getSolidRange() / 2 + Settings.getGradientRange()) {
            int value = (int) ((smallestW - Settings.getSolidRange() / 2) / Settings.getGradientRange() * 255);
            if (pos) {
                return new Color(value, 0, 0, blurValue);
            } else {
                return new Color(0, 0, value, blurValue);
            }
        } else {
            if (pos) {
                return new Color(255, 0, 0, blurValue);
            } else {
                return new Color(0, 0, 255, blurValue);
            }
        }
    }

    public void move(int distance, Dimention direction) {
        cornerOne = cornerOne.move(distance, direction);
        cornerTwo = cornerTwo.move(distance, direction);
        cornerThree = cornerThree.move(distance, direction);
    }
}
