package schumacher.dennis.visualisierungtouchpad;

import com.fazecast.jSerialComm.SerialPort;

import javax.swing.*;

/**
 * Vereint die Hauptkomponenten der Anwendung
 * - Visualisierung
 * - Intepretation Spannungswerte
 */
class AnzeigeFenster extends JFrame {
  private final Zeichner zeichner;
  private final SpannungsFilter spannungsFilter;
  private final DatenstromAufbereiter datenstromAufbereiter;

  AnzeigeFenster(SerialPort serialPort, int width, int height) {
    this.datenstromAufbereiter = new DatenstromAufbereiter(serialPort, this::beiPunkt, this::beiFehler);
    this.zeichner = new Zeichner(this);
    this.spannungsFilter = new SpannungsFilterImplementierung();

    initialisiereNutzerOberfläche(width, height, serialPort.getDescriptivePortName());
  }

  /**
   * Definiert die Nutzeroberfläche durch das Setzen von:
   * - Titel
   * - Größe
   * - Zeichner Panel
   * - Sichtbarkeit
   * - Standard Operation die beim Schließen der Anwendung geschehen soll
   */
  private void initialisiereNutzerOberfläche(int width, int height, String name) {
    setTitle("Visualisierung Touchpad: " + name);
    setSize(width, height);
    add(zeichner);
    setVisible(true);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
  }

  /**
   * Konsumierer, der im Falle eines Fehlers wärhend der Intepretierung des Datenstroms aufgerufen wird
   * Beendet die Andwenung korrekt.
   */
  private void beiFehler(Exception e) {
    datenstromAufbereiter.stoppeLesen();
    e.printStackTrace();
    dispose();
  }

  /**
   * Konsumierer, der im Falle eines neuen Punktes aufgerufen wird.
   * - Konvertiert den generierten Punkt
   * - Zeichnet bei Notwendigkeit die grafische Oberfläche neu
   */
  private void beiPunkt(DoppelPunkt point) {
    DoppelPunkt ergebnis = spannungsFilter.konvertiereSpannungInKoordinate(point);

    if (ergebnis == null && zeichner.getPunkt() == null)
      return;

    zeichner.setPunkt(ergebnis);
    repaint();
  }
}
