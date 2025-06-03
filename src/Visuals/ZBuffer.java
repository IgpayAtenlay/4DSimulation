package Visuals;

import Controls.Control;
import Controls.Settings;
import Data.Dimention;
import Entities.Mesh;
import Entities.Triangle;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ZBuffer extends JPanel {
    private static int CROSSHAIR_LENGTH = 12;
    private LinkedListColor[][] zBuffer;
    private BufferedImage image;
    private static final Color background = Color.WHITE;

    public ZBuffer() {
        super();
        clearZBuffer();
        clearImage();
    }

    private void clearZBuffer() {
        zBuffer = new LinkedListColor[Math.max(1, getWidth())][Math.max(1, getHeight())];
        for (int x = 0; x < zBuffer.length; x++) {
            for (int y = 0; y < zBuffer[x].length; y++) {
                zBuffer[x][y] = new LinkedListColor();
            }
        }
    }
    private void clearImage() {
        this.image = new BufferedImage(zBuffer.length, zBuffer[0].length, BufferedImage.TYPE_INT_RGB);
    }

    private void rasterizeTriangle(Triangle triangle) {
        Dimention cornerOne = modifyCoordinates(triangle.cornerOne);
        Dimention cornerTwo = modifyCoordinates(triangle.cornerTwo);
        Dimention cornerThree = modifyCoordinates(triangle.cornerThree);

        // Bounding box
        int minX = (int) Math.max(0, Math.min(cornerOne.x(), Math.min(cornerTwo.x(), cornerThree.x())));
        int maxX = (int) Math.min(zBuffer.length - 1, Math.max(cornerOne.x(), Math.max(cornerTwo.x(), cornerThree.x())));
        int minY = (int) Math.max(0, Math.min(cornerOne.y(), Math.min(cornerTwo.y(), cornerThree.y())));
        int maxY = (int) Math.min(zBuffer[0].length - 1, Math.max(cornerOne.y(), Math.max(cornerTwo.y(), cornerThree.y())));

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                double[] bary = baryCoords(x, y, cornerOne, cornerTwo, cornerThree);
                double u = bary[0];
                double v = bary[1];
                double baryW = bary[2];

                // inside triangle
                if (u >= 0 && v >= 0 && baryW >= 0) {
                    double z = u * cornerOne.z() + v * cornerTwo.z() + baryW * cornerThree.z();
                    if (z > 0) {
                        double w = u * cornerOne.w() + v * cornerTwo.w() + baryW * cornerThree.w();
                        zBuffer[x][y].add(z, getColor(w));
                    }
                }
            }
        }
    }

    // Compute barycentric coordinates
    private double[] baryCoords(int pointX, int pointY, Dimention one, Dimention two, Dimention three) {
        double determinant = ((two.y() - three.y())*(one.x() - three.x()) + (three.x() - two.x())*(one.y() - three.y()));
        if (determinant == 0) {
            return new double[]{-1, -1, -1};
        }
        double u = ((two.y() - three.y())*(pointX - three.x()) + (three.x() - two.x())*(pointY - three.y())) / determinant;
        double v = ((three.y() - one.y())*(pointX - three.x()) + (one.x() - three.x())*(pointY - three.y())) / determinant;
        double w = 1 - u - v;
        return new double[]{u, v, w};
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // create image
        clearZBuffer();
        for (Mesh shape : Control.getScene().getShapes()) {
            for (Triangle triangle : shape.mesh) {
                rasterizeTriangle(triangle);
            }
        }

        // paint image
        clearImage();
        for (int x = 0; x < zBuffer.length; x++) {
            for (int y = 0; y < zBuffer[x].length; y++) {
                Color blendedColor = background;
                for (Color color : zBuffer[x][y]) {
                    int red = (int) (blendedColor.getRed() * ((double)(255 - color.getAlpha()) / 255) + color.getRed() * ((double)(color.getAlpha()) / 255));
                    int green = (int) (blendedColor.getGreen() * ((double)(255 - color.getAlpha()) / 255) + color.getGreen() * ((double)(color.getAlpha()) / 255));
                    int blue = (int) (blendedColor.getBlue() * ((double)(255 - color.getAlpha()) / 255) + color.getBlue() * ((double)(color.getAlpha()) / 255));

                    blendedColor = new Color(red, green, blue);
                }
                image.setRGB(x, y, blendedColor.getRGB());
            }
        }
        g.drawImage(image, 0, 0, null);

        // add crosshairs
        g.drawLine(getWidth() / 2 - CROSSHAIR_LENGTH / 2, getHeight() / 2, getWidth() / 2 + CROSSHAIR_LENGTH / 2, getHeight() / 2);
        g.drawLine(getWidth() / 2, getHeight() / 2 - CROSSHAIR_LENGTH / 2, getWidth() / 2, getHeight() / 2 + CROSSHAIR_LENGTH / 2);
    }
    public void tick() {
        repaint();
    }

    public Dimention modifyCoordinates(Dimention dimention) {
        Dimention result = Control.getScene().getEye().modifyCoordinates(dimention);
        return new Dimention(result.x() + (double) getWidth() / 2, result.y() * -1 + (double) getHeight() / 2, result.z(), result.w());
    }
    public Color getColor(double w) {
        double absW = Math.abs(w);
        boolean pos = w >= 0;

        int blurValue = 0;
        if (absW <= Settings.getBlurRange() / 2) {
            blurValue = (int) ((1 - absW / (Settings.getBlurRange() / 2)) * 255);
        }

        if (absW <= Settings.getSolidRange() / 2) {
            return new Color(0, 0, 0, blurValue);
        } else if (absW <= Settings.getSolidRange() / 2 + Settings.getGradientRange()) {
            int value = (int) ((absW - Settings.getSolidRange() / 2) / Settings.getGradientRange() * 255);
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
}
