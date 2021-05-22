package client;

import server.Capture;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.*;
import java.awt.*;

public class Board extends JPanel {

    private BufferedImage capture;
    Dimension sSize = Toolkit.getDefaultToolkit().getScreenSize();
    public Board() throws AWTException {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(400, 400));
    }

    public void setCapture(BufferedImage image){
        capture = image;
    }

    @Override
    public void paintComponent(Graphics g2) {
        super.paintComponent(g2);
        g2.drawImage(capture, 0, 0, null);
    }

}
