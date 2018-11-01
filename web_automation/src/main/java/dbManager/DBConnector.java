package dbManager;
import org.testng.Assert;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DBConnector {

    private static Connection connection = null;

    private DBConnector() {
    }

    private static void createConnection(String env, String platform){
        DBProperties dbProperties = DBProperties.getPropertiesFor(env, platform);
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(dbProperties.getURL(), dbProperties.getUser(), dbProperties.getPass());
        } catch (Exception e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
        }
    }


    private static Connection getConnection(String env, String platform) {
        if (connection == null) {
            createConnection(env, platform);
            if(connection == null){
                Assert.assertTrue(false, "DB connection was not established");
            }
        }
            return connection;
        }

    public static String getInvitationIdForCreatedUserFromMC2DB(String env, String userEmail) {
        String tableName = DBProperties.getPropertiesFor(env,"mc2").getDBName();
        String query = "SELECT * FROM "+tableName+".invitation where email=\""+userEmail+"\";";
        Statement statement = null;
        ResultSet results = null;
        String id = null;
        try {
            statement = getConnection(env, "mc2").createStatement();
            statement.executeQuery(query);
            results = statement.getResultSet();
            results.next();
            id = results.getString("id");
            statement.close();
            DBConnector.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }


    public static boolean isConnectionEstablished(String env, String platform){
        boolean result;
            try {
                getConnection(env, platform).getSchema();
                result = true;

            } catch (SQLException e) {
                e.printStackTrace();
                result = false;
            }
            closeConnection();
        return result;
    }

    public static void closeConnection(){
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            connection = null;
        }
    }


    public static String getAccountActivationIdFromMC2DB(String env) {
        String tableName = DBProperties.getPropertiesFor(env,"mc2").getDBName();
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        localDateTime = localDateTime.minusHours(3);
        String datetime = localDateTime.format(formatter);
        String query = "SELECT * FROM "+tableName+".account_activation where created_date > '" + datetime + "';";
        Statement statement = null;
        ResultSet results = null;
        String id = null;
        try {
            statement = getConnection(env, "mc2").createStatement();
            statement.executeQuery(query);
            results = statement.getResultSet();
            results.next();
            id = results.getString("id");
            statement.close();
            DBConnector.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

//    public static void main(String args[]){
//        String a = DBConnector.getAccountActivationIdFromMC2DB("testing");
//        DBConnector.closeConnection();
//    }
}
