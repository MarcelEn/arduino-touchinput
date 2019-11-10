package schumacher.dennis.touchinput;

import com.fazecast.jSerialComm.*;

import java.awt.*;
import java.io.*;
import java.util.function.Consumer;

public class SerialHandler extends Thread {
    private final SerialPort target;
    private final Consumer<DoublePoint> onPoint;


    public SerialHandler(SerialPort target, Consumer<DoublePoint> onPoint) {
        this.target = target;
        this.onPoint = onPoint;
        start();
    }

    @Override
    public void run() {
        target.openPort();
        BufferedReader reader = new BufferedReader(new InputStreamReader((target.getInputStream())));

        StringBuilder builder = new StringBuilder();

        while (true) {
            try {
                builder.append((char) reader.read());
                String string = builder.toString();
                if (string.contains("\n")) {
                    doThings(string);
                    builder = new StringBuilder();
                }
            } catch (IOException ignore) {
            }
        }
    }

    private void doThings(String string) {
        try {
            String[] split = string.split(";");
            Double y = Double.parseDouble(split[0]);
            Double x = Double.parseDouble(split[1]);
            onPoint.accept(new DoublePoint(x, y));
        } catch (Exception ignore) {
        }
    }
}
