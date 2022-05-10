package javaserver;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import datamanager.jacksonschemas.dotcontrol.MessageResponse;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.Assert;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DotControlServer extends Server {

    public static volatile Map<String, MessageResponse> dotControlIncomingRequests = new HashMap<>();
    public static volatile List<String> dotControlMessages = new ArrayList<>();

    public void startServer() {
        startServer(new DotControlHandler());
    }

    class DotControlHandler implements HttpHandler {

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

            MessageResponse incomingRequest = null;

            if (t.getRequestMethod().equalsIgnoreCase("post")) {
                incomingRequest = convertStringBotResponseBodyToObject(incomingBody);
                DotControlServer.dotControlIncomingRequests.clear();
                DotControlServer.dotControlIncomingRequests.put(incomingRequest.getClientId(), incomingRequest);
                if (incomingRequest.getMessage() != null && !incomingRequest.getMessage().isEmpty()) {
                    DotControlServer.dotControlMessages.add(incomingRequest.getMessage());
                }

            }

        }

        private MessageResponse convertStringBotResponseBodyToObject(String body) {
            if (body.contains("CHAT_SUMMARY")) {
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
                Assert.fail("Incorrect schema of response from .Control \n" + "Catched Body " + body);
            }

            return botMessage;
        }

    }
}
