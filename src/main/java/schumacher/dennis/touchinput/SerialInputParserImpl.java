package schumacher.dennis.touchinput;

public class SerialInputParserImpl implements SerialInputParser {
    private static final double X_MIN = 66,
            X_MAX = 961,
            X_DIFF = X_MAX - X_MIN,
            Y_MIN = 90,
            Y_MAX = 911,
            Y_DIFF = Y_MAX - Y_MIN;


    @Override
    public DoublePoint parse(DoublePoint point) {
        if (point.getX() > X_MAX || point.getY() > Y_MAX) return null;

        double x = (point.getX() - X_MIN) / X_DIFF;
        double y = 1 - (point.getY() - Y_MIN) / Y_DIFF;

        return new DoublePoint(x, y);
    }
}
