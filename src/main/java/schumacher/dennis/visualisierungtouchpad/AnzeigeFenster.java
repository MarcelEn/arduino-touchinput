package schumacher.dennis.visualisierungtouchpad;

import com.fazecast.jSerialComm.SerialPort;

import javax.swing.*;

class AnzeigeFenster extends JFrame {
    private final Zeichner zeichner;
    private final SpannungsFilter spannungsFilter;
    private final DatenstromAufbereiter datenstromAufbereiter;

    AnzeigeFenster(SerialPort serialPort, int width, int height) {
        this.datenstromAufbereiter = new DatenstromAufbereiter(serialPort, this::beiPunkt, this::beiFehler);
        this.zeichner = new Zeichner(this);
        this.spannungsFilter = new SpannungsFilterImplementierung();

        setTitle("Visualisierung Touchpad: " + serialPort.getDescriptivePortName());
        setSize(width, height);
        add(zeichner);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void beiFehler(Exception e) {
        datenstromAufbereiter.stoppeLesen();
        e.printStackTrace();
        dispose();
    }

    private void beiPunkt(DoppelPunkt point){
        DoppelPunkt ergebnis = spannungsFilter.parse(point);

        if(ergebnis == null && zeichner.getPunkt() == null) return;

        zeichner.setPunkt(spannungsFilter.parse(point));
        repaint();
    }
}
