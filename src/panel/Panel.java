package panel;

import client.ClientImg;
import client.Board;
import server.ServerImg;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class Panel {
    private final JPanel panel;
    private ServerImg server = new ServerImg();

    public Panel(Board board, JFrame start) {
        ClientImg client = new ClientImg();
        Dimension sSize = Toolkit.getDefaultToolkit().getScreenSize();
        board.setFocusable(true);
        board.requestFocusInWindow();
        NewDialog dialog = new NewDialog(server, board);
        StartManage startManage = new StartManage(client, board);
        panel = new JPanel();
        panel.setLayout(null);

        JLabel ipText = new JLabel(" Ваш IP:" + new GetIP().getIP());
        ipText.setFont(new Font("Serif", Font.PLAIN, 25));
        ipText.setBackground(new Color(0, 191, 255));
        ipText.setSize(sSize.width / 2,40);
        ipText.setOpaque(true);
        ipText.setLayout(null);

        panel.add(ipText);

        try {
            server.startServer(dialog);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }


        JTextField textField = new JTextField(" Введите IP");
        textField.setFont(new Font("Serif", Font.PLAIN, 25));
        textField.setSize(sSize.width / 2,40);
        textField.setLocation(0, 70);
        textField.setOpaque(true);
        textField.setLayout(null);
        textField.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                textField.setText("");
            }
        });
        panel.add(textField);


        JButton clientButton = new JButton("Управление");
        clientButton.setFont(new Font("Serif", Font.PLAIN, 25));
        clientButton.setSize(sSize.width / 8,40);
        clientButton.setLocation(0, 140);
        clientButton.setBackground(new Color(0, 191, 255));
        clientButton.setLayout(new BorderLayout());
        clientButton.setLayout(null);
        clientButton.addActionListener(e -> {
            try {
                client.startClient(board, server, startManage, dialog, textField.getText(), start);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        panel.add(clientButton);
    }

    public JPanel getPanel(){
        return panel;
    }

    public ServerImg getServer(){
        return server;
    }


}