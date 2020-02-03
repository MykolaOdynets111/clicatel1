package javaserver;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import datamanager.jacksonschemas.dotcontrol.MessageResponse;
import drivermanager.ConfigManager;
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
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Server {

    public static final String INTERNAL_CI_IP = "172.31.16.120";
    public static volatile int SERVER_PORT = 5000;
    private static boolean running = true;
    public static volatile Map<String, MessageResponse> incomingRequests = new HashMap<>();

    public static void stopServer(){
        running = false;
    }

    public static synchronized String getServerURL(){
        if(ConfigManager.isRemote()){
            return "http://" + Server.INTERNAL_CI_IP + ":" + Server.SERVER_PORT;
        }else{
            // to provide local ngrok url
            return "https://9fb9cd51.ngrok.io";
        }
    }

    public static synchronized void createServerURL(){
        SERVER_PORT += 1;
    }

    public void startServer() {
        createServerURL();
        running = true;
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
                            // for some reason without this wait inside loop the server is not stopping
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            if(!running){
                                System.out.println("\n!!! will stop the server \n");
                                server.stop(0);
                                clientProcessingPool.shutdownNow();
                                try {
                                    clientProcessingPool.awaitTermination(8, TimeUnit.SECONDS);
                                } catch (Exception ignored) {}
                                break;
                            }
                        }

                    } catch (IOException e) {
                        System.err.println("Unable to process client request");
                        e.printStackTrace();
                    } finally {
                        server.stop(1);
                        clientProcessingPool.shutdownNow();
                    }
                }
        );
    }


    class MyHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange t) throws IOException {
            System.out.println("\n Inside handler \n");
            BufferedReader in = new BufferedReader(new InputStreamReader(t.getRequestBody()));
            String incomingBody = in.lines().map(e -> e + "\n").collect(Collectors.toList()).toString();
            System.out.println("\n Inside handler  incomingBody \n "+ incomingBody);

            String encoding = "UTF-8";
            String response = "{\"responseMessage\": \"This is the response\"}";
            t.getResponseHeaders().set("Content-Type", "application/json; charset=" + encoding);
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();

            MessageResponse incomingRequest = null;

            if(t.getRequestMethod().equalsIgnoreCase("post")){
                incomingRequest = convertStringBotResponseBodyToObject(incomingBody);
                Server.incomingRequests.clear();
                Server.incomingRequests.put(incomingRequest.getClientId(), incomingRequest);
            }

        }

        private MessageResponse convertStringBotResponseBodyToObject(String body) {
            if(body.contains("CHAT_SUMMARY")){
                return new MessageResponse();
            }
            if ((body.charAt(0) + "").equals("[")) {
                body = body.replace("[", "");
                body = body.replace("]", "");
            }
            System.out.println("Incomming body :" + body);
            MessageResponse botMessage = null;
            ObjectMapper mapper = new ObjectMapper();
            try {
                botMessage = mapper.readValue(body, MessageResponse.class);
            } catch (IOException e) {
                Assert.fail("Incorrect schema of response from .Control \n" +"Catched Body "+ body);
            }

            return botMessage;
        }

    }
}
