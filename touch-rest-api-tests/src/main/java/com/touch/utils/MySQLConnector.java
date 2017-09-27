package com.touch.utils;

import com.clickatell.models.permissions.PermissionRequest;
import com.clickatell.models.roles.response.Permission;
import com.clickatell.models.solutions.response.Solution;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MySQLConnector {
    Connection connection = null;
    Statement statement = null;
    ResultSet rs = null;
    int reslut;

    public static MySQLConnector db;

    private MySQLConnector() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your MySQL JDBC Driver?");
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(ConfigApp.DB_URL, ConfigApp.DB_USER, ConfigApp.DB_PASSWORD);
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
        }
    }

    /**
     * @return MysqlConnect Database connection object
     */
    public static synchronized MySQLConnector getDbConnection() {
        if (db == null) {
            db = new MySQLConnector();
        }
        return db;

    }

    public String getInvitationIdBy(String emailOfInvitedUser, String accountInviteToId) {
        String query = "SELECT id FROM invitation where account_id = '" + accountInviteToId + "' and email = '" + emailOfInvitedUser + "'";
        try {
            statement = connection.createStatement();
            rs = executeSelectQuery(query);
            System.out.println(rs.toString());
            if (rs != null) {
                while (rs.next()) {
                    return rs.getString(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Solution getSolutionsBy(String accountId) {
        String query = "SELECT * FROM solution where account_id = '" + accountId + "'";
        try {
            statement = connection.createStatement();
            rs = executeSelectQuery(query);
            System.out.println(rs.toString());
            if (rs != null) {
                while (rs.next()) {
                    Solution solution = new Solution();
                    solution.setType(rs.getString("type"));
                    solution.setUsersLimit(rs.getInt("users_limit"));
                    solution.setUsersAmount(0);
                    return solution;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getUserMetadataIdBy(String email) {
        String query = "SELECT id FROM user_metadata where email = '" + email + "'";
        try {
            statement = connection.createStatement();
            rs = executeSelectQuery(query);
            if (rs != null) {
                while (rs.next()) {
                    return rs.getString(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getPasswordResetId(String email) {
        String userMetadataId = getUserMetadataIdBy(email);
        String query2 = "SELECT id FROM password_reset where user_metadata_id = '" + userMetadataId + "'";
        try {
            statement = connection.createStatement();
            rs = executeSelectQuery(query2);
            if (rs != null) {
                while (rs.next()) {
                    return rs.getString(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Permission> getAllPermissionsLongForm() {
        String query = "SELECT * FROM permission";
        try {
            statement = connection.createStatement();
            rs = executeSelectQuery(query);
            ResultSetHandler<List<Permission>> h = new BeanListHandler<>(Permission.class);
            return h.handle(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<String> getAllPermissionsShortForm() {
        String query = "SELECT id FROM permission";
        List<String> array = new ArrayList<>();
        try {
            statement = connection.createStatement();
            rs = executeSelectQuery(query);
            while (rs.next()) {
                array.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return array;
    }

    /**
     * Add permission
     */
    public void addPermisssion(PermissionRequest permission) {
        ResultSet rs;
        String query = "INSERT INTO permission (id, description, category, solution, action, subject) " +
                "VALUES ('" + permission.getId() + "'" +
                ", '" + permission.getDescription() + "'" +
                ", '" + permission.getCategory() + "'" +
                ", '" + permission.getSolution() + "'" +
                ", '" + permission.getAction() + "'" +
                ", '" + permission.getSubject() + "')";
        try {
            statement = connection.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean romovePermisssion(PermissionRequest permission) {
        String query = "DELETE FROM  permission WHERE id = '" + permission.getId() + "'";
        try {
            statement = connection.createStatement();
            return statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getVerificationCodeForNumber(String numberId) {
        String query = "SELECT sn.verification_code FROM sandbox_number as sn \n" +
                "WHERE sn.id ='" + numberId + "'    \n" +
                "\tAND sn.verified = false\n" +
                "ORDER BY sn.created_date DESC \n" +
                "LIMIT 1";
        try {
            statement = connection.createStatement();
            rs = executeSelectQuery(query);
            rs.first();
            return rs.getString(1);
        } catch (SQLException e) {

        }
        return null;
    }

    public String getAccountIdByName(String accountName) {
        String query = "SELECT id FROM account where name='" + accountName + "'";
        try {
            statement = connection.createStatement();
            rs = executeSelectQuery(query);
            rs.first();
            return rs.getString(1);
        } catch (SQLException e) {

        }
        return null;
    }

    public String getNewUserEmailId(String newEmail) {
        String query = "SELECT id FROM email_update_request where new_email='" + newEmail + "'";
        try {
            statement = connection.createStatement();
            rs = executeSelectQuery(query);
            rs.first();
            return rs.getString(1);
        } catch (SQLException e) {

        }
        return null;
    }

    private ResultSet executeSelectQuery(String query) {
        try {
            statement.executeQuery(query);
            return statement.getResultSet();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void closeConnection() {
        try {
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public String getTractBillingAccountId(String accountId) {
        String query = "select tract_billing_account_id from account WHERE id='" + accountId + "'";
        try {
            statement = connection.createStatement();
            rs = executeSelectQuery(query);
            rs.first();
            return rs.getString(1);
        } catch (SQLException e) {

        }
        return null;
    }

    public String getCMNextId(String messageId) {
        String query = "select cmnext_id from sms_message_response WHERE message_id='" + messageId + "'";
        try {
            statement = connection.createStatement();
            rs = executeSelectQuery(query);
            rs.first();
            return rs.getString(1);
        } catch (SQLException e) {

        }
        return null;
    }

    public String getCMNextCallbackStatus(String cmnextId) {
        String query = "select status from sms_message_callback WHERE cmnext_id='" + cmnextId + "'";
        try {
            statement = connection.createStatement();
            rs = executeSelectQuery(query);
            rs.first();
            return rs.getString(1);
        } catch (SQLException e) {

        }
        return null;
    }

    public String getMessageInfoFromById(String messageId, String tableName, String... fields) {
        String query;
        if (fields.length == 0) {
            query = "select * from " + tableName + " WHERE message_id='" + messageId + "'";
        } else {
            String fieldsArray = Arrays.toString(fields);
            query = "select " + fieldsArray.substring(1, fieldsArray.length() - 1) + " from " + tableName + " WHERE message_id='" + messageId + "'";
        }
        try {
            statement = connection.createStatement();
            rs = executeSelectQuery(query);
            rs.first();
            return rs.getString(1);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }


    public void updateAccountClickatellStatus(Integer clickatell, String accountId) {
        String query = "update account SET clickatell =" + clickatell + " WHERE id='" + accountId + "'";
        try {
            statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {

        }
    }

    public void updateTwoWayNumberStatus(String number) {
        String query = "update two_way_number SET status = 'AVAILABLE'" + " WHERE number='" + number + "'";
        try {
            statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {

        }
    }

    public void updateTenantBusinessHours(String tenantId, String dayOfWeek, String startWorkTime, String endWorkTime) {
        String query = "update touch_testing.tenant_business_hour set end_work_time='" + endWorkTime  +
                "', start_work_time='" + startWorkTime + "' where tenant_id='" + tenantId + "' and day_of_week='" + dayOfWeek + "'";
        try {
            statement = connection.createStatement();
            reslut = statement.executeUpdate(query);
        } catch (SQLException e) {

        }
    }

    public void updateAccountBalance(String accountId, Integer balance) {
        String query = "update account_balance SET balance = " + balance + " WHERE account_id ='" + accountId + "'";
        try {
            statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {

        }
    }

    public String getAccountActivationId(String accountId) {
        String query = "SELECT * FROM account_activation WHERE account_id = '" + accountId + "'";
        try {
            statement = connection.createStatement();
            rs = executeSelectQuery(query);
            rs.next();
            return rs.getString(1);
        } catch (SQLException e) {
        }
        return null;
    }
}
