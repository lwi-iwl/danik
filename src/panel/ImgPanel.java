package panel;

import client.Board;

import javax.swing.*;

public class ImgPanel {
    private final JPanel imgPanel;

    public ImgPanel(Board board) {
        imgPanel = new JPanel();
        imgPanel.add(board);
    }

    public JPanel getPanel(){
        return imgPanel;
    }
}
