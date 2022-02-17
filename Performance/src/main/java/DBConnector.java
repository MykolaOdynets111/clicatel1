import com.sun.istack.NotNull;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBConnector {
    private static Connection connection = null;
    private static String URL = "jdbc:mysql://chatd-db-primary-01.shared-stage.eu-west-1.aws.clickatell.com:4001/chatdesk_platform?characterEncoding=UTF-8&useUnicode=yes&autoReconnect=true&allowPublicKeyRetrieval=true";
    private static String user = "w.chatdesk_platform";
    private static String password = "uXEa&3cI2@";

    public DBConnector() {
    }

    private static void createConnection(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, user, password);
        } catch (Exception e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
        }
    }

    @NotNull
    private static  Connection getConnection() {
        if (connection == null) {
            createConnection();
        }
        return connection;
    }

    public static List<String> getListOfUnclosedChats(String query, String column) {
        ResultSet results = null;
        List<String> result = new ArrayList<>();
        try(Statement statement = getConnection().createStatement()) {
            statement.executeQuery(query);
            results = statement.getResultSet();
            while (results.next()) {
                result.add(results.getString(column));
            }
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }


    public static synchronized void updateDataInDB(String query){
        try(Statement statement = getConnection().createStatement()) {
            statement.executeUpdate(query);
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    public static void main(String[] args) {
        System.out.println(DBConnector.getListOfUnclosedChats("select * from chat where chat_status_id = 10  and tenant_id = '017e7d45188427df4d8a0181d1fc5g1b'", "id"));
    }
}
