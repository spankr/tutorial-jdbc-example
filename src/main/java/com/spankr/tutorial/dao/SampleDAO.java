/**
 *
 */
package com.spankr.tutorial.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Lee_Vettleson
 *
 */
public class SampleDAO {

    private static final Logger log = LoggerFactory.getLogger(SampleDAO.class);

    private Connection conn;

    /**
     * Create a table in the database
     */
    public boolean createTable() {
        boolean success = false;
        if (conn != null) {
            Statement stmt = null;

            try {
                stmt = conn.createStatement();
                stmt.execute("CREATE TABLE sample_table (id INT IDENTITY, first_name VARCHAR(30), last_name VARCHAR(30), age INT)");
                log.info("Creating sample_table");
                success = true;
            } catch (SQLException e) {
                log.error("Unable to create the database table", e);
            } finally {
                if (stmt != null) {
                    try {
                        stmt.close();
                    } catch (SQLException e) {
                    }
                }
            }
        }
        return success;
    }

    /**
     * Remove our sample table
     *
     * @return
     */
    public boolean dropTable() {
        boolean success = false;
        if (conn != null) {
            Statement stmt = null;

            try {
                stmt = conn.createStatement();
                stmt.execute("DROP TABLE sample_table");
                log.info("Deleting sample_table");
                success = true;
            } catch (SQLException e) {
                log.error("Unable to create the database table", e);
            } finally {
                if (stmt != null) {
                    try {
                        stmt.close();
                    } catch (SQLException e) {
                    }
                }
            }
        }
        return success;
    }

    /**
     * Insert a person into the database
     *
     * @param firstName
     * @param lastName
     * @return
     */
    public boolean insertPerson(SamplePerson person) {
        boolean success = false;

        if (person != null) {
            log.debug("insertPerson({} {})", person.getFirstName(), person.getLastName());
            if (conn != null) {
                PreparedStatement pstmt = null;

                try {
                    pstmt = conn.prepareStatement("INSERT INTO sample_table VALUES (null, ?, ?, ?)");
                    pstmt.setString(1, person.getFirstName());
                    pstmt.setString(2, person.getLastName());
                    pstmt.setInt(3, person.getAge());

                    success = (pstmt.executeUpdate() == 1);  // success means exactly one row inserted
                } catch (SQLException e) {
                    log.error("Unable to insert" + person.getFirstName() + " " + person.getLastName() + "into the table", e);
                } finally {
                    if (pstmt != null) {
                        try {
                            pstmt.close();
                        } catch (SQLException e) {
                        }
                    }
                }
            }
        }
        return success;
    }

    /**
     * Get all the people stored in the database
     *
     * @return
     */
    public List<SamplePerson> getAllPeople() {
        log.debug("getAllPeople()");
        List<SamplePerson> people = new ArrayList<SamplePerson>();
        SamplePerson person;
        if (conn != null) {
            Statement stmt = null;

            try {
                stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM sample_table");

                while (rs.next()) {
                    person = new SamplePerson();
                    person.setId(rs.getInt("id"));
                    person.setFirstName(rs.getString("first_name"));
                    person.setLastName(rs.getString("last_name"));
                    person.setAge(rs.getInt("age"));
                    people.add(person);
                }
            } catch (SQLException e) {
                log.error("Unable to create the database table", e);
            } finally {
                if (stmt != null) {
                    try {
                        stmt.close();
                    } catch (SQLException e) {
                    }
                }
            }
        }

        return people;
    }

    /**
     * Get a person by their unique ID
     *
     * @param id
     * @return
     */
    public SamplePerson getPersonById(int id) {
        log.debug("getPersonById({})", id);
        SamplePerson person = null;
        if (conn != null) {
            PreparedStatement pstmt = null;

            try {
                pstmt = conn.prepareStatement("SELECT * FROM sample_table WHERE id = ?");
                pstmt.setInt(1, id);

                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    person = new SamplePerson();
                    person.setId(rs.getInt("id"));
                    person.setFirstName(rs.getString("first_name"));
                    person.setLastName(rs.getString("last_name"));
                    person.setAge(rs.getInt("age"));
                } else {
                    log.warn("No person found for id = {}", id);
                }
            } catch (SQLException e) {
                log.error("Unable query person by ID(" + id + ")", e);
            } finally {
                if (pstmt != null) {
                    try {
                        pstmt.close();
                    } catch (SQLException e) {
                    }
                }
            }
        }

        return person;
    }

    /**
     * Assign the connection to use for this DAO
     *
     * @param conn
     */
    public void setConn(Connection conn) {
        this.conn = conn;
    }
}
