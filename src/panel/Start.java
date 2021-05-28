package panel;
import client.Board;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;


public class Start  extends JFrame{

    public Start() throws AWTException {
        JFrame jf = new JFrame("BeamViewer");
        Board board = new Board();
        Panel panel = new Panel(board, jf);
        jf.add(panel.getPanel());
        Dimension sSize = Toolkit.getDefaultToolkit().getScreenSize();
        jf.setSize((int)Math.round((float)sSize.width/2), (int)Math.round((float)sSize.height/3));
        jf.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                panel.getServer().infStopServ();
                System.exit(0);
            }
        });
        jf.setVisible(true);
        jf.setResizable(false);
    }

    public static void main(String[] args) throws AWTException {
        Start start = new Start();
    }
}
