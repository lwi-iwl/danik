package panel;
import javax.swing.*;
import java.awt.*;

public class Start  extends JFrame{

    public Start() throws AWTException {
        Panel panel = new Panel();
        JFrame jf = new JFrame("For2D");
        jf.add(panel.getPanel());
        Dimension sSize = Toolkit.getDefaultToolkit().getScreenSize();
        jf.setSize(1000, 1000);
        jf.setDefaultCloseOperation(jf.EXIT_ON_CLOSE);
        jf.setVisible(true);
        jf.setResizable(false);
    }

    public static void main(String[] args) throws AWTException {
        Start start = new Start();
    }
}
