package schumacher.dennis.touchinput;

public class DoublePoint {
    private Double x = null;

    private Double y = null;


    public DoublePoint() {
    }

    public DoublePoint(Double x, Double y) {
        this.x = x;
        this.y = y;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public Double getX() {
        return x;
    }

    public Double getY() {
        return y;
    }
}
