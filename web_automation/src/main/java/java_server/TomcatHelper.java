package java_server;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;

import java.io.File;

public class TomcatHelper {

    private Tomcat tomcat;

    public Tomcat getTomcat(){
        tomcat = new Tomcat();
        tomcat.setPort(8080);
        tomcat.setHostname("localhost");
        String appBase = ".";
        tomcat.getHost().setAppBase(appBase);
        File docBase = new File(System.getProperty("java.io.tmpdir"));
        Context context = tomcat.addContext("", docBase.getAbsolutePath());
        return tomcat;
    }

    public static void main(String[] args){
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);
        tomcat.setHostname("localhost");
//        File appDir = new File(System.getProperty("user.dir")+"/src/main/java/java_server/");
//        // app dir is relative to server home
//        StandardContext ctx = (StandardContext) tomcat.addWebapp(
//                null, "/automationcallback", appDir.getAbsolutePath());
        String appBase = ".";
        tomcat.getHost().setAppBase(appBase);
        File docBase = new File(System.getProperty("java.io.tmpdir"));
        tomcat.addContext("", docBase.getAbsolutePath());
        try {
            tomcat.start();
        } catch (LifecycleException e) {
            e.printStackTrace();
        }
        tomcat.getServer().await();
        int a =2;
    }
}
