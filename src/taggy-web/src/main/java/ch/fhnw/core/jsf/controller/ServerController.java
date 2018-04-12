package ch.fhnw.core.jsf.controller;


import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

@Scope(value = "request")
@Component(value = "serverController")
public class ServerController {

    private boolean isPortAvailable(String host, int port, int timeout) {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(host, port), timeout);
            return true;
        } catch (IOException e) {
            return false; // Either timeout or unreachable or failed DNS lookup.
        }
    }

    public String isPortAvilableColor(String host, int port, int timeout){
        boolean serverOnline = isPortAvailable(host, port, timeout);
        if (serverOnline){ return "green";}
        else return "red";
    }
}
