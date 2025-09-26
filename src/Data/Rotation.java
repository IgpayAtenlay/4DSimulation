package Data;

import Util.Compress;

public record Rotation(double yaw, double pitch, double fourDRotate) {
    public Rotation rotate(double yaw, double pitch, double fourDRotate) {
        return new Rotation(this.yaw + yaw, this.pitch + pitch, this.fourDRotate + fourDRotate);
    }
    public Rotation rotate(double degree, RotationDirection direction) {
        return switch(direction) {
            case YAW -> new Rotation(Compress.simplify(this.yaw + degree), this.pitch, this.fourDRotate);
            case PITCH -> new Rotation(this.yaw, Compress.simplify(this.pitch + degree), this.fourDRotate);
            case FOUR_D_ROTATE -> new Rotation(this.yaw, this.pitch, Compress.simplify(this.fourDRotate + degree));
        };
    }
    public Rotation protectedRotate(double degree, RotationDirection directions) {
        return switch (directions) {
            case YAW, FOUR_D_ROTATE -> rotate(degree, directions);
            case PITCH -> {
                if (this.pitch + degree > Math.PI / 2) {
                    yield new Rotation(yaw, Math.PI / 2, fourDRotate);
                } else if (this.pitch + degree < Math.PI / -2) {
                    yield new Rotation(yaw, Math.PI / -2, fourDRotate);
                } else {
                    yield rotate(degree, directions);
                }
            }
        };
    }
}
