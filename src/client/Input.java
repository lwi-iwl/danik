package client;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class Input {
    InputStream is;
    public void startPaint(Board board){
        new Thread(() -> {
                while (true) {
                    BufferedImage newBi = null;
                    try {
                        newBi = ImageIO.read(is);
                    board.setCapture(newBi);
                    board.repaint();
                    System.out.println("input");
                    Thread.sleep(100);
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        }).start();
    }


    public void setInputStream(InputStream is){
        this.is = is;
    }
}
