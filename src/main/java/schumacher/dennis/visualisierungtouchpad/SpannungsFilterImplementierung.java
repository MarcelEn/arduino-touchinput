package schumacher.dennis.visualisierungtouchpad;

public class SpannungsFilterImplementierung implements SpannungsFilter {
    private static final double X_MIN = 66,
            X_MAX = 961,
            X_DIFF = X_MAX - X_MIN,
            Y_MIN = 90,
            Y_MAX = 911,
            Y_DIFF = Y_MAX - Y_MIN;


    @Override
    public DoppelPunkt parse(DoppelPunkt punkt) {
        if (punkt.getX() > X_MAX || punkt.getY() > Y_MAX) return null;

        double x = (punkt.getX() - X_MIN) / X_DIFF;
        double y = 1 - (punkt.getY() - Y_MIN) / Y_DIFF;

        return new DoppelPunkt(x, y);
    }
}
