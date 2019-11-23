package schumacher.dennis.touchinput;

import com.fazecast.jSerialComm.*;

import java.io.*;
import java.util.function.Consumer;

public class SerialHandler extends Thread {
    private final SerialPort target;
    private final Consumer<DoublePoint> onPoint;
    private final Consumer<Exception> onException;
    private boolean read = true;


    SerialHandler(SerialPort target, Consumer<DoublePoint> onPoint, Consumer<Exception> onException) {
        this.target = target;
        this.onPoint = onPoint;
        this.onException = onException;
        setDaemon(true);
        start();
    }

    @Override
    public void run() {
        try {
            target.openPort();
            BufferedReader reader = new BufferedReader(new InputStreamReader((target.getInputStream())));

            StringBuilder builder = new StringBuilder();

            while (this.read) {
                try {
                    builder.append((char) reader.read());
                    String string = builder.toString();
                    if (string.contains("\n")) {
                        doThings(string);
                        builder = new StringBuilder();
                    }
                } catch (IOException ignore) {
                } catch (Exception e) {
                    onException.accept(e);
                }
            }
        } catch (Exception e) {
            onException.accept(e);
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

    void stopReading() {
        this.read = false;
    }
}
