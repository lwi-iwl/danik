package panel;

import client.Board;
import server.ServerImg;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class NewDialog {
    private JDialog d;

    public NewDialog(ServerImg server, Board board) {
        JFrame f = new JFrame();
        d = new JDialog(f, "Запрос на управление", true);
        d.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // Already there
        d.setLayout(new FlowLayout());
        JButton yes = new JButton("Да");
        yes.addActionListener(e -> {
            server.startServ();
            d.setVisible(false);
        });

        JButton no = new JButton("Нет");
        no.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                server.stopServ();
                d.setVisible(false);
            }
        });


        JLabel savelabel = new JLabel("Подтвердить?");
        savelabel.setLocation(100, 100);
        d.add(savelabel);
        d.add(yes);
        d.add(no);
        d.setSize(300, 100);
        d.setLocation(500, 300);
    }
    public void setVisible() {
        d.setVisible(true);
    }

}
