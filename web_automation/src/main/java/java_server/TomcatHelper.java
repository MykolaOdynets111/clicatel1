//package java_server;
//
//import org.apache.catalina.Context;
//import org.apache.catalina.startup.Tomcat;
//
//import java.io.File;
//
//public class TomcatHelper {
//
//    private Tomcat tomcat;
//
//    public Tomcat getTomcat(){
//        tomcat = new Tomcat();
//        tomcat.setPort(8080);
//        tomcat.setHostname("localhost");
//        String appBase = ".";
//        tomcat.getHost().setAppBase(appBase);
//        File docBase = new File(System.getProperty("java.io.tmpdir"));
//        Context context = tomcat.addContext("", docBase.getAbsolutePath());
//        return tomcat;
//    }
//
//    public static void main
//}
