package java_server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import dataManager.jackson_schemas.dot_control.BotMessage;
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

    public static Map<String, BotMessage> incomingRequests = new HashMap<>();

    public void startServer() {
        final ExecutorService clientProcessingPool = Executors.newFixedThreadPool(10);

        clientProcessingPool.execute(
                () -> {
                    HttpServer server = null;

                    try {
                        server = HttpServer.create(new InetSocketAddress(8888), 0);
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

            BotMessage incomingRequest = convertStringBotResponseBodyToObject(incomingBody);
//            System.out.println("\n\nincomingRequest +" + incomingRequest + " + clientid = " + incomingRequest.getClientId());

            if (Server.incomingRequests.isEmpty() || !Server.incomingRequests.containsKey(incomingRequest.getClientId())) {
                System.out.println("?? Map is empty ?? \n");
                Server.incomingRequests.put(incomingRequest.getClientId(), incomingRequest);
//                System.out.println("After output was written  " + Server.incomingRequests.get(incomingRequest.getClientId()).getMessage());
            }

        }

        private BotMessage convertStringBotResponseBodyToObject(String body) {
            if ((body.charAt(0) + "").equals("[")) {
                body = body.replace("[", "");
                body = body.replace("]", "");
            }
            BotMessage botMessage = null;
            ObjectMapper mapper = new ObjectMapper();
            try {
                botMessage = mapper.readValue(body, BotMessage.class);
            } catch (IOException e) {
                Assert.assertTrue(false, "Incorrect schema of response from .Control \n" + body);
            }

            return botMessage;
        }

    }
}
