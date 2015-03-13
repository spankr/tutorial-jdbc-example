/**
 *
 */
package com.spankr.tutorial.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Lee_Vettleson
 *
 */
public class DBConnection {

    private static final Logger log = LoggerFactory.getLogger(DBConnection.class);
    private Connection conn = null;

    public void close() {
        if (conn != null) {
            try {
                log.info("Closing database connection to sampleDB");
                conn.close();
            } catch (SQLException e) {
                log.error("Unable to close connection", e);
            }
        }
    }

    public Connection getConnection() throws SQLException {
        if (conn == null) {
            log.info("Opening connection to sampleDB");
            conn = DriverManager.getConnection("jdbc:hsqldb:mem:sampleDB", "SA", "");
        }
        return conn;
    }
}
