package com.touch.utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sbryt on 6/13/2016.
 */
public class MySQLConnector {
    Connection connection = null;
    Statement statement = null;
    ResultSet rs = null;

    public MySQLConnector() {
        try {
            Class.forName(ApplicationProperties.getInstance().getAppProperties().getProperty("db.driver"));
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your MySQL JDBC Driver?");
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(com.touch.utils.ConfigApp.DB_URL, com.touch.utils.ConfigApp.DB_USER, com.touch.utils.ConfigApp.DB_PASSWORD);
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
        }
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
}
