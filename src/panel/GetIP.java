package panel;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class GetIP {

    String IP;
    public GetIP(){
        try (final DatagramSocket socket = new DatagramSocket()){
            socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
            InetAddress ip = socket.getLocalAddress();
            //InetAddress ip = InetAddress.getLocalHost();
            System.out.println("Current IP address : " + ip.getHostAddress());
            IP = ip.getHostAddress();
        }
        catch (IOException e){

        }
    }

    public String getIP(){
        return IP;
    };
}
