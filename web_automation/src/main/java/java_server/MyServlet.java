//package java_server;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//
//@WebServlet(name = "com.baeldung.tomcat.programmatic.MyServlet", urlPatterns = { "/my-servlet" })
//public class MyServlet extends HttpServlet {
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//        resp.setStatus(HttpServletResponse.SC_OK);
//        resp.getWriter().write("test");
//        resp.getWriter().flush();
//        resp.getWriter().close();
//    }
//}
//
