package panel;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class GetIP {

    String IP;
    public GetIP(){
        try {
            InetAddress ip = InetAddress.getLocalHost();
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
