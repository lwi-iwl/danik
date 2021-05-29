package server;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Capture {
    public Capture() throws AWTException {

    }
    private Boolean isTrue = true;
    private byte[] bytes;
    public void newCapture(){
        new Thread(() -> {
            Robot robot = null;
            try {
                robot = new Robot();
            BufferedImage screenShot;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.setUseCache(false);
            while (isTrue) {
                try {
                    baos.reset();
                    screenShot = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
                    ImageIO.write(screenShot, "jpeg", baos);
                    bytes = baos.toByteArray();
                    System.out.println("newcapture");
                }
                catch (IOException | NullPointerException e) {
                    e.printStackTrace();
                }
            }
            } catch (AWTException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public byte[] getBaos(){
        return bytes;
    }

    public void setClose(Boolean isTrue){
        this.isTrue = isTrue;
    }
}
