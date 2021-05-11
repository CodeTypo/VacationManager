package com.codetypo.VacationManager.Utilities;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * This class represents database connectivity.
 */
public abstract class DbUtil {

    /**
     * This method closes <code>Connection</code>, <code>Statement</code> and <code>ResultSet</code>.
     *
     * @param conn      represents <code>Connection</code>.
     * @param statement represents <code>Statement</code>.
     * @param resultSet represents <code>ResultSet</code>.
     */
    protected static void close(Connection conn, Statement statement, ResultSet resultSet) {

        try {
            if (resultSet != null)
                resultSet.close();

            if (statement != null)
                statement.close();

            if (conn != null)
                conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
