package panel;

import java.io.IOException;
import java.net.InetAddress;

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
