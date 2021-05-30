package client;

import java.awt.event.*;
import java.awt.im.InputContext;
import java.io.*;
import java.net.Socket;
import java.nio.file.Files;

public class ClientFile {
    BufferedOutputStream bufferedOutputStream;
    DataOutputStream dataOutputStream;
    public void startClientFile(Board board, String host) throws IOException {
        Socket socket = new Socket(host, 3347);
        bufferedOutputStream = new BufferedOutputStream(socket.getOutputStream());
        dataOutputStream = new DataOutputStream(socket.getOutputStream());
    }

    public void dropFile(File file) throws IOException {
        byte[] fileContent = Files.readAllBytes(file.toPath());
        dataOutputStream.writeUTF(file.getName());
        dataOutputStream.writeInt(fileContent.length);
        dataOutputStream.write(fileContent);
    }
}
