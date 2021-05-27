package panel;

import client.Board;

import javax.swing.*;
import java.awt.*;

public class StartManage {
    public void start(Board board) throws AWTException {
        JFrame jf = new JFrame("BeamViewer");
        Dimension sSize = Toolkit.getDefaultToolkit().getScreenSize();
        jf.setSize(Math.round(sSize.width), Math.round(sSize.height));
        jf.add(board);
        jf.setDefaultCloseOperation(jf.EXIT_ON_CLOSE);
        jf.setVisible(true);
        jf.setResizable(false);
    }
}
