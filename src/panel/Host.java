package panel;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Host {

    public Host() throws IOException {
        checkHosts("192.168.0");
    }
    
    public void checkHosts(String subnet) throws IOException {
        int timeout=1000;
        for (int i=1;i<255;i++){
            String host=subnet + "." + i;
            if (InetAddress.getByName(host).isReachable(timeout)){
                System.out.println(host + " is reachable");
            }
        }
    }

}
