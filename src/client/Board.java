package client;

import server.Capture;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Board extends JPanel {
    private Capture cap = new Capture();
    private BufferedImage capture;
    Dimension sSize = Toolkit.getDefaultToolkit().getScreenSize();
    public Board() throws AWTException {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension((int)Math.round(sSize.width*0.8), (int)Math.round(sSize.height*0.8)));
    }

    public void setCapture(BufferedImage image){
        capture = image;
    }

    @Override
    public void paintComponent(Graphics g2) {
        super.paintComponent(g2);
        if (capture!=null)
            g2.drawImage(capture, 0, 0, (int)Math.round(sSize.width*0.8), (int)Math.round(sSize.height*0.8), 0,0, capture.getWidth(), capture.getHeight(), null);
    }

}
