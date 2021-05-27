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
    JFrame jf;
    public StartManage(ClientImg client, Board board, ServerImg server, NewDialog dialog){
        this.client = client;
        jf = new JFrame("Viewer");
        Dimension sSize = Toolkit.getDefaultToolkit().getScreenSize();
        jf.setSize(Math.round(sSize.width), Math.round(sSize.height));
        jf.add(board);
        jf.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                client.setClientCommand("INFSTOP");
                try {
                    server.startServer(dialog);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                jf.dispose();
            }
        });
        jf.setResizable(false);
    }
    public void start() throws AWTException {
        jf.setVisible(true);
    }

    public void startManageExit(){
        jf.setVisible(false);
    }

}
