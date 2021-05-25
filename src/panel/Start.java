package panel;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Start  extends JFrame{

    public Start() throws AWTException {
        Panel panel = new Panel();
        JFrame jf = new JFrame("For2D");
        jf.add(panel.getPanel());
        Dimension sSize = Toolkit.getDefaultToolkit().getScreenSize();
        jf.setSize((int)Math.round(sSize.width*0.9), (int)Math.round(sSize.height*0.9));
        jf.setDefaultCloseOperation(jf.EXIT_ON_CLOSE);
        jf.setVisible(true);
        jf.setResizable(false);
    }

    public static void main(String[] args) throws AWTException {
        Start start = new Start();
    }
}
