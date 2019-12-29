package schumacher.dennis.visualisierungtouchpad;

public class DoppelPunkt {
  private Double x = null;

  private Double y = null;


  public DoppelPunkt() {
  }

  public DoppelPunkt(Double x, Double y) {
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
