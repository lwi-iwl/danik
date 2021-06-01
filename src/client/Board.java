package client;

import panel.StartManage;
import server.Capture;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Board extends JPanel {
    float multiplier = 1;
    int width = 1;
    int height = 1;
    private Capture cap = new Capture();
    private BufferedImage capture;
    Dimension sSize = Toolkit.getDefaultToolkit().getScreenSize();
    public Board() throws AWTException {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        //setPreferredSize(new Dimension((int)Math.round(height), (int)Math.round(height)));
    }

    public void setCapture(BufferedImage image){
        capture = image;
    }

    @Override
    public void paintComponent(Graphics g2) {
        super.paintComponent(g2);
        if (capture!=null)
            g2.drawImage(capture, 0, 0, Math.round(capture.getWidth()*multiplier), Math.round(capture.getHeight()*multiplier), 0,0, capture.getWidth(), capture.getHeight(), null);
    }

    public void setMultiplier(BufferedImage image, StartManage startManage){
        double x = sSize.width/(double)image.getWidth()*0.9;
        double y = sSize.height/(double)image.getHeight()*0.9;
        width = (int)Math.round(image.getWidth()*0.9)+20;
        height = (int)Math.round(image.getHeight()*0.9)+40;
        multiplier = (float)Math.min(x, y);
        startManage.setSize(width, height);
    }

}
