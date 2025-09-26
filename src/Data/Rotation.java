package Data;

import Util.Compress;

public record Rotation(double xz, double yz, double wz) {
    public Rotation rotate(double xz, double yz, double wz) {
        return new Rotation(this.xz + xz, this.yz + yz, this.wz + wz);
    }
    public Rotation rotate(double degree, RotationDirection direction) {
        return switch(direction) {
            case XZ -> new Rotation(Compress.simplify(this.xz + degree), this.yz, this.wz);
            case YZ -> new Rotation(this.xz, Compress.simplify(this.yz + degree), this.wz);
            case WZ -> new Rotation(this.xz, this.yz, Compress.simplify(this.wz + degree));
        };
    }
    public Rotation protectedRotate(double degree, RotationDirection directions) {
        return switch (directions) {
            case XZ, WZ -> rotate(degree, directions);
            case YZ -> {
                if (this.yz + degree > Math.PI / 2) {
                    yield new Rotation(xz, Math.PI / 2, wz);
                } else if (this.yz + degree < Math.PI / -2) {
                    yield new Rotation(xz, Math.PI / -2, wz);
                } else {
                    yield rotate(degree, directions);
                }
            }
        };
    }
}
