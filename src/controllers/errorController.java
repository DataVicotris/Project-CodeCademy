package controllers;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;

public class errorController {
    public static String databaseErrorHandling(int code) {
        // Duplicate PK
        if (code == 2627)
            return "Already exists";
        // Database error
        if (code == 9998)
            return "No connection with database.";
        // ResultSet error
        if (code == 9997)
            return "Oops, something went wrong.";
        // Other errors
        else
            return "Oops, something went wrong.";
    }

    public static String queryError(ResultSet rs, Statement stmt, Connection con) {
        if (rs != null) {
            try {
                rs.close();
            } catch (Exception e) {
                System.out.println(e);
                return errorController.databaseErrorHandling(9997);
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (Exception e) {
                System.out.println(e);
                return errorController.databaseErrorHandling(9999);
            }
        }
        if (con != null) {
            try {
                con.close();
            } catch (Exception e) {
                System.out.println(e);
                return errorController.databaseErrorHandling(9998);
            }
        }
        return null;
    }
}
