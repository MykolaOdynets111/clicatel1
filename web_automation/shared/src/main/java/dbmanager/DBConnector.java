package dbmanager;
import com.sun.istack.NotNull;
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
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(dbProperties.getURL(), dbProperties.getUser(), dbProperties.getPass());
        } catch (Exception e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
        }
    }

    @NotNull
    private static  Connection getConnection(String env, String platform) {
        if (connection == null) {
            createConnection(env, platform);
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

    public static String getLinkedClientProfileID(String env, String clientID) {
        String tableName = DBProperties.getPropertiesFor(env,"touch").getDBName();
        String query = "SELECT * FROM "+ tableName +".client_profile where client_id='"+clientID+"' ";

        return getDataFromDb(env, "touch", query, "linked_profile_id");
    }

    public static Map<String, String> getDatesOfUserConversationOrSession(String env, String clientId, String table){
        ResultSet results = null;
        Map<String, String> conversationDates = new HashMap<>();
        String tableName = DBProperties.getPropertiesFor(env, "touch").getDBName();
        String query = "SELECT * FROM " + tableName + "."+table+" where client_id='"+clientId+"'";
        try(Statement statement = getConnection(env, "touch").createStatement()) {
            statement.executeQuery(query);
            results = statement.getResultSet();
            results.next();
            conversationDates.put("started_date", getColumnValue(results, "started_date"));
            if(table.equalsIgnoreCase("conversation")){
                conversationDates.put("updated_date", getColumnValue(results, "updated_date"));
            }else if (table.equalsIgnoreCase("session")){
                conversationDates.put("last_activity", getColumnValue(results, "last_activity"));
            }
            conversationDates.put("ended_date", getColumnValue(results, "ended_date"));
        } catch (SQLException e)  {
            e.printStackTrace();
        }
        return conversationDates;
    }

    public static void updateDatesOfUserConversationOrSession(String env, String clientId, String table, Map<String, String> map) {
        String tableName = DBProperties.getPropertiesFor(env,"touch").getDBName();
        String query = "";
        if (table.equalsIgnoreCase("session")) {
            query = "UPDATE " + tableName + ".session SET " +
                    "started_date ='"+map.get("started_date")+"', last_activity='"+map.get("last_activity")+"', ended_date='"+map.get("ended_date")+"' WHERE (client_id='"+clientId+"')";
        } else if (table.equalsIgnoreCase("conversation")){
            query= "UPDATE " + tableName + ".conversation SET started_date='"+map.get("started_date")+"', updated_date='"+map.get("updated_date")+"', ended_date='"+map.get("ended_date")+"' WHERE (client_id='"+clientId+"')";
        }
        updateDataInDB(env, "touch", query);
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


    public static String getchatId(String env, String clientId){
        String tableName = DBProperties.getPropertiesFor(env,"touch").getDBName();
        String query = "SELECT * FROM " + tableName + ".conversation where client_id = '" + clientId + "'";
        return getDataFromDb(env, "touch", query, "conversation_id");
    }

    public static void updateAgentHistoryTicketStatus(String env, String status, String chatId){
        String tableName = DBProperties.getPropertiesFor(env,"touch").getDBName();
        String query = "UPDATE " + tableName + ".chat_agent_history_ticket SET state = '" + status + "' WHERE (chat_id = '" + chatId + "')";
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
        ResultSet results = null;
        try(Statement statement = getConnection(env, "touch").createStatement()) {
            statement.executeQuery(query);
            results = statement.getResultSet();
            results.next();
            sessionDetails.put("sessionId", getColumnValue(results, "session_id"));
            sessionDetails.put("clientProfileId",  getColumnValue(results,"client_profile_id"));
            sessionDetails.put("conversationId",  getColumnValue(results,"conversation_id"));
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
        ResultSet results = null;
        try(Statement statement = getConnection(env, "touch").createStatement()) {
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

    public static String getDefaultAutoResponder(String env, String autoresponderId){
        String tableName = DBProperties.getPropertiesFor(env,"touch").getDBName();
        String query = "SELECT * FROM "+tableName+".autoresponders where id = '"+autoresponderId+"';";

        return getDataFromDb(env, "touch", query, "default_message");
    }


    private static void waitFor(int wait) {
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
