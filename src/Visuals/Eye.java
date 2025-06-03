package Visuals;

import Data.Dimention;

public class Eye {
    private Dimention location;
    private Dimention direction;
    private Dimention displayRelativePosition;
    public Eye() {
        location = new Dimention(0, 0, 0, 0);
        direction = new Dimention(0, 0, 0, 0);
        displayRelativePosition = new Dimention(0, 0, 10, 0);
    }

    public Dimention modifyCoordinates(Dimention dimention) {
        // move camera to 0,0,0,0
        double x = dimention.x() - location.x();
        double y = dimention.y() - location.y();
        double z = dimention.z() - location.z();
        double w = dimention.w() - location.w();

        // rotate
        Dimention rotated = new Dimention(
                Math.cos(direction.y()) * (
                                        Math.sin(direction.z()) * y + Math.cos(direction.z()) * x
                                ) - Math.sin(direction.y()) * z,
                Math.sin(direction.x()) * (
                                    Math.cos(direction.y()) * z + Math.sin(direction.y()) * (
                                        Math.sin(direction.z()) * y + Math.cos(direction.z()) * x
                                    )
                                ) + Math.cos(direction.x()) * (
                                    Math.cos(direction.z()) * y - Math.sin(direction.z()) * x
                                ),
                Math.cos(direction.x()) * (
                                        Math.cos(direction.y()) * z + Math.sin(direction.y()) * (
                                                Math.sin(direction.z()) * y + Math.cos(direction.z()) * x
                                        )
                                ) - Math.sin(direction.x()) * (
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
}
