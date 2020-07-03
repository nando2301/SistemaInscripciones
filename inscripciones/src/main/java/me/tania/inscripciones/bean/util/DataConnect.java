package me.tania.inscripciones.bean.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataConnect {

    private static final Logger LOG = Logger.getLogger(DataConnect.class.getName());

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/inscripciones?serverTimezone=UTC", "root", "mysql");
            return con;
        } catch (ClassNotFoundException | SQLException ex) {
            LOG.log(Level.WARNING, "Database.getConnection() Error -->{0}", ex.getMessage());
            System.out.println("Database.getConnection() Error -->"
                    + ex.getMessage());
            return null;
        }
    }

    public static void close(Connection con) {
        try {
            con.close();
        } catch (SQLException ex) {
        }
    }
}
