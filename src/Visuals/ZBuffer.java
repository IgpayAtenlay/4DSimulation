package Visuals;

import Controls.Control;
import Controls.Settings;
import Data.Dimention;
import Entities.Mesh4D;
import Entities.TriangularPyramid;
import Util.ColorValues;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class ZBuffer extends JPanel {
    private static int CROSSHAIR_LENGTH = 12;
    private Dimention[][] zBuffer;
    private BufferedImage image;

    public ZBuffer() {
        super();
        clearZBuffer();
        clearImage();
    }

    private void clearZBuffer() {
        zBuffer = new Dimention[Math.max(1, getWidth())][Math.max(1, getHeight())];
    }
    private void updateZBuffer() {
        clearZBuffer();
        for (Mesh4D shape : Control.getScene().getShapes()) {
            for (TriangularPyramid triangularPyramid : shape.mesh) {
                rasterizeTriangularPyramid(triangularPyramid);
            }
        }
    }
    private void clearImage() {
        this.image = new BufferedImage(zBuffer.length, zBuffer[0].length, BufferedImage.TYPE_INT_RGB);
    }
    private void updateImage() {
        updateZBuffer();
        clearImage();
        for (int x = 0; x < zBuffer.length; x++) {
            for (int y = 0; y < zBuffer[x].length; y++) {
                if (zBuffer[x][y] != null) {
                    Color color = getColor(zBuffer[x][y]);
                    image.setRGB(
                            x, y,
                            ColorValues.blendColors(
                                    Settings.getBackground(),
                                    color,
                                    color.getAlpha()
                            ).getRGB()
                    );
                } else {
                    image.setRGB(x, y, Settings.getBackground().getRGB());
                }
            }
        }
    }

    private void rasterizeTriangularPyramid(TriangularPyramid triangularPyramid) {
        Dimention[] corners = new Dimention[] {
                modifyCoordinates(triangularPyramid.cornerOne),
                modifyCoordinates(triangularPyramid.cornerTwo),
                modifyCoordinates(triangularPyramid.cornerThree),
                modifyCoordinates(triangularPyramid.cornerFour)
        };

        // Bounding box
        int minX = (int) Math.max(0, Math.min(corners[0].x(), Math.min(corners[1].x(), Math.min(corners[2].x(), corners[3].x()))));
        int maxX = (int) Math.min(zBuffer.length - 1, Math.max(corners[0].x(), Math.max(corners[1].x(), Math.max(corners[2].x(), corners[3].x()))));
        int minY = (int) Math.max(0, Math.min(corners[0].y(), Math.min(corners[1].y(), Math.min(corners[2].y(), corners[3].y()))));
        int maxY = (int) Math.min(zBuffer[0].length - 1, Math.max(corners[0].y(), Math.max(corners[1].y(), Math.max(corners[2].y(), corners[3].y()))));

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                ArrayList<Dimention> intersections = new ArrayList<>();
                for (int cornerOne = 0; cornerOne < 2; cornerOne++) {
                    for (int cornerTwo = cornerOne + 1; cornerTwo < 3; cornerTwo++) {
                        for (int cornerThree = cornerTwo + 1; cornerThree < 4; cornerThree++) {
                            Dimention intersection = rasterizeTriangle(corners[cornerOne], corners[cornerTwo], corners[cornerThree], x, y);
                            if (intersection != null) {
                                intersections.add(intersection);
                            }
                        }
                    }
                }
                if (!intersections.isEmpty()) {
                    Dimention closest = intersections.get(0);
                    for (Dimention current : intersections) {
                        if (current.distance() < closest.distance()) {
                            closest = current;
                        }
                    }
                    if (zBuffer[x][y] == null || zBuffer[x][y].distance() > closest.distance()) {
                        zBuffer[x][y] = closest;
                    }
                }
            }
        }
    }
    private Dimention rasterizeTriangle(Dimention cornerOne, Dimention cornerTwo, Dimention cornerThree, int x, int y) {
        double[] bary = baryCoords(x, y, cornerOne, cornerTwo, cornerThree);
        double u = bary[0];
        double v = bary[1];
        double baryW = bary[2];

        // inside triangle
        if (u >= 0 && v >= 0 && baryW >= 0) {
            double z = u * cornerOne.z() + v * cornerTwo.z() + baryW * cornerThree.z();
            if (z > 0) {
                double w = u * cornerOne.w() + v * cornerTwo.w() + baryW * cornerThree.w();
                return new Dimention(x, y, z, w);
            }
        }
        return null;
    }
    // Compute barycentric coordinates
    private double[] baryCoords(int pointX, int pointY, Dimention one, Dimention two, Dimention three) {
        double determinant = ((two.y() - three.y()) * (one.x() - three.x()) + (three.x() - two.x()) * (one.y() - three.y()));
        if (determinant == 0) {
            return new double[]{-1, -1, -1};
        }
        double u = ((two.y() - three.y()) * (pointX - three.x()) + (three.x() - two.x()) * (pointY - three.y())) / determinant;
        double v = ((three.y() - one.y()) * (pointX - three.x()) + (one.x() - three.x()) * (pointY - three.y())) / determinant;
        double w = 1 - u - v;
        return new double[]{u, v, w};
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        updateImage();
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
    private Color getColor(Dimention dimention) {
        double absW = Math.abs(dimention.w());
        double absZ = Math.abs(dimention.z());
        boolean pos = dimention.w() >= 0;
        double distance = Math.sqrt(absZ * absZ + absW * absW);

        int totalBlur = 0;
        if (distance <= Settings.getBlurRange() / 2) {
            totalBlur = (int) ((1 - distance / (Settings.getBlurRange() / 2)) * 255);
        }
        int zBlur = 0;
        if (absZ <= Settings.getBlurRange() / 2) {
            zBlur = (int) ((1 - absZ / (Settings.getBlurRange() / 2)) * 255);
        }
        int wBlur = 0;
        if (zBlur != 0) {
            wBlur = totalBlur * 255 / zBlur;
        }

        Color baseColor;
        if (absW <= Settings.getSolidRange() / 2) {
            baseColor = new Color(0, 0, 0);
        } else if (absW <= Settings.getSolidRange() / 2 + Settings.getGradientRange()) {
            int value = (int) ((absW - Settings.getSolidRange() / 2) / Settings.getGradientRange() * 255);
            if (pos) {
                baseColor = new Color(value, 0, 0);
            } else {
                baseColor = new Color(0, 0, value);
            }
        } else {
            if (pos) {
                baseColor = new Color(255, 0, 0);
            } else {
                baseColor = new Color(0, 0, 255);
            }
        }

        return ColorValues.blendColors(Settings.getBackground(), baseColor, zBlur, wBlur);
//            return Color.BLUE;
    }
}
