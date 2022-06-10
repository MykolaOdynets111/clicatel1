package javaserver;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import drivermanager.ConfigManager;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public abstract class Server {

    public static final String INTERNAL_CI_IP = "172.31.16.120";
    public static volatile int SERVER_PORT = 5000;
    protected static boolean running = true;

    public static void stopServer() {
        running = false;
    }

    public static synchronized String getServerURL() {
        if (ConfigManager.isRemote()) {
            return "http://" + Server.INTERNAL_CI_IP + ":" + Server.SERVER_PORT;
        } else {
            // to provide local ngrok url
            return "https://56df-194-44-126-140.ngrok.io";
        }
    }

    public static synchronized void createServerURL() {
        SERVER_PORT += 1;
    }

    public void startServer(HttpHandler handler) {
        createServerURL();
        running = true;
        final ExecutorService clientProcessingPool = Executors.newFixedThreadPool(10);

        clientProcessingPool.execute(
                () -> {
                    HttpServer server = null;

                    try {
                        server = HttpServer.create(new InetSocketAddress(SERVER_PORT), 0);
                        System.out.println("Waiting for clients to connect...");
                        server.createContext("/", handler);
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


}
