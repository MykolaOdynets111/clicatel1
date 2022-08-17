package javaserver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import datamanager.jacksonschemas.orca.OrcaEvent;

import org.testng.Assert;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.*;
import java.util.stream.Collectors;

public class OrcaServer extends Server {
    public static volatile Map<String, Set<String>> orcaMessagesMap = new HashMap<>();
    public static volatile List<String> orcaMessages = new ArrayList<>();
    public static volatile List<OrcaEvent> orcaEvents = new ArrayList<>();

    public void startServer() {
        startServer(new OrcaHandler());
    }

    class OrcaHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange t) throws IOException {
            System.out.println("\n Inside Orca handler \n");
            BufferedReader in = new BufferedReader(new InputStreamReader(t.getRequestBody()));
            String incomingBody = in.lines().map(e -> e + "\n").collect(Collectors.toList()).toString();
            System.out.println("\n Inside Orca handler  incomingBody \n " + incomingBody);

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
                orcaEvents.add(orcaEvent);
                if (orcaEvent.getContent() != null &&
                        orcaEvent.getContent().getEvent() != null &&
                        orcaEvent.getContent().getEvent().getText() != null &&
                        !orcaEvent.getContent().getEvent().getText().isEmpty()) {
                    Set<String> currentMessages = orcaMessagesMap.get(orcaEvent.getSourceId());
                    if (Objects.isNull(currentMessages))
                        currentMessages = new HashSet<>();
                    currentMessages.add(orcaEvent.getContent().getEvent().getText());
                    OrcaServer.orcaMessagesMap.put(orcaEvent.getSourceId(), currentMessages);
                    OrcaServer.orcaMessages.add(orcaEvent.getContent().getEvent().getText());
                }
            }
        }

        private OrcaEvent convertStringResponseBodyToObject(String body) {
            for (String otherEvent : Arrays.asList("MEDIA", "NOTIFICATION", "TERMINATE", "OPT_OUT", "AUTH",
                    "AUTH_STATUS", "TIMESLOT_PICKER", "PAYMENT_REQUEST", "PAYMENT_STATUS", "RICHLINK", "LIST_PICKER")) {
                if (body.contains(otherEvent) && !body.contains("STRUCTURED_TEXT_NOTIFICATION")) {
                    return new OrcaEvent();
                }
            }

            if ((body.charAt(0) + "").equals("[")) {
                body = body.replace("[", "");
                body = body.replace("]", "");
            }
            System.out.println("Orca Incoming body :" + body);
            OrcaEvent orcaEvent = null;
            ObjectMapper mapper = new ObjectMapper();
            try {
                orcaEvent = mapper.readValue(body, OrcaEvent.class);
            } catch (IOException e) {
                Assert.fail(e.getMessage() + "\nIncorrect schema of response from Orca \n" + "Catched Body " + body);
            }

            return orcaEvent;
        }

    }

}

