package dbmanager;
import org.testng.Assert;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

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
                Assert.fail("DB connection was not established");
            }
        }
            return connection;
        }

    private static synchronized String getDataFromDb(String env, String platform, String query, String column){
        ResultSet results = null;
        String result = null;
        try(Statement statement = getConnection(env, platform).createStatement()) {
            statement.executeQuery(query);
            results = statement.getResultSet();
            results.next();
            result = results.getString(column);
            DBConnector.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static synchronized void updateDataInDB(String env, String platform, String query){
        try(Statement statement = getConnection(env, platform).createStatement()) {
            statement.executeUpdate(query);
            DBConnector.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static synchronized boolean isRecordExistsInDB(String env, String platform, String query) {
        ResultSet results = null;
        boolean result = false;
        try(Statement statement = getConnection(env, platform).createStatement()){
            statement.executeQuery(query);
            results = statement.getResultSet();
            result = results.next();
        } catch (SQLException e){

        }
        return result;
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

    public static String getInvitationIdForCreatedUserFromMC2DB(String env, String userEmail) {
        String tableName = DBProperties.getPropertiesFor(env,"mc2").getDBName();
        String query = "SELECT * FROM "+tableName+".invitation where email=\""+userEmail+"\" AND deleted = 0;";

        return getDataFromDb(env, "mc2", query, "id");
    }


    public static String getAccountActivationIdFromMC2DB(String env, String accountId) {
        String tableName = DBProperties.getPropertiesFor(env,"mc2").getDBName();
        String query = "SELECT * FROM "+tableName+".account_activation where account_id = '"+accountId+"';";

        return getDataFromDb(env, "mc2", query, "id");
    }

    public static String getAccountIdFromMC2DB(String env, String accountName) {
        String tableName = DBProperties.getPropertiesFor(env,"mc2").getDBName();
        String query = "SELECT * FROM "+ tableName +".account where name = '" + accountName + "';";

        return getDataFromDb(env, "mc2", query, "id");
    }


    public static String getClientProfileID(String env, String clientID, String type, int isTenantProfile) {
        String tableName = DBProperties.getPropertiesFor(env,"touch").getDBName();
        String query = "SELECT * FROM "+ tableName +".client_profile where client_id='"+clientID+"' " +
                "and is_tenant_profile="+isTenantProfile+" and type = '"+type+"';";

        return getDataFromDb(env, "touch", query, "id");
    }

    public static String getLinkedClientProfileID(String env, String clientID) {
        String tableName = DBProperties.getPropertiesFor(env,"touch").getDBName();
        String query = "SELECT * FROM "+ tableName +".client_profile where client_id='"+clientID+"' ";

        return getDataFromDb(env, "touch", query, "linked_profile_id");
    }

    public static void addPhoneAndOTPStatusIntoDB(String env, String linkedClientProfileID){
        String tableName = DBProperties.getPropertiesFor(env,"touch").getDBName();
        String query = "INSERT INTO `" + tableName + "`.`client_attribute` (`client_profile_id`, `key`, `value`) " +
                "VALUES ('" + linkedClientProfileID + "', 'otpSent', 'true') ON DUPLICATE KEY UPDATE `value` = 'true';";

        updateDataInDB(env, "touch", query);
    }

    public static boolean isSMSClientProfileCreated(String env, String phoneNumber, String linkedProfileID, String type) {
        String tableName = DBProperties.getPropertiesFor(env,"touch").getDBName();

        String query = "SELECT * FROM "+ tableName +".client_profile where client_id='"+phoneNumber+"' " +
                "and linked_profile_id='"+linkedProfileID+"' and type = '"+type+"';";
        boolean isRecordExists = false;
        for(int i = 0; i<5; i++) {
                isRecordExists = isRecordExistsInDB(env, "touch", query);
                if (isRecordExists) {
                    break;
                } else {
                    waitFor(500);
                }
        }
        DBConnector.closeConnection();
        return isRecordExists;
    }

    public static boolean isLastVisitSavedInDB(String env, String linkedClientProfileId, int secondsTimeout) {
        String tableName = DBProperties.getPropertiesFor(env, "touch").getDBName();
        String query = "SELECT value FROM " + tableName + ".client_attribute where client_profile_id='" + linkedClientProfileId + "' and `key` = 'lastVisit';";
        boolean isLastVisitSaved = false;
        int exitLoop = (secondsTimeout*60)/15;
        for (int i = 0; i < exitLoop+2; i++) {

                isLastVisitSaved = isRecordExistsInDB(env, "touch", query);
                if (isLastVisitSaved) {
                    break;
                } else {
                    waitFor(15000);
                }
        }
        DBConnector.closeConnection();
        return isLastVisitSaved;
    }

    public static boolean isAgentCreatedInDB(String env, String agentEmail) {
        String tableName = DBProperties.getPropertiesFor(env,"touch").getDBName();
        String query = "SELECT * FROM "+tableName+".agent where agent_email='"+agentEmail+"';";
        boolean isAgentPresent = false;
        for(int i = 0; i<20; i++){
                isAgentPresent = isRecordExistsInDB(env, "touch", query);
                if(isAgentPresent){
                    break;
                }
                else {
                    waitFor(500);
                }
        }
        DBConnector.closeConnection();
        return isAgentPresent;
    }


    public static void updateClientLastVisitDate(String env, String linkedClientProfileId, Long timestampDate){
        String tableName = DBProperties.getPropertiesFor(env,"touch").getDBName();
        String query = "UPDATE `"+tableName+"`.`client_attribute` SET `value`='"+timestampDate+"' WHERE `client_profile_id`='"+linkedClientProfileId+"' and`key`='lastVisit';";

        updateDataInDB(env, "touch", query);
    }

    public static Long getLastVisitForUserProfile(String env, String linkedClientProfileId){
        String tableName = DBProperties.getPropertiesFor(env,"touch").getDBName();
        String query = "SELECT value FROM "+ tableName +".client_attribute where client_profile_id='"+linkedClientProfileId+"' and `key` = 'lastVisit';";

        return Long.valueOf(getDataFromDb(env, "touch", query, "value"));
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

    public static int getNumberOfSessionsInConversationForLast3Days(String env, String chatID){
        String tableName = DBProperties.getPropertiesFor(env,"touch").getDBName();
        ZoneId zoneId = TimeZone.getTimeZone("UTC").toZoneId();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime nowMinus3Days = LocalDateTime.now(zoneId).minusDays(3);
        String date = nowMinus3Days.format(formatter);
        String query = "SELECT count(session_id) FROM "+tableName+".session where conversation_id = '"+chatID+"' and ended_date > '"+date+"';";

        return Integer.valueOf(getDataFromDb(env, "touch", query, "count(session_id)"));
    }

    public static Map<String, String> getChatAgentHistoryDetailsBySessionID(String env, String sessionID) {
        String tableName = DBProperties.getPropertiesFor(env,"touch").getDBName();
        Map<String, String> details = new HashMap<>();
        String query = "SELECT * FROM "+tableName+".chat_agent_history_active where session_id = '"+sessionID+"';";
        String date = getDataFromDb(env, "touch", query, "ended_date");
        details.put("endedDate", date);

        return details;
    }

    public static String getLastSessioinID(String env, String tenantName, String clientID){
        String tableName = DBProperties.getPropertiesFor(env,"touch").getDBName();
        String query = "SELECT * FROM "+tableName+".session where tenant_name = '"+tenantName+"' and client_id = '"+clientID+"' order by started_date desc;";

        return getDataFromDb(env, "touch", query, "session_id");
    }

    public static Map<String, String> getConversationByID(String env, String conversationID) {
        String tableName = DBProperties.getPropertiesFor(env,"touch").getDBName();
        Map<String, String> conversationDetails = new HashMap<>();
        String query = "SELECT * FROM "+tableName+".conversation where conversation_id = '"+conversationID+"';";
        String details = getDataFromDb(env, "touch", query, "active");
        conversationDetails.put("active", details);

        return conversationDetails;
    }

    private static String getColumnValue(ResultSet results, String column){
        String columnValue="";
        try {
            columnValue = results.getString(column);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e){
            Assert.fail("Unable to get '" + column + "' column value");
        }
        return columnValue;
    }

    public static String getCountryName(String env, String code) {
        String tableName = DBProperties.getPropertiesFor(env,"touch").getDBName();
        String query = "SELECT name FROM "+ tableName +".country where code='"+code+"';";

        return getDataFromDb(env, "touch", query, "name");
    }


    public static String getResetPassId(String env, String email){
        String tableName = DBProperties.getPropertiesFor(env,"mc2").getDBName();
        String userMetadataIdQuery = "SELECT id FROM "+ tableName +".user_metadata where email='" + email + "';";
        String userMetadatId = getDataFromDb(env, "touch", userMetadataIdQuery, "id");
        String userResetPassQuery = "SELECT id FROM "+ tableName +".password_reset where user_metadata_id='"
                + userMetadatId + "' and deleted=0;";
        return getDataFromDb(env, "touch", userResetPassQuery, "id");
    }

    public static String getVerificationOTPCode(String env, String account_id, String phone) {
        String tableName = DBProperties.getPropertiesFor(env,"mc2").getDBName();
        String query = "SELECT * FROM "+ tableName +".sandbox_number where " +
                "account_id='"+account_id+"' and deleted=0 and number='"+phone.replace("+", "")+"'";

        return getDataFromDb(env, "touch", query, "verification_code");
    }

    private static void waitFor(int wait){
        try {
            Thread.sleep(wait);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

//    public static void main(String args[]){
//        String clientProfileID = DBConnector.getClientProfileID("testing", "camundatest17");
//        long lastVisit = DBConnector.getLastVisitForUserProfile("testing", clientProfileID);
//        long lastVisitWithShift = minusHoursFromTimestamp(lastVisit, 12);
//        DBConnector.updateClientLastVisitDate("testing", clientProfileID, lastVisitWithShift);
//    }


}
