package dbmanager;
import org.testng.Assert;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

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


    public static String getAccountActivationIdFromMC2DB(String env, String accountName) {
        String tableName = DBProperties.getPropertiesFor(env,"mc2").getDBName();
        String accountId = getAccountIdFromMC2DB(env, accountName);

        String query = "SELECT * FROM "+tableName+".account_activation where account_id = '"+accountId+"';";
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

    public static String getAccountIdFromMC2DB(String env, String accountName) {
        String tableName = DBProperties.getPropertiesFor(env,"mc2").getDBName();
        String accountId = "";

        String query = "SELECT * FROM "+ tableName +".account where name = '" + accountName + "';";
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

    public static boolean isAgentCreatedInDB(String env, String agentEmail) {
        String tableName = DBProperties.getPropertiesFor(env,"touch").getDBName();
        String query = "SELECT * FROM "+tableName+".agent where agent_email='"+agentEmail+"';";
        Statement statement = null;
        ResultSet results = null;
        boolean isAgentPresent = false;
        try {
            for(int i = 0; i<20; i++){
                statement = getConnection(env, "touch").createStatement();
                statement.executeQuery(query);
                results = statement.getResultSet();
                isAgentPresent = results.next();
                if(isAgentPresent){
                    statement.close();
                    DBConnector.closeConnection();
                    break;
                }
                else {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                statement.close();
                DBConnector.closeConnection();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isAgentPresent;
    }

    public static String getClientProfileID(String env, String clientID, String type, int isTenantProfile) {
        String tableName = DBProperties.getPropertiesFor(env,"touch").getDBName();

        String query = "SELECT * FROM "+ tableName +".client_profile where client_id='"+clientID+"' " +
                "and is_tenant_profile="+isTenantProfile+" and type = '"+type+"';";
        Statement statement = null;
        ResultSet results = null;
        String id = null;
        try {
            statement = getConnection(env, "touch").createStatement();
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

    public static String getLinkedClientProfileID(String env, String clientID) {
        String tableName = DBProperties.getPropertiesFor(env,"touch").getDBName();

        String query = "SELECT * FROM "+ tableName +".client_profile where client_id='"+clientID+"' ";
        Statement statement = null;
        ResultSet results = null;
        String id = null;
        try {
            statement = getConnection(env, "touch").createStatement();
            statement.executeQuery(query);
            results = statement.getResultSet();
            results.next();
            id = results.getString("linked_profile_id");
            statement.close();
            DBConnector.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public static void addPhoneAndOTPStatusIntoDB(String env, String linkedClientProfileID){
        String tableName = DBProperties.getPropertiesFor(env,"touch").getDBName();

        String query = "INSERT INTO `" + tableName + "`.`client_attribute` (`client_profile_id`, `key`, `value`) " +
                "VALUES ('" + linkedClientProfileID + "', 'otpSent', 'true') ON DUPLICATE KEY UPDATE `value` = 'true';";

        Statement statement = null;
        ResultSet results = null;
        String id = null;
        try {
            statement = getConnection(env, "touch").createStatement();
            statement.executeUpdate(query);
            results = statement.getResultSet();
            statement.close();
            DBConnector.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean isSMSClientProfileCreated(String env, String phoneNumber, String linkedProfileID, String type) {
        String tableName = DBProperties.getPropertiesFor(env,"touch").getDBName();

        String query = "SELECT * FROM "+ tableName +".client_profile where client_id='"+phoneNumber+"' " +
                "and linked_profile_id='"+linkedProfileID+"' and type = '"+type+"';";
        Statement statement = null;
        ResultSet results = null;
        boolean isRecordExists = false;
        try {
            for(int i = 0; i<5; i++) {
                statement = getConnection(env, "touch").createStatement();
                statement.executeQuery(query);
                results = statement.getResultSet();
                isRecordExists = results.next();
                if (isRecordExists) {
                    statement.close();
                    DBConnector.closeConnection();
                    break;
                } else {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                statement.close();
                DBConnector.closeConnection();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isRecordExists;
    }

    public static void updateClientLastVisitDate(String env, String linkedClientProfileId, Long timestampDate){
        String tableName = DBProperties.getPropertiesFor(env,"touch").getDBName();

        String query = "UPDATE `"+tableName+"`.`client_attribute` SET `value`='"+timestampDate+"' WHERE `client_profile_id`='"+linkedClientProfileId+"' and`key`='lastVisit';";

        Statement statement = null;
        ResultSet results = null;
        String id = null;
        try {
            statement = getConnection(env, "touch").createStatement();
            statement.executeUpdate(query);
            results = statement.getResultSet();
            statement.close();
            DBConnector.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Long getLastVisitForUserProfile(String env, String linkedClientProfileId){
        String tableName = DBProperties.getPropertiesFor(env,"touch").getDBName();

        String query = "SELECT value FROM "+ tableName +".client_attribute where client_profile_id='"+linkedClientProfileId+"' and `key` = 'lastVisit';";
        Statement statement = null;
        ResultSet results = null;
        long timestamp =0;
        try {
            statement = getConnection(env, "touch").createStatement();
            statement.executeQuery(query);
            results = statement.getResultSet();
            results.next();
            timestamp = results.getLong("value");
            statement.close();
            DBConnector.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return timestamp;
    }


    public static boolean isLastVisitSavedInDB(String env, String linkedClientProfileId, int secondsTimeout) {
        String tableName = DBProperties.getPropertiesFor(env, "touch").getDBName();
        String query = "SELECT value FROM " + tableName + ".client_attribute where client_profile_id='" + linkedClientProfileId + "' and `key` = 'lastVisit';";
        Statement statement = null;
        ResultSet results = null;
        boolean isLastVisitSaved = false;
        int exitLoop = (secondsTimeout*60)/15;
        try {
            for (int i = 0; i < exitLoop+2; i++) {
                statement = getConnection(env, "touch").createStatement();
                statement.executeQuery(query);
                results = statement.getResultSet();
                isLastVisitSaved = results.next();
                if (isLastVisitSaved) {
                    statement.close();
                    DBConnector.closeConnection();
                    break;
                } else {
                    try {
                        Thread.sleep(15000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                statement.close();
                DBConnector.closeConnection();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isLastVisitSaved;
    }

    public static Map<String, String> getActiveSessionDetailsByClientProfileID(String env, String clientID) {
        String tableName = DBProperties.getPropertiesFor(env,"touch").getDBName();
        Map<String, String> sessionDetails = new HashMap<>();
        String query = "SELECT * FROM "+tableName+".session where client_id = '"+clientID+"' and state = 'ACTIVE';";
        Statement statement = null;
        ResultSet results = null;
        try {
            statement = getConnection(env, "touch").createStatement();
            statement.executeQuery(query);
            results = statement.getResultSet();
            results.next();
            sessionDetails.put("sessionId", getColumnValue(results, "session_id"));
            sessionDetails.put("clientProfileId",  getColumnValue(results,"client_profile_id"));
            sessionDetails.put("conversationId",  getColumnValue(results,"conversation_id"));
            statement.close();
            DBConnector.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sessionDetails;
    }

    public static Map<String, String> getSessionDetailsByClientID(String env, String clientID) {
        String tableName = DBProperties.getPropertiesFor(env,"touch").getDBName();
        Map<String, String> sessionDetails = new HashMap<>();
        String query = "SELECT * FROM "+tableName+".session where client_id = '"+clientID+"';";
        Statement statement = null;
        ResultSet results = null;
        try {
            statement = getConnection(env, "touch").createStatement();
            statement.executeQuery(query);
            results = statement.getResultSet();
            results.next();
            sessionDetails.put("clientJID", getColumnValue(results, "client_jid"));
            sessionDetails.put("state", getColumnValue(results, "state"));
            sessionDetails.put("startedDate", getColumnValue(results, "started_date"));
            sessionDetails.put("endedDate", getColumnValue(results, "ended_date"));
            sessionDetails.put("sessionId", getColumnValue(results, "session_id"));
            sessionDetails.put("clientProfileId",  getColumnValue(results,"client_profile_id"));
            sessionDetails.put("conversationId",  getColumnValue(results,"conversation_id"));
            statement.close();
            DBConnector.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sessionDetails;
    }

    public static Map<String, String> getChatAgentHistoryDetailsBySessionID(String env, String sessionID) {
        String tableName = DBProperties.getPropertiesFor(env,"touch").getDBName();
        Map<String, String> details = new HashMap<>();
        String query = "SELECT * FROM "+tableName+".chat_agent_history where session_id = '"+sessionID+"';";
        Statement statement = null;
        ResultSet results = null;
        try {
            statement = getConnection(env, "touch").createStatement();
            statement.executeQuery(query);
            results = statement.getResultSet();
            results.next();
            details.put("endedDate", getColumnValue(results, "ended_date"));
            statement.close();
            DBConnector.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return details;
    }

    public static Map<String, String> getConversationByID(String env, String conversationID) {
        String tableName = DBProperties.getPropertiesFor(env,"touch").getDBName();
        Map<String, String> conversationDetails = new HashMap<>();
        String query = "SELECT * FROM "+tableName+".conversation where conversation_id = '"+conversationID+"';";
        Statement statement = null;
        ResultSet results = null;
        try {
            statement = getConnection(env, "touch").createStatement();
            statement.executeQuery(query);
            results = statement.getResultSet();
            results.next();
            conversationDetails.put("active", getColumnValue(results, "active"));
            statement.close();
            DBConnector.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conversationDetails;
    }

    private static String getColumnValue(ResultSet results, String column){
        String columnValue="";
        try {
            columnValue = results.getString(column);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e){
            Assert.assertTrue(false, "Unable to get '" +column+ "' column value");
        }
        return columnValue;
    }

    public static String getCountryName(String env, String code) {
        String tableName = DBProperties.getPropertiesFor(env,"touch").getDBName();
        String query = "SELECT name FROM "+ tableName +".country where code='"+code+"';";
        Statement statement = null;
        ResultSet results = null;
        String name = null;
        try {
            statement = getConnection(env, "touch").createStatement();
            statement.executeQuery(query);
            results = statement.getResultSet();
            results.next();
            name = results.getString("name");
            statement.close();
            DBConnector.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return name;
    }

//    public static void main(String args[]){
//        String clientProfileID = DBConnector.getClientProfileID("testing", "camundatest17");
//        long lastVisit = DBConnector.getLastVisitForUserProfile("testing", clientProfileID);
//        long lastVisitWithShift = minusHoursFromTimestamp(lastVisit, 12);
//        DBConnector.updateClientLastVisitDate("testing", clientProfileID, lastVisitWithShift);
//    }


}
