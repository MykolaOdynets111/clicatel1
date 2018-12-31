package java_server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class JavaHTTPServer{

    public static boolean IS_READY = false;
    private OutputStream os = null;
    private String input = null;
    public static String incommingMessage = "start";

    public static void startServer() {
        HttpServer server = null;
        try {
            server = HttpServer.create(new InetSocketAddress(8888), 0);
            server.createContext("/", new MyHandler());
            server.setExecutor(null); // creates a default executor
            server.start();

            while(true) {
                if(!KnockKnockProtocol.message.equals("test")) {
                    System.out.println("incommingMessage from while loop " + KnockKnockProtocol.message);
//                    KnockKnockProtocol.message = null;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
//            System.out.println("After output was written +" + incommingMessage);
            server.stop(1);
        }

    }

    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            BufferedReader in = new BufferedReader(new InputStreamReader(t.getRequestBody()));
            try {
                Thread.sleep(8000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            in.read()
//            System.out.println(in.lines().map(e -> e + "\n").collect(Collectors.toList()));
                String input = in.lines().map(e -> e + "\n").collect(Collectors.toList()).toString();
//                System.out.println(input);

            String response = "This is the response";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
            System.out.println("After output was written +" + input);
            KnockKnockProtocol.message = input;
        }
    }

}
