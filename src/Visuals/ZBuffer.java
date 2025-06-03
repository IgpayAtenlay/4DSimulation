package Visuals;

import Data.Dimention;
import Shapes.CompositeShape;
import Shapes.Triangle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class ZBuffer extends JPanel {
    public static int CROSSHAIR_LENGTH = 12;
    public static int SPEED = 50;
    private final ArrayList<CompositeShape> shapes;
    private final Eye eye;
    private int WIDTH;
    private int HEIGHT;
    private BufferedImage image;
    private LinkedListColor[][] zBuffer;
    private static final Color background = Color.WHITE;

    public ZBuffer(int width, int height) {
        this.WIDTH = width;
        this.HEIGHT = height;
        clearZBuffer();
        clearImage();
        shapes = new ArrayList<>();
        this.eye = new Eye();
    }

    private void clearZBuffer() {
        zBuffer = new LinkedListColor[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                zBuffer[x][y] = new LinkedListColor();
            }
        }
    }
    private void clearImage() {
        this.image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    }

    private void rasterizeTriangle(Dimention cornerOne,
                                   Dimention cornerTwo,
                                   Dimention cornerThree,
                                   Color color) {

        // Bounding box
        int minX = (int) Math.max(0, Math.min(cornerOne.x(), Math.min(cornerTwo.x(), cornerThree.x())));
        int maxX = (int) Math.min(WIDTH - 1, Math.max(cornerOne.x(), Math.max(cornerTwo.x(), cornerThree.x())));
        int minY = (int) Math.max(0, Math.min(cornerOne.y(), Math.min(cornerTwo.y(), cornerThree.y())));
        int maxY = (int) Math.min(HEIGHT - 1, Math.max(cornerOne.y(), Math.max(cornerTwo.y(), cornerThree.y())));

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                double[] bary = baryCoords(x, y, cornerOne, cornerTwo, cornerThree);
                double u = bary[0];
                double v = bary[1];
                double w = bary[2];

                // inside triangle
                if (u >= 0 && v >= 0 && w >= 0) {
                    double z = u * cornerOne.z() + v * cornerTwo.z() + w * cornerThree.z();
                    zBuffer[x][y].add(z, color);
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
        for (CompositeShape shape : shapes) {
            for (Triangle triangle : shape.mesh) {
                rasterizeTriangle(modifyCoordinates(triangle.cornerOne), modifyCoordinates(triangle.cornerTwo), modifyCoordinates(triangle.cornerThree), Color.BLACK);
            }
        }

        // paint image
        clearImage();
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                Color blendedColor = Color.WHITE;
                for (Color color : zBuffer[x][y]) {
                    System.out.println("color");
                    System.out.println(blendedColor);
                    System.out.println(color);
                    int red = (int) (blendedColor.getRed() * ((double)(255 - color.getAlpha()) / 255) + color.getRed() * ((double)(color.getAlpha()) / 255));
                    int green = (int) (blendedColor.getGreen() * ((double)(255 - color.getAlpha()) / 255) + color.getGreen() * ((double)(color.getAlpha()) / 255));
                    int blue = (int) (blendedColor.getBlue() * ((double)(255 - color.getAlpha()) / 255) + color.getBlue() * ((double)(color.getAlpha()) / 255));
                    System.out.println(red);
                    System.out.println(green);
                    System.out.println(blue);

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

    public void add(CompositeShape shape) {
        shapes.add(shape);
    }
    public void tick() {
        if (Keys.isKeyPressed(KeyEvent.VK_W) && shapes.size() > 0) {
            shapes.get(0).move(SPEED, new Dimention(0, 1, 0, 0));
        }
        if (Keys.isKeyPressed(KeyEvent.VK_S) && shapes.size() > 0) {
            shapes.get(0).move(SPEED, new Dimention(0, -1, 0, 0));
        }
        if (Keys.isKeyPressed(KeyEvent.VK_A) && shapes.size() > 0) {
            shapes.get(0).move(SPEED, new Dimention(-1, 0, 0, 0));
        }
        if (Keys.isKeyPressed(KeyEvent.VK_D) && shapes.size() > 0) {
            shapes.get(0).move(SPEED, new Dimention(1, 0, 0, 0));
        }
        if (Keys.isKeyPressed(KeyEvent.VK_I) && shapes.size() > 0) {
            shapes.get(0).move(SPEED / 2, new Dimention(0, 0, 1, 0));
        }
        if (Keys.isKeyPressed(KeyEvent.VK_K) && shapes.size() > 0) {
            shapes.get(0).move(SPEED / 2, new Dimention(0, 0, -1, 0));
        }
        if (Keys.isKeyPressed(KeyEvent.VK_J) && shapes.size() > 0) {
            shapes.get(0).move(SPEED / 2, new Dimention(0, 0, 0, -1));
        }
        if (Keys.isKeyPressed(KeyEvent.VK_L) && shapes.size() > 0) {
            shapes.get(0).move(SPEED / 2, new Dimention(0, 0, 0, 1));
        }

        repaint();
    }
    public Dimention modifyCoordinates(Dimention dimention) {
        Dimention result = eye.modifyCoordinates(dimention);
        return new Dimention(result.x() + (double) getWidth() / 2, result.y() * -1 + (double) getHeight() / 2, result.z(), result.w());
    }
}
