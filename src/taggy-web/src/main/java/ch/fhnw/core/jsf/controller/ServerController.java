package ch.fhnw.core.jsf.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

@Scope(value = "session")
@Component(value = "serverController")
public class ServerController {

    public boolean pingHost(String host, int port, int timeout) {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(host, port), timeout);
            return true;
        } catch (IOException e) {
            return false; // Either timeout or unreachable or failed DNS lookup.
        }
    }

    public String pingHostColor(String host, int port, int timeout){
        boolean serverOnline = pingHost(host, port, timeout);
        if (serverOnline){ return "green";}
        else return "red";
    }
    public String callErrorPage(String host, int port, int timeout){
        boolean serverOnline = pingHost(host, port, timeout);
        if (!serverOnline){ return "serverStatus";}
        return "";
    }
}
