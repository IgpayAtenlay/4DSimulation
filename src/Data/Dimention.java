package Data;

import org.ejml.simple.SimpleMatrix;

public record Dimention(double x, double y, double z, double w) {
    public double distance() {
        return Math.sqrt(distanceSquared());
    }
    public double distanceSquared() {
        return x*x + y*y + z*z + w*w;
    }
    public double distance(Dimention dimention) {
        return subtract(dimention).distance();
    }
    public Dimention move(double distance, Dimention direction) {
        return new Dimention(
                x + direction.x() * distance / direction.distance(),
                y + direction.y() * distance / direction.distance(),
                z + direction.z() * distance / direction.distance(),
                w + direction.w() * distance / direction.distance()
        );
    }
    public Dimention move(Dimention start, Dimention end) {
        return move((int) start.distance(end), start.direction(end));
    }
    public Dimention direction(Dimention end) {
        return new Dimention(end.x - x, end.y - y, end.z - z, end.w - w);
    }
    public boolean isCloser(Dimention other) {
        double distance = distanceSquared();
        double otherDistance = other.distanceSquared();
        if (distance == otherDistance) {
            return Math.abs(w) < Math.abs(other.w);
        } else {
            return distance < otherDistance;
        }
    }
    public boolean isCloserW(Dimention other) {
        if (Math.abs(w) < Math.abs(other.w)) {
            return true;
        } else if (Math.abs(w) > Math.abs(other.w)) {
            return false;
        } else {
            return isCloser(other);
        }
    }
    public boolean isVisible() {
        return z > 0;
    }

    // matrix
    public SimpleMatrix getMatrix() {
        return new SimpleMatrix(
                new double[][] {
                        new double[] {x},
                        new double[] {y},
                        new double[] {z},
                        new double[] {w}
                }
        );
    }
    public static Dimention fromMatrix(SimpleMatrix matrix) {
        return new Dimention(matrix.get(0, 0), matrix.get(1, 0), matrix.get(2, 0), matrix.get(3, 0));
    }
    public Dimention add(Dimention dimention) {
        return new Dimention(this.x + dimention.x, this.y + dimention.y, this.z + dimention.z, this.w + dimention.w);
    }
    public Dimention subtract(Dimention dimention) {
        return new Dimention(this.x - dimention.x, this.y - dimention.y, this.z - dimention.z, this.w - dimention.w);
    }
}
