package schumacher.dennis.touchinput;

import com.fazecast.jSerialComm.SerialPort;

public class Main {
    static String PORT_NAME = "Serielles USB-Gerät (COM3)";

    public static void main(String[] args) {
        SerialPort target = null;
        for(SerialPort port : SerialPort.getCommPorts()){
            if(port.getDescriptivePortName().equals(PORT_NAME))
                target = port;
        }
        if(target != null){
            new Frame(target);
        }


    }
}
