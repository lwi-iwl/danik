package panel;

import client.Board;
import client.ClientImg;
import server.ServerImg;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class StartManage {
    ClientImg client;
    public StartManage(ClientImg client){
        this.client = client;
    }
    public void start(Board board, ServerImg server) throws AWTException {
        JFrame jf = new JFrame("Viewer");
        Dimension sSize = Toolkit.getDefaultToolkit().getScreenSize();
        jf.setSize(Math.round(sSize.width), Math.round(sSize.height));
        jf.add(board);
        jf.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                    server.startServer();
                    client.setClientCommand("INFSTOP");
            }
        });
        jf.setDefaultCloseOperation(jf.EXIT_ON_CLOSE);
        jf.setVisible(true);
        jf.setResizable(false);
    }
}
