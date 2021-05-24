package server;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Capture {
    private Robot robot = new Robot();
    private BufferedImage screenShot;
    private byte[] bytes;
    Dimension sSize = Toolkit.getDefaultToolkit().getScreenSize();
    public Capture() throws AWTException {

    }

    public void newCapture(){
        new Thread(() -> {
            try {
            while (true) {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    screenShot = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
                    ImageIO.write(screenShot, "jpg", baos);
                    bytes = baos.toByteArray();
                System.out.println("s");
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public BufferedImage getCapture() throws IOException {
        screenShot = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
        return screenShot;
    }

    public byte[] getBaos(){
        return bytes;
    }
}
