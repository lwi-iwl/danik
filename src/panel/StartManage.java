package panel;

import client.Board;
import client.ClientImg;
import server.ServerImg;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class StartManage {
    ClientImg client;
    JFrame jf;
    public StartManage(ClientImg client, Board board){
        this.client = client;
        jf = new JFrame("Viewer");
        Dimension sSize = Toolkit.getDefaultToolkit().getScreenSize();
        jf.setSize(Math.round(sSize.width), Math.round(sSize.height));
        jf.add(board);
        jf.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                client.setClientCommand("INFSTOP");
                jf.dispose();
            }
        });
        jf.setResizable(false);
        jf.setDropTarget(new DropTarget() {
            public synchronized void drop(DropTargetDropEvent evt) {
                try {
                    evt.acceptDrop(DnDConstants.ACTION_COPY);
                    List<File> droppedFiles = (List<File>) evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                    for (File file : droppedFiles) {
                        client.getClientFile().dropFile(file);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
    public void start() throws AWTException {
        jf.setVisible(true);
    }

    public void setSize(int width, int height){
        jf.setSize(width, height);
    }

    public void startManageExit(){
        jf.setVisible(false);
    }

}
