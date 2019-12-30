package schumacher.dennis.visualisierungtouchpad;

/**
 * Beinhalted die Informationen eines Punktes in Form zweier Doubles
 */
public class DoppelPunkt {
  private Double x, y;

  public DoppelPunkt(Double x, Double y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Gibt den X-Wert zurück
   */
  public Double getX() {
    return x;
  }

  /**
   * Gibt den X-Wert zurück
   */
  public Double getY() {
    return y;
  }
}
