package Data;

public record Dimention(double x, double y, double z, double w) {
    public double distance() {
        return Math.sqrt(x*x + y*y + z*z + w*w);
    }
    public double distance(Dimention dimention) {
        return new Dimention(this.x - dimention.x, this.y - dimention.y, this.z - dimention.z, this.w - dimention.w).distance();
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
    public boolean isVisible() {
        return z > 0;
    }
}
