package Controls;

public class Settings {
    private static double solidRange = 10;
    private static double blurRange = 100;
    private static double gradientRange = 5;
    private static int horizontalSpeed = 50;
    private static int forwardsSpeed = 5;
    private static int anaSpeed = 5;

    public static double getSolidRange() {
        return solidRange;
    }
    public static double getBlurRange() {
        return blurRange;
    }
    public static double getGradientRange() {
        return gradientRange;
    }
    public static int getHorizontalSpeed() {
        return horizontalSpeed;
    }
    public static int getForwardsSpeed() {
        return forwardsSpeed;
    }
    public static int getAnaSpeed() {
        return anaSpeed;
    }
}
