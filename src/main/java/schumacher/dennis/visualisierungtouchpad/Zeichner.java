package schumacher.dennis.visualisierungtouchpad;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;

public class Zeichner extends JPanel {
    private final JFrame frame;
    private Robot robot = null;
    private DoppelPunkt punkt = null;
    private DoppelPunkt punktDavor = null;

    Zeichner(JFrame frame) {
        this.frame = frame;
        try {
            this.robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    void setPunkt(DoppelPunkt punkt) {
        punktDavor = this.punkt;
        this.punkt = punkt;
    }

    DoppelPunkt getPunkt() {
        return punkt;
    }

    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);
        graphics.setColor(Color.BLACK);
        if (punkt != null) {
            Dimension size = getSize();
            int x = (int) (size.getWidth() * punkt.getX() - 3);
            int y = (int) (size.getHeight() * punkt.getY() - 3);

            if (robot != null) {
                robot.mouseMove(
                        x + frame.getX() + frame.getWidth() - getWidth() - 4,
                        y + frame.getY() + frame.getHeight() - getHeight() - 4
                );
                if (punktDavor != null) {
                    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                }
            }

            graphics.fillOval(x, y, 7, 7);
        } else if (punktDavor != null) {
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        }
    }
}
