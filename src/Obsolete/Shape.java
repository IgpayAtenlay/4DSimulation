package Obsolete;

import Data.Dimention;

import java.awt.*;

public abstract class Shape {
    public abstract void draw(Graphics g, Panel panel);
    public abstract void move(int distance, Dimention direction);
}
