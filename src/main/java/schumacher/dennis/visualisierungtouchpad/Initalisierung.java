package schumacher.dennis.visualisierungtouchpad;

import com.fazecast.jSerialComm.SerialPort;
import org.apache.commons.cli.*;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Initalisierung {
    public Initalisierung(String[] argumente) {
        SerialPort serialPort = Objects.requireNonNullElseGet(
                extrahiereSeriellenPortAusArgumenten(argumente),
                () -> Objects.requireNonNull(bekommeSeriellenPortUeberOptionPane())
        );
        new AnzeigeFenster(serialPort, 1280, 720);
    }

    private SerialPort extrahiereSeriellenPortAusArgumenten(String[] argumente) {
        Options optionen = bekommeOptionen();

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine kommandozeile;

        try {
            kommandozeile = parser.parse(optionen, argumente);
            if (kommandozeile.hasOption("hilfe")) throw new ParseException("");
            String portName = kommandozeile.getOptionValue("port");
            return bekommeSerialPortDurchName(portName);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("Touchinput", optionen);

            System.exit(1);
            return null;
        }
    }

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

    private SerialPort bekommeSerialPortDurchName(String portName) {
        if (portName == null) return null;
        return Arrays.stream(SerialPort.getCommPorts())
                .filter(port -> port.getDescriptivePortName().equals(portName))
                .findFirst()
                .orElse(null);
    }

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
