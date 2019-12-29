package schumacher.dennis.visualisierungtouchpad;

import com.fazecast.jSerialComm.*;

import java.io.*;
import java.util.function.Consumer;

public class DatenstromAufbereiter extends Thread {
    private final SerialPort serialPort;
    private final Consumer<DoppelPunkt> beiPunkt;
    private final Consumer<Exception> beiFehler;
    private boolean lese = true;


    DatenstromAufbereiter(SerialPort serialPort, Consumer<DoppelPunkt> beiPunkt, Consumer<Exception> beiFehler) {
        this.serialPort = serialPort;
        this.beiPunkt = beiPunkt;
        this.beiFehler = beiFehler;
        setDaemon(true);
        start();
    }

    @Override
    public void run() {
        try {
            serialPort.openPort();
            BufferedReader reader = new BufferedReader(new InputStreamReader((serialPort.getInputStream())));

            StringBuilder builder = new StringBuilder();

            while (this.lese) {
                try {
                    builder.append((char) reader.read());
                    String string = builder.toString();
                    if (string.contains("\n")) {
                        DoppelPunkt punkt = konvertiereZuPunkt(string);
                        if (punkt != null) {
                            beiPunkt.accept(punkt);
                        }
                        builder = new StringBuilder();
                    }
                } catch (IOException ignore) {
                } catch (Exception e) {
                    beiFehler.accept(e);
                }
            }
        } catch (Exception e) {
            beiFehler.accept(e);
        }
    }

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

    void stoppeLesen() {
        this.lese = false;
    }
}
