package client;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class Input {
    InputStream is;
    public void startPaint(Board board){
        new Thread(() -> {
            BufferedImage newBi;
                while (true) {
                    try {
                        newBi = ImageIO.read(is);
                    board.setCapture(newBi);
                        if (newBi!=null)
                    board.repaint();
                    System.out.println("paint");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        }).start();
    }


    public void setInputStream(InputStream is){
        System.out.println("set");
        this.is = is;
    }
}
