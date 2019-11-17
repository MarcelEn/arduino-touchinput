package schumacher.dennis.touchinput;

import com.fazecast.jSerialComm.SerialPort;

import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {
    private final Drawer drawer;
    private final SerialInputParser serialInputParser;

    Frame(SerialPort serialPort) {
        new SerialHandler(serialPort, this::onPoint);
        this.drawer = new Drawer(this);
        this.serialInputParser = new SerialInputParserImpl();

        setSize(300, 200);
        add(drawer);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void onPoint(DoublePoint point){
        DoublePoint result = serialInputParser.parse(point);

        if(result == null && drawer.getPoint() == null) return;

        drawer.setPoint(serialInputParser.parse(point));
        repaint();
    }
}
