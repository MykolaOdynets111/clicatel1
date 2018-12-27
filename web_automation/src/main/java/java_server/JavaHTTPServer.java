package java_server;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class JavaHTTPServer implements Runnable{

    static final File WEB_ROOT = new File(".");
    static final String DEFAULT_FILE = "index.html";
    static final String FILE_NOT_FOUND = "404.html";
    static final String METHOD_SOT_SUPPORTED = "not_supported.html";
    static final int PORT = 8888;

    static final boolean verbose = true;

    private Socket connect;

    public JavaHTTPServer(Socket c){
        connect = c;
    }

    public static void main(String[] args){
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server started + \n");

            while ((true)){
                JavaHTTPServer myServer = new JavaHTTPServer(serverSocket.accept());
                Thread thread = new Thread(myServer);
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        BufferedReader in = null;
        PrintWriter out = null;

        
    }
}
