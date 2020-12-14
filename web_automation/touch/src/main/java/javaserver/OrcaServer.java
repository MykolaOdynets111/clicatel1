package javaserver;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import datamanager.jacksonschemas.orca.OrcaEvent;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.Assert;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class OrcaServer extends Server {
    public static volatile Map<String, Set<String>> orcaMessagesMap = new HashMap<>();
    public static volatile List<String> orcaMessages = new ArrayList<>();

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
                        server.createContext("/", new OrcaHandler());
                        server.setExecutor(null); // creates a default executor
                        server.start();
                        while (true) {
                            // for some reason without this wait inside loop the server is not stopping
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            if (!running) {
                                System.out.println("\n!!! will stop the server \n");
                                server.stop(0);
                                clientProcessingPool.shutdownNow();
                                try {
                                    clientProcessingPool.awaitTermination(8, TimeUnit.SECONDS);
                                } catch (Exception ignored) {
                                }
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

    private OrcaEvent convertStringResponseBodyToObject(String body) {
        for (String otherEvent : Arrays.asList("MEDIA", "NOTIFICATION", "TERMINATE", "OPT_OUT", "AUTH",
                "AUTH_STATUS", "TIMESLOT_PICKER", "PAYMENT_REQUEST", "PAYMENT_STATUS", "RICHLINK", "LIST_PICKER")) {
            if (body.contains(otherEvent)) {
                return new OrcaEvent();
            }
        }

        if ((body.charAt(0) + "").equals("[")) {
            body = body.replace("[", "");
            body = body.replace("]", "");
        }
        System.out.println("Incomming body :" + body);
        OrcaEvent orcaEvent = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            orcaEvent = mapper.readValue(body, OrcaEvent.class);
        } catch (IOException e) {
            Assert.fail(e.getMessage() + "\nIncorrect schema of response from Orca \n" + "Catched Body " + body);
        }

        return orcaEvent;
    }

    class OrcaHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange t) throws IOException {
            System.out.println("\n Inside handler \n");
            BufferedReader in = new BufferedReader(new InputStreamReader(t.getRequestBody()));
            String incomingBody = in.lines().map(e -> e + "\n").collect(Collectors.toList()).toString();
            System.out.println("\n Inside handler  incomingBody \n " + incomingBody);

            String encoding = "UTF-8";
            String response = "{\"responseMessage\": \"This is the response\"}";
            t.getResponseHeaders().set("Content-Type", "application/json; charset=" + encoding);
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();

            OrcaEvent orcaEvent = null;

            if (t.getRequestMethod().equalsIgnoreCase("post")) {
                orcaEvent = convertStringResponseBodyToObject(incomingBody);
                OrcaServer.orcaMessagesMap.clear();
                //for avoiding NullPointerException in the future
                if (orcaEvent.getContent() != null &&
                        orcaEvent.getContent().getEvent() != null &&
                        orcaEvent.getContent().getEvent().getText() != null &&
                        !orcaEvent.getContent().getEvent().getText().isEmpty()) {
                    Set<String> currentMessages = orcaMessagesMap.get(orcaEvent.getSourceId());
                    if(Objects.isNull(currentMessages))
                        currentMessages = new HashSet<>();
                    currentMessages.add(orcaEvent.getContent().getEvent().getText());
                    OrcaServer.orcaMessagesMap.put(orcaEvent.getSourceId(), currentMessages);
                    OrcaServer.orcaMessages.add(orcaEvent.getContent().getEvent().getText());
                }
            }
        }

    }

}

