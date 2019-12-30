package schumacher.dennis.visualisierungtouchpad;

/**
 * Implementiert das Interface SpannungsFilter
 * Dieser definiert die Funktionen, welche benötigt werden, um die Spannung korrekt umzuwandeln
 */
public class SpannungsFilterImplementierung implements SpannungsFilter {
  /**
   * Spannungskonstanten
   * beinhalted die Randwerte der Spannungen die bei Druck auf dem Touchpad an den äußersten Punkten zustande kommen.
   */
  private static final double X_MIN = 66,
          X_MAX = 961,
          X_DIFF = X_MAX - X_MIN,
          Y_MIN = 90,
          Y_MAX = 911,
          Y_DIFF = Y_MAX - Y_MIN;

  /**
   * Intepretiert Spannungen
   * - Spannungen, welche X > 961 mV oder Y > 911 mV enthalten werden als kein Druck intepretiert,
   *   zurückgegeben wird somit "null"
   * - ansonsten wird Spannung jeweils auf einen DoublePoint mit den Werten von 0 - 1 umgewandelt,
   *   um später proportional damit arbeiten zu können
   */
  @Override
  public DoppelPunkt konvertiereSpannungInKoordinate(DoppelPunkt punkt) {
    if (punkt.getX() > X_MAX || punkt.getY() > Y_MAX || punkt.getX() < X_MIN || punkt.getY() < Y_MIN){
      return null;
    }

    double x = (punkt.getX() - X_MIN) / X_DIFF;
    double y = 1 - (punkt.getY() - Y_MIN) / Y_DIFF;

    return new DoppelPunkt(x, y);
  }
}
