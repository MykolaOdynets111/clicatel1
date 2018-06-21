package dbManager;
import java.sql.*;

public class DBConnector {

    private static Connection connection = null;

    private DBConnector() {
    }

    private static void createCoinnestion(String env, String db){
        if (env.equalsIgnoreCase("integration")){
            env="int";
        }
        String creds = db+""+env;
        try {
            Class.forName("com.mysql.jdbc.Driver");
//            connection = DriverManager.getConnection(DBConnector.DB_URL, creds, creds);
        } catch (Exception e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
        }
    }


    private static Connection getConnection(String env, String db) {
        if (connection == null) {

        }
            return connection;
        }

}
