//package java_server;
//
//import com.sun.net.httpserver.HttpExchange;
//import com.sun.net.httpserver.HttpHandler;
//import com.sun.net.httpserver.HttpServer;
//import org.apache.catalina.Context;
//import org.apache.catalina.LifecycleException;
//import org.apache.catalina.core.StandardContext;
//import org.apache.catalina.startup.Tomcat;
//import org.apache.tomcat.util.descriptor.web.FilterDef;
//import org.apache.tomcat.util.descriptor.web.FilterMap;
//
//import java.io.File;
//import java.io.IOException;
//import java.io.OutputStream;
//import java.net.InetSocketAddress;
//
//public class TomcatHelper {
//
//
//    private Tomcat tomcat = null;
//
//    // uncomment for live test
//    // public static void main(String[] args) throws LifecycleException, ServletException, URISyntaxException, IOException {
//    // startTomcat();
//    // }
//
//    public void startTomcat() throws LifecycleException {
//        tomcat = new Tomcat();
//        tomcat.setPort(8083);
//        tomcat.setHostname("localhost");
//        String appBase = ".";
//        tomcat.getHost().setAppBase(appBase);
//
//        File docBase = new File(System.getProperty("java.io.tmpdir"));
//        Context context = tomcat.addContext("", docBase.getAbsolutePath());
//
//        // add a servlet
//        Class servletClass = MyServlet.class;
//        Tomcat.addServlet(context, servletClass.getSimpleName(), servletClass.getName());
//        context.addServletMappingDecoded("/my-servlet/*", servletClass.getSimpleName());
//
//        // add a filter and filterMapping
//        Class filterClass = MyFilter.class;
//        FilterDef myFilterDef = new FilterDef();
//        myFilterDef.setFilterClass(filterClass.getName());
//        myFilterDef.setFilterName(filterClass.getSimpleName());
//        context.addFilterDef(myFilterDef);
//
//        FilterMap myFilterMap = new FilterMap();
//        myFilterMap.setFilterName(filterClass.getSimpleName());
//        myFilterMap.addURLPattern("/my-servlet/*");
//        context.addFilterMap(myFilterMap);
//
//        tomcat.start();
//        // uncomment for live test
//        // tomcat
//        // .getServer()
//        // .await();
//    }
//
//    public void stopTomcat() throws LifecycleException {
//        tomcat.stop();
//        tomcat.destroy();
//    }
//
//    static class MyHandler implements HttpHandler {
//        @Override
//        public void handle(HttpExchange t) throws IOException {
//            String response = "This is the response";
//            t.sendResponseHeaders(200, response.length());
//            OutputStream os = t.getResponseBody();
//            os.write(response.getBytes());
//            os.close();
//        }
//    }
//
//    public static void main(String[] args) {
////        HttpServer server = null;
////        try {
////            server = HttpServer.create(new InetSocketAddress(8000), 0);
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
////        server.createContext("/test", new MyHandler());
////        server.setExecutor(null); // creates a default executor
////        server.start();
//        TomcatHelper tomcatHelper1 = new TomcatHelper();
//        try {
//            tomcatHelper1.startTomcat();
//        } catch (LifecycleException e) {
//            e.printStackTrace();
//        }
//        int a = 2;
//    }
//}
