package project;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private DBConnection() {
    }

    public static Connection getCon() throws Exception {
        Class.forName("oracle.jdbc.driver.OracleDriver");

        return DriverManager.getConnection(
            DBInfo.dbUrl,
            DBInfo.uName,
            DBInfo.pWord
        );
    }
}