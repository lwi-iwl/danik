package panel;

import client.Client;
import client.Board;
import server.Server;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Panel {
    private final JPanel panel;

    public Panel() throws AWTException {
        Dimension sSize = Toolkit.getDefaultToolkit().getScreenSize();
        Board board = new Board();
        Client client = new Client();
        Server server = new Server();
        panel = new JPanel();
        JButton clientButton = new JButton("Client");
        clientButton.setSize(sSize.width / 12, sSize.height / 40);
        clientButton.setLocation(0, 0);
        clientButton.setLayout(new BorderLayout());
        clientButton.addActionListener(e -> {
            try {
                client.startClient(board);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });


        JButton serverButton = new JButton("Server");
        serverButton.setSize(sSize.width / 12, sSize.height / 40);
        serverButton.setLocation(0, 10);
        serverButton.setLayout(new BorderLayout());
        panel.add(serverButton);
        serverButton.addActionListener(e -> {
            try {
                server.startServer();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        panel.add(board);
        panel.add(clientButton);
    }

    public JPanel getPanel(){
        return panel;
    }
}
