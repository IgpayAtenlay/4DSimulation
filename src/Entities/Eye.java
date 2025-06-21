package Entities;

import Data.Dimention;

public class Eye extends Entity {
    private Dimention location;
    private Dimention direction;
    private Dimention displayRelativePosition;
    public Eye() {
        location = new Dimention(0, 0, 0, 0);
        direction = new Dimention(0, 0, 0, 0);
        displayRelativePosition = new Dimention(0, 0, 700, 0);
    }
    public Dimention modifyCoordinates(Dimention dimention) {
        // move camera to 0,0,0,0
        double x = dimention.x() - location.x();
        double y = dimention.y() - location.y();
        double z = dimention.z() - location.z();
        double w = dimention.w() - location.w();

        // rotate
        Dimention rotated = new Dimention(
                Math.cos(direction.x()) * (
                                        Math.sin(direction.z()) * y + Math.cos(direction.z()) * x
                                ) - Math.sin(direction.x()) * z,
                Math.sin(direction.y() * -1) * (
                                    Math.cos(direction.x()) * z + Math.sin(direction.x()) * (
                                        Math.sin(direction.z()) * y + Math.cos(direction.z()) * x
                                    )
                                ) + Math.cos(direction.y() * -1) * (
                                    Math.cos(direction.z()) * y - Math.sin(direction.z()) * x
                                ),
                Math.cos(direction.y() * -1) * (
                                        Math.cos(direction.x()) * z + Math.sin(direction.x()) * (
                                                Math.sin(direction.z()) * y + Math.cos(direction.z()) * x
                                        )
                                ) - Math.sin(direction.y() * -1) * (
                                        Math.cos(direction.z()) * y - Math.sin(direction.z()) * x
                                ),
                w
        );

        return new Dimention(
                displayRelativePosition.z() / rotated.z() * rotated.x() + displayRelativePosition.x(),
                displayRelativePosition.z() / rotated.z() * rotated.y() + displayRelativePosition.y(),
                z,
                w
        );
    }
    public void move(int distance, Dimention direction) {
        location = location.move(distance, direction);
    }
    public void turn(double degree, Dimention direction) {
        this.direction = this.direction.move(degree, direction);
    }
}
