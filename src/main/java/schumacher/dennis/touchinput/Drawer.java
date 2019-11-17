package schumacher.dennis.touchinput;

import javax.swing.*;
import java.awt.*;

public class Drawer extends JPanel {
    private final JFrame frame;
    private Robot robot = null;
    private DoublePoint point = null;

    public Drawer(JFrame frame) {
        this.frame = frame;
        try {
            this.robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public void setPoint(DoublePoint point) {
        this.point = point;
    }

    public DoublePoint getPoint() {
        return point;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.BLACK);
        if (point != null) {
            Dimension size = getSize();
            int x = (int) (size.getWidth() * point.getX() - 3);
            int y = (int) (size.getHeight() * point.getY() - 3);

            if(robot != null){
                robot.mouseMove(
                        x + frame.getX() + frame.getWidth() - getWidth() - 4,
                        y + frame.getY() + frame.getHeight() - getHeight() - 4
                );
            }


            g.fillOval(x, y, 7, 7);
        }
    }
}
