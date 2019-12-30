package schumacher.dennis.visualisierungtouchpad;

import com.fazecast.jSerialComm.*;

import java.io.*;
import java.util.function.Consumer;

/**
 * Abstrahiert das Lesen des seriellen Datenstroms
 */
public class DatenstromAufbereiter extends Thread {
  private final SerialPort serialPort;
  private final Consumer<DoppelPunkt> beiPunkt;
  private final Consumer<Exception> beiFehler;
  private BufferedReader reader;
  private boolean lese = true;


  DatenstromAufbereiter(SerialPort serialPort, Consumer<DoppelPunkt> beiPunkt, Consumer<Exception> beiFehler) {
    this.serialPort = serialPort;
    this.beiPunkt = beiPunkt;
    this.beiFehler = beiFehler;
    setDaemon(true);
    start();
  }

  /**
   * Wird nach dem Start des Threads aufgerufen
   */
  @Override
  public void run() {
    initialisierePort();
    intepretiereUndDelegiereDatenstrom();
  }

  /**
   * Öffnet den Port und erstellt einen BufferedReader, um den Stream lesen zu können
   */
  private void initialisierePort() {
    serialPort.openPort();
    reader = new BufferedReader(new InputStreamReader((serialPort.getInputStream())));
  }

  /**
   * Ließt den Datenstrom und ruft bei einem Punkt oder Fehler die entsprechenden
   * Rückrufmethoden auf (beiPunkt / beiFehler)
   */
  private void intepretiereUndDelegiereDatenstrom() {
    while (this.lese) {
      try {
        DoppelPunkt punkt = konvertiereZuPunkt(leseNaechsteZeile());
        if (punkt != null) {
          beiPunkt.accept(punkt);
        }
      } catch (Exception e) {
        beiFehler.accept(e);
      }
    }
  }

  /**
   * Ließt den Datenstrom und gibt, sobald es eine neue Zeile gibt diese zurück.
   * ReadLine des BufferedReaders wurde nicht verwendet, aufgrund von Konflikten
   * des Zeilenumbruchs auf Linux und Windows
   */
  private String leseNaechsteZeile() {
    StringBuilder builder = new StringBuilder();
    while (this.lese) {
      try {
        builder.append((char) reader.read());
        String string = builder.toString();
        if (string.contains("\n")) {
          return string;
        }
      } catch (IOException ignore) {
      }
    }
    return null;
  }

  /**
   * Konvertiert eine Zeichenkette des Schemas "Double;Double;\n" => "234;343;\n"
   * in ein DoppelPunkt Object
   */
  private DoppelPunkt konvertiereZuPunkt(String string) {
    try {
      String[] split = string.split(";");
      Double y = Double.parseDouble(split[0]);
      Double x = Double.parseDouble(split[1]);
      return new DoppelPunkt(x, y);
    } catch (Exception ignore) {
    }
    return null;
  }

  /**
   * Hiermit wird der asynchrone Prozess in dem Lesethread beendet
   */
  void stoppeLesen() {
    this.lese = false;
  }
}
