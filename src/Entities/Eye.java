package Entities;

import Data.Dimention;
import Data.FacingDirection;
import Data.Rotation;
import Data.RotationDirection;

public class Eye extends Entity {
    private Dimention location;
    private Rotation direction;
    private Dimention displayRelativePosition;
    public Eye() {
        location = new Dimention(0, 0, 0, 0);
        direction = new Rotation(0, 0, 0);
        displayRelativePosition = new Dimention(0, 0, 700, 0);
    }
    public Dimention modifyCoordinates(Dimention dimention) {
        // move camera to 0,0,0,0
        dimention = dimention.subtract(location);

        // rotate
        dimention = rotateRealToVisual(dimention);

        // account for display
        dimention = accountForDisplay(dimention);

        return dimention;
    }
    public Dimention rotateRealToVisual(Dimention dimention) {
        // rotate yz
        dimention = rotateYZ(dimention, true);
        // rotate wz
        dimention = rotateWZ(dimention, true);
        // rotate xz
        dimention = rotateXZ(dimention, true);
        return dimention;
    }
    public Dimention rotateVisualToReal(Dimention dimention, boolean accountForY) {
        // rotate xz
        dimention = rotateXZ(dimention, false);
        // rotate wz
        dimention = rotateWZ(dimention, false);
        if (accountForY) {
            // rotate yz
            dimention = rotateYZ(dimention, false);
        }

        return dimention;
    }
    public Dimention rotateXZ(Dimention dimention, boolean realToVisual) {
        // c 0 s 0
        // 0 1 0 0
        // -s 0 c 0
        // 0 0 0 1

        double cos = Math.cos(direction.xz() * (realToVisual ? 1 : -1));
        double sin = Math.sin(direction.xz() * (realToVisual ? 1 : -1));

        return new Dimention(
                dimention.x() * cos + dimention.z() * sin,
                dimention.y(),
                dimention.x() * sin * -1 + dimention.z() * cos,
                dimention.w()
        );
    }
    public Dimention rotateYZ(Dimention dimention, boolean realToVisual) {
        // 1 0 0 0
        // 0 c -s 0
        // 0 s c 0
        // 0 0 0 1

        double cos = Math.cos(direction.yz() * (realToVisual ? 1 : -1));
        double sin = Math.sin(direction.yz() * (realToVisual ? 1 : -1));

        return new Dimention(
                dimention.x(),
                dimention.y() * cos + dimention.z() * sin * -1,
                dimention.y() * sin + dimention.z() * cos,
                dimention.w()
        );
    }
    public Dimention rotateWZ(Dimention dimention, boolean realToVisual) {
        // 1 0 0 0
        // 0 1 0 0
        // 0 0 c -s
        // 0 0 s c

        double cos = Math.cos(direction.wz() * (realToVisual ? 1 : -1));
        double sin = Math.sin(direction.wz() * (realToVisual ? 1 : -1));

        return new Dimention(
                dimention.x(),
                dimention.y(),
                dimention.w() * sin * -1 + dimention.z() * cos,
                dimention.w() * cos + dimention.z() * sin
        );
    }
    public Dimention accountForDisplay(Dimention dimention) {
        double displayZ = displayRelativePosition.z() / dimention.z();
        return new Dimention(
                displayZ * dimention.x() + displayRelativePosition.x(),
                displayZ * dimention.y() + displayRelativePosition.y(),
                dimention.z(),
                dimention.w()
        );
    }
    public void move(int distance, Dimention direction) {
        location = location.move(distance, direction);
    }
    public void move(int distance, FacingDirection direction) {
        switch (direction) {
            case FORWARD_BACK -> move(distance, rotateVisualToReal(new Dimention(0, 0, 100, 0), false));
            case LEFT_RIGHT -> move(distance, rotateVisualToReal(new Dimention(100, 0, 0, 0), false));
            case ANA_KATA -> move(distance, rotateVisualToReal(new Dimention(0, 0, 0, 100), false));
        }
    }
    public void turn(double degree, RotationDirection direction) {
        this.direction = this.direction.protectedRotate(degree, direction);
        System.out.println(this.direction);
    }
}
