package schumacher.dennis.touchinput;

import com.fazecast.jSerialComm.SerialPort;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.cli.*;

public class Main {
    public static void main(String[] args) {
        new Main(args);
    }

    private Main(String[] args){
        SerialPort target = Objects.requireNonNullElseGet(
                getSerialPortByArgs(args),
                () -> Objects.requireNonNull(getSerialPortByOptionPane())
        );
        new Frame(target, 1280, 720);
    }

    private SerialPort getSerialPortByArgs(String[] args) {
        Options options = getOptions();

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cli;

        try {
            cli = parser.parse(options, args);
            if (cli.hasOption("help")) throw new ParseException("");
            String portName = cli.getOptionValue("port");
            return getSerialPortByName(portName);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("Touchinput", options);

            System.exit(1);
            return null;
        }
    }

    private Options getOptions() {
        Options options = new Options();
        Option serialPortOption = Option.builder()
                .desc("Serial Port name of your Arduino")
                .hasArg()
                .argName("port")
                .longOpt("port")
                .type(String.class)
                .build();

        Option helpOption = Option.builder()
                .desc("prints this help dialog")
                .longOpt("help")
                .hasArg(false)
                .type(Boolean.class)
                .build();

        options.addOption(serialPortOption);
        options.addOption(helpOption);
        return options;
    }

    private SerialPort getSerialPortByName(String portName) {
        if (portName == null) return null;
        return Arrays.stream(SerialPort.getCommPorts())
                .filter(port -> port.getDescriptivePortName().equals(portName))
                .findFirst()
                .orElse(null);
    }

    private SerialPort getSerialPortByOptionPane() {
        List<SerialPort> ports = Arrays.asList(SerialPort.getCommPorts());
        List<String> options = ports.stream()
                .map(SerialPort::getDescriptivePortName)
                .collect(Collectors.toList());

        if (options.size() == 0) {
            JOptionPane.showMessageDialog(null, "No Serial Port found.");
            return null;
        }

        int index = getSelectedOption(options);

        if (index == -1) {
            return null;
        }

        return ports.get(index);
    }

    private int getSelectedOption(List<String> options) {
        return JOptionPane.showOptionDialog(
                null,
                "Please select the port of your Arduino.",
                "Serial Port Selection",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                options.toArray(),
                options.get(0)
        );
    }
}
