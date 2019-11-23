package schumacher.dennis.touchinput;

import com.fazecast.jSerialComm.SerialPort;

import javax.swing.*;

class Frame extends JFrame {
    private final Drawer drawer;
    private final SerialInputParser serialInputParser;
    private final SerialHandler serialHandler;

    Frame(SerialPort serialPort, int width, int height) {
        this.serialHandler = new SerialHandler(serialPort, this::onPoint, this::onException);
        this.drawer = new Drawer(this);
        this.serialInputParser = new SerialInputParserImpl();

        setTitle("Touchinput: " + serialPort.getDescriptivePortName());
        setSize(width, height);
        add(drawer);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void onException(Exception e) {
        serialHandler.stopReading();
        e.printStackTrace();
        dispose();
    }

    private void onPoint(DoublePoint point){
        DoublePoint result = serialInputParser.parse(point);

        if(result == null && drawer.getPoint() == null) return;

        drawer.setPoint(serialInputParser.parse(point));
        repaint();
    }
}
