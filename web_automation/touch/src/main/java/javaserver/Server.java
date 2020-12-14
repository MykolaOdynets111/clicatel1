package javaserver;

import drivermanager.ConfigManager;

public class Server {

    public static final String INTERNAL_CI_IP = "172.31.16.120";
    public static volatile int SERVER_PORT = 5000;
    protected static boolean running = true;

    public static void stopServer(){
        running = false;
    }

    public static synchronized String getServerURL(){
        if(ConfigManager.isRemote()){
            return "http://" + Server.INTERNAL_CI_IP + ":" + Server.SERVER_PORT;
        }else{
            // to provide local ngrok url
            return "https://4f15b17a0b8a.ngrok.io";
        }
    }

    public static synchronized void createServerURL(){
        SERVER_PORT += 1;
    }



}
