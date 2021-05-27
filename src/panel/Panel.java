package panel;

import client.ClientImg;
import client.Board;
import server.ServerImg;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Panel {
    private final JPanel panel;
    public Panel(Board board) throws AWTException {
        Dimension sSize = Toolkit.getDefaultToolkit().getScreenSize();
        board.setFocusable(true);
        board.requestFocusInWindow();
        ClientImg client = new ClientImg();
        ServerImg server = new ServerImg();
        NewDialog dialog = new NewDialog(server, board);

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
            server.startServer(dialog);
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
                client.startClient(board);
                server.infStopServ();
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

}
