package java_server;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;


public class ServerJUNITTest {

    @BeforeClass
    public static void setUpServer(){
        new Server().startServer();
    }

    @Test
    public  void test(){
        System.out.println("!! Hello from main thread !!");
        for (int i = 0; i<10; i++){

            System.out.println("!! Hello from main thread !!  " +i + "\n");
            try{
                System.out.println("\n keys map from main method + " + Server.incomingRequests.get("test123_clt241044514").getMessage());
            }catch (java.lang.NullPointerException e){

            }

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Assert.assertTrue(true);
    }

//    @Test
//    public void test2(){
//        System.out.println("\n Starting second test \n");
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        for (int i = 0; i<10; i++){
//            System.out.println("!! Hello from main thread !!  " +i + "\n"+
//                    Server.incomingRequests);
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//        Assert.assertTrue(false);
//    }

}


