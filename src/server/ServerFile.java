package server;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class ServerFile {

    private Boolean isTrue = true;
    ServerSocket server;
    public void startServerFile() throws IOException {
        new Thread(() -> {
            try {
                isTrue = true;
                server = new ServerSocket(3347);
                Socket client = server.accept();
                DataInputStream dataInputStream = new DataInputStream(client.getInputStream());
                BufferedInputStream bufferedInputStream = new BufferedInputStream(client.getInputStream());
                int quan;
                String name;
                while (isTrue) {
                    name = dataInputStream.readUTF();
                    quan = dataInputStream.readInt();
                    System.out.println("Filequan" + quan);
                    byte [] buffer  = new byte [quan];
                    int offset = 0;
                    int bytesRead = 0;
                    while (offset < quan) {
                        bytesRead = bufferedInputStream.read(buffer, offset, quan - offset);
                        offset += bytesRead;
                    }
                    try (FileOutputStream fos = new FileOutputStream("C:\\Users\\nikst\\Downloads\\"+name)) {
                        fos.write(buffer);
                        fos.flush();
                    }
                    catch (Exception e) {
                        System.out.println("FILENOTWRITTEN");
                    }

                }
                server.close();
                System.out.println("fileClose");
            }
            catch (IOException | IllegalArgumentException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
