package schumacher.dennis.visualisierungtouchpad;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;

/**
 * Zeichnet einen Punkt auf die gegebene Koordinate in ein JPanel (Swing spezifisch)
 */
public class Zeichner extends JPanel {
  private final JFrame frame;
  private Robot robot = null;
  private DoppelPunkt punkt = null;
  private DoppelPunkt punktDavor = null;

  Zeichner(JFrame frame) {
    this.frame = frame;
    try {
      this.robot = new Robot();
    } catch (AWTException e) {
      e.printStackTrace();
    }
  }

  /**
   * Setzt einen neuen Punkt und merkt sich den alten Wert
   */
  void setPunkt(DoppelPunkt punkt) {
    punktDavor = this.punkt;
    this.punkt = punkt;
  }

  /**
   * Gibt den aktuellen Wert des Punktkes zurück
   */
  DoppelPunkt getPunkt() {
    return punkt;
  }

  /**
   * Zeichnet auf das Panel und steuert die Maus
   */
  @Override
  public void paint(Graphics graphics) {
    super.paint(graphics);
    graphics.setColor(Color.BLACK);

    if (punkt != null) {
      int x = korrigierePunktbreite(bekommeAktuelleXKoordinate());
      int y = korrigierePunktbreite(bekommeAktuelleYKoordinate());

      steuereMaus(x, y);
      graphics.fillOval(x, y, 7, 7);
    } else if (punktDavor != null) {
      robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }
  }

  /**
   * Gibt den aktuellen Pixel der Y-Koordinate zurück
   */
  private int bekommeAktuelleYKoordinate() {
    return (int) (getSize().getHeight() * punkt.getY());
  }

  /**
   * Gibt den aktuellen Pixel der X-Koordinate zurück
   */
  private int bekommeAktuelleXKoordinate() {
    return (int) (getSize().getWidth() * punkt.getX());
  }

  /**
   * Versetzt den Anfang der Koordianate,
   * um die Hälfte der Größe des Punktes,
   * welcher gezeichnet wird
   */
  private int korrigierePunktbreite(int wert) {
    return wert - 3;
  }

  /**
   * Bewegt die Maus an die Koordinate und
   * drückt ggf. die linke Maustaste
   */
  private void steuereMaus(int x, int y) {
    if (robot != null) {
      robot.mouseMove(
              x + frame.getX() + frame.getWidth() - getWidth() - 4,
              y + frame.getY() + frame.getHeight() - getHeight() - 4
      );
      if (punktDavor != null) {
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
      }
    }
  }
}
