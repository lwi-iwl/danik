package panel;
import client.Board;
import javax.swing.*;
import java.awt.*;


public class Start  extends JFrame{

    public Start() throws AWTException {
        Board board = new Board();
        Panel panel = new Panel(board);
        JFrame jf = new JFrame("BeamViewer");
        jf.add(panel.getPanel());
        Dimension sSize = Toolkit.getDefaultToolkit().getScreenSize();
        jf.setSize((int)Math.round((float)sSize.width/2), (int)Math.round((float)sSize.height/2));
        jf.setDefaultCloseOperation(jf.EXIT_ON_CLOSE);
        jf.setVisible(true);
        jf.setResizable(false);
    }

    public static void main(String[] args) throws AWTException {
        Start start = new Start();
    }
}
