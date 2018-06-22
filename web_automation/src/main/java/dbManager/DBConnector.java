package dbManager;
import driverManager.ConfigManager;
import org.testng.Assert;

import java.sql.*;

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

    public static boolean isConnectionEstabliished(String env, String platform){
        boolean result;
            try {
                getConnection(env, platform).getSchema();
                result = true;

            } catch (SQLException e) {
                e.printStackTrace();
                result = false;
            }
            connection=null;
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
        }
    }

//    public static void main(String args[]){
//        String a = DBConnector.getInvitationIdForCreatedUserFromMC2DB("demo", "aqatest_1@aqa.com");
//        DBConnector.closeConnection();
//    }
}
