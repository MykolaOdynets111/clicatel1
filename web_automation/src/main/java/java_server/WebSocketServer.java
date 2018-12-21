package java_server;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;


@ServerEndpoint("/automationcallback")
public class WebSocketServer {

    @OnMessage
    public void onMessage(Session session, String message) {
        System.out.println("onMessage::From=" + session.getId() + " Message=" + message);
    }

    @OnError
    public void onError(Session session) {
        System.out.println("onMessage::From=" + session.getId() + " Message= + connection session error");
    }
}
