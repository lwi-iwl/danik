package panel;

import client.ClientImg;
import client.Board;
import server.ServerImg;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.ConnectException;

public class Panel {
    private final JPanel panel;
    ClientImg client = new ClientImg();
    ServerImg server = new ServerImg();

    public Panel(Board board) throws AWTException {
        Dimension sSize = Toolkit.getDefaultToolkit().getScreenSize();
        board.setFocusable(true);
        board.requestFocusInWindow();
        NewDialog dialog = new NewDialog(server, board);
        StartManage startManage = new StartManage(client);
        panel = new JPanel();
        panel.setLayout(null);

        JLabel ipText = new JLabel("Ваш IP:" + new GetIP().getIP());
        ipText.setFont(new Font("Serif", Font.PLAIN, 25));
        ipText.setBackground(new Color(0, 191, 255));
        ipText.setSize(sSize.width / 2,40);
        ipText.setOpaque(true);
        ipText.setLayout(null);
        panel.add(ipText);

        try {
            server.formThread(dialog);
            server.startServer();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

        JButton clientButton = new JButton("Управление");
        clientButton.setSize(sSize.width / 12, sSize.height / 40);
        clientButton.setLocation(0, 100);
        clientButton.setBackground(new Color(0, 191, 255));
        clientButton.setLayout(new BorderLayout());
        clientButton.setLayout(null);
        clientButton.addActionListener(e -> {
            try {
                client.startClient(board, server, startManage);
                //client.startUDPClient(board);
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

    public ClientImg getClient(){
        return client;
    }

}
