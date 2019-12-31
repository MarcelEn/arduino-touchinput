package schumacher.dennis.visualisierungtouchpad;

import com.fazecast.jSerialComm.SerialPort;
import org.apache.commons.cli.*;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Sammelt die nötigen Informationen und erstellt das Anzeigefenster
 */
public class Initalisierung {
  private SerialPort serialPort;
  private AnzeigeFenster anzeigeFenster;

  public Initalisierung(String[] argumente) {
    serialPort = bekommeSeriellenPort(argumente);
    anzeigeFenster = new AnzeigeFenster(serialPort, 1280, 720);
  }

  /**
   * Gibt den seriellen Port zurück
   * - löst den Port anhand gegebener Kommandozeilenargumente auf
   * - im Fehlerfall wird eine Oberfläche angezeigt,
   * welche eine Auswahlmöglichkeit der verbundenen seriellen Ports bereitstellt
   */
  private SerialPort bekommeSeriellenPort(String[] argumente) {
    return Objects.requireNonNullElseGet(
            extrahiereSeriellenPortAusArgumenten(argumente),
            () -> Objects.requireNonNull(bekommeSeriellenPortUeberOptionPane())
    );
  }

  /**
   * intepretiert die Kommandozeilen Argumente und gibt ggf. einen seriellen Port zurück.
   */
  private SerialPort extrahiereSeriellenPortAusArgumenten(String[] argumente) {
    Options optionen = bekommeOptionen();

    try {
      return parseKommandozeile(argumente, optionen);
    } catch (ParseException e) {
      System.out.println(e.getMessage());
      zeigeHilfeAn(optionen);
      System.exit(1);
      return null;
    }
  }

  /**
   * zeigt die Hilfe der Kommandozeilenargumente auf der Kommandozeile an.
   */
  private void zeigeHilfeAn(Options optionen) {
    new HelpFormatter().printHelp("Touchinput", optionen);
  }

  /**
   * intepretiert die Kommandozeilen Argumente.
   */
  private SerialPort parseKommandozeile(String[] argumente, Options optionen) throws ParseException {
    CommandLineParser parser = new DefaultParser();
    CommandLine kommandozeile;

    kommandozeile = parser.parse(optionen, argumente);
    if (kommandozeile.hasOption("hilfe"))
      throw new ParseException("");

    String portName = kommandozeile.getOptionValue("port");
    return bekommeSerialPortDurchName(portName);
  }

  /**
   * Hilfunktionen zum bauen der Optionen für die Kommandozeilgenargumente
   * Gibt die Optionenen zurück
   */
  private Options bekommeOptionen() {
    Options optionen = new Options();
    Option seriellePortOption = Option.builder()
            .desc("Serieller Portname vom Arduino")
            .hasArg()
            .argName("port")
            .longOpt("port")
            .type(String.class)
            .build();

    Option hilfeOption = Option.builder()
            .desc("Zeigt diesen Dialog an")
            .longOpt("hilfe")
            .hasArg(false)
            .type(Boolean.class)
            .build();

    optionen.addOption(seriellePortOption);
    optionen.addOption(hilfeOption);
    return optionen;
  }

  /**
   * Zeigt die Oberfläache für die serielle Port Auswahl an
   * gibt den ggf. ausgewählten Port zurück
   * falls kein Port angeschlossen ist wird eine Warnmeldung angezeigt und das Programm beendet
   */
  private SerialPort bekommeSeriellenPortUeberOptionPane() {
    List<SerialPort> ports = Arrays.asList(SerialPort.getCommPorts());
    List<String> optionen = ports.stream()
            .map(SerialPort::getDescriptivePortName)
            .collect(Collectors.toList());

    if (optionen.size() == 0) {
      JOptionPane.showMessageDialog(null, "Es konnten keine Ports gefunden werden.");
      return null;
    }

    int index = bekommeSelektierteOptionsIndex(optionen);

    if (index == -1) {
      return null;
    }

    return ports.get(index);
  }

  /**
   * Gibt den seriellen Port anhand des gegeben Namens zurück, sofern dieser vorhanden ist.
   */
  private SerialPort bekommeSerialPortDurchName(String portName) {
    if (portName == null) return null;
    return Arrays.stream(SerialPort.getCommPorts())
            .filter(port -> port.getDescriptivePortName().equals(portName))
            .findFirst()
            .orElse(null);
  }

  /**
   * Gibt den Ausgewählten index im Options Array zurück.
   * Dabei wird die Oberfläche dem Nutzer angezeigt.
   */
  private int bekommeSelektierteOptionsIndex(List<String> options) {
    return JOptionPane.showOptionDialog(
            null,
            "Bitte wähle den Port des Arduinos.",
            "Serielle Port Auswahl",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.PLAIN_MESSAGE,
            null,
            options.toArray(),
            options.get(0)
    );
  }
}
