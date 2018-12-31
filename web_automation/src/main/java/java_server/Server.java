package java_server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import dataManager.jackson_schemas.dot_control.BotMessageResponse;
import driverManager.ConfigManager;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.Assert;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Server {

    public static final String INTERNAL_CI_IP = "172.31.16.120";
    public static final int SERVER_PORT = 8888;

    public static Map<String, BotMessageResponse> incomingRequests = new HashMap<>();

    public static String getServerURL(){
        if(ConfigManager.isRemote()){
            return Server.INTERNAL_CI_IP + ":" + Server.SERVER_PORT;
        }else{
            // to provide local ngrok url
            return "http://89e3ef8d.ngrok.io";
        }
    }

    public void startServer() {
        final ExecutorService clientProcessingPool = Executors.newFixedThreadPool(10);

        clientProcessingPool.execute(
                () -> {
                    HttpServer server = null;

                    try {
                        server = HttpServer.create(new InetSocketAddress(SERVER_PORT), 0);
                        System.out.println("Waiting for clients to connect...");
                        server.createContext("/", new MyHandler());
                        server.setExecutor(null); // creates a default executor
                        server.start();
                        while (true) {
                        }

                    } catch (IOException e) {
                        System.err.println("Unable to process client request");
                        e.printStackTrace();
                    } finally {
                        server.stop(1);
                    }
                }
        );
    }


    class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
                BufferedReader in = new BufferedReader(new InputStreamReader(t.getRequestBody()));
                String incomingBody = in.lines().map(e -> e + "\n").collect(Collectors.toList()).toString();


                String response = "This is the response";
                t.sendResponseHeaders(200, response.length());
                OutputStream os = t.getResponseBody();
                os.write(response.getBytes());
                os.close();

                BotMessageResponse incomingRequest = convertStringBotResponseBodyToObject(incomingBody);
//            System.out.println("\n\nincomingRequest +" + incomingRequest + " + clientid = " + incomingRequest.getClientId());

                if (Server.incomingRequests.isEmpty() || !Server.incomingRequests.containsKey(incomingRequest.getClientId())) {
                    System.out.println("?? Map is empty ?? \n");
                    Server.incomingRequests.put(incomingRequest.getClientId(), incomingRequest);
//                System.out.println("After output was written  " + Server.incomingRequests.get(incomingRequest.getClientId()).getMessage());
                }
//            }

        }

        private BotMessageResponse convertStringBotResponseBodyToObject(String body) {
            if ((body.charAt(0) + "").equals("[")) {
                body = body.replace("[", "");
                body = body.replace("]", "");
            }
            BotMessageResponse botMessage = null;
            ObjectMapper mapper = new ObjectMapper();
            try {
                botMessage = mapper.readValue(body, BotMessageResponse.class);
            } catch (IOException e) {
                Assert.assertTrue(false, "Incorrect schema of response from .Control \n" + body);
            }

            return botMessage;
        }

    }
}
