package Entities;

import Data.Dimention;
import Data.FacingDirection;
import Data.Rotation;
import Data.RotationDirection;
import org.ejml.simple.SimpleMatrix;

public class Eye extends Entity {
    private Dimention location;
    private Rotation direction;
    private Dimention displayRelativePosition;
    public Eye() {
        location = new Dimention(0, 0, 0, 0);
        direction = new Rotation(0.5, 0, 0);
        displayRelativePosition = new Dimention(0, 0, 700, 0);
    }
    public Dimention modifyCoordinates(Dimention dimention) {
        // move camera to 0,0,0,0
        dimention = dimention.subtract(location);

        // rotate
        dimention = intrinsicRotation(dimention);

        // account for display
        dimention = accountForDisplay(dimention);

        return dimention;
    }
    public Dimention intrinsicRotation(Dimention dimention) {
        return Dimention.fromMatrix(yawMatrix(true).mult(pitchMatrix(true)).mult(fourDRotateMatrix(true)).mult(dimention.getMatrix()));
    }
    public Dimention extrinsicRotation(Dimention dimention, boolean accountForPitch) {
        if (accountForPitch) {
            return Dimention.fromMatrix(fourDRotateMatrix(false).mult(pitchMatrix(false)).mult(yawMatrix(false)).mult(dimention.getMatrix()));
        } else {
            return Dimention.fromMatrix(fourDRotateMatrix(false).mult(yawMatrix(false)).mult(dimention.getMatrix()));
        }
    }
    private SimpleMatrix yawMatrix(boolean intrinsic) {
        double cosYaw = Math.cos(direction.yaw() * (intrinsic ? 1 : -1));
        double sinYaw = Math.sin(direction.yaw() * (intrinsic ? 1 : -1));
        return new SimpleMatrix(
                new double[][] {
                        new double[] {cosYaw, 0, sinYaw, 0},
                        new double[] {0, 1, 0, 0},
                        new double[] {sinYaw * -1, 0, cosYaw, 0},
                        new double[] {0, 0, 0, 1}
                }
        );
    }
    private SimpleMatrix pitchMatrix(boolean intrinsic) {
        double cosPitch = Math.cos(direction.pitch() * (intrinsic ? 1 : -1));
        double sinPitch = Math.sin(direction.pitch() * (intrinsic ? 1 : -1));
        return new SimpleMatrix(
                new double[][] {
                        new double[] {1, 0, 0, 0},
                        new double[] {0, cosPitch, sinPitch * -1, 0},
                        new double[] {0, sinPitch, cosPitch, 0},
                        new double[] {0, 0, 0, 1}
                }
        );
    }
    private SimpleMatrix fourDRotateMatrix(boolean intrinsic) {
        double cosFourDRotate = Math.cos(direction.fourDRotate() * (intrinsic ? 1 : -1));
        double sinFourDRotate = Math.sin(direction.fourDRotate() * (intrinsic ? 1 : -1));
        return new SimpleMatrix(
                new double[][] {
                        new double[] {1, 0, 0, 0},
                        new double[] {0, 1, 0, 0},
                        new double[] {0, 0, cosFourDRotate, sinFourDRotate * -1},
                        new double[] {0, 0, sinFourDRotate, cosFourDRotate}
                }
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
            case FORWARD_BACK -> move(distance, extrinsicRotation(new Dimention(0, 0, 100, 0), false));
            case LEFT_RIGHT -> move(distance, extrinsicRotation(new Dimention(100, 0, 0, 0), false));
            case ANA_KATA -> move(distance, extrinsicRotation(new Dimention(0, 0, 0, 100), false));
        }
    }
    public void turn(double degree, RotationDirection direction) {
        this.direction = this.direction.protectedRotate(degree, direction);
        System.out.println(this.direction);
    }
}
