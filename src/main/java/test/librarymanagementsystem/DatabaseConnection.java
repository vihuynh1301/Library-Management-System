package test.librarymanagementsystem;

import java.sql.*;

public class DatabaseConnection {
    public Connection databaseLink;
    public Connection getDBConnection() {

        String connectionUrl = "jdbc:sqlserver://team5csds341fp.cagsdrstrnzv.us-east-2.rds.amazonaws.com:1433;"
                + "database=Library_Management_System;"
                + "user=team5;"
                + "password=csds341!;"
                + "encrypt=true;"
                + "trustServerCertificate=true;"
                + "loginTimeout=15;";

        String databaseName = "Library_Management_System";
        String databaseUser = "team5";
        String databasePassword = "csds341!";
        String url = "jdbc:sqlserver://team5csds341fp.cagsdrstrnzv.us-east-2.rds.amazonaws.com:1433;";

        try {
            Connection connection = DriverManager.getConnection(connectionUrl);
            Statement statement = connection.createStatement();
            System.out.println("Database is connected ...");

            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            databaseLink = DriverManager.getConnection("jdbc:sqlserver://team5csds341fp.cagsdrstrnzv.us-east-2.rds.amazonaws.com:1433;"
                    + "database=Library_Management_System;"
                    + "user=team5;"
                    + "password=csds341!;"
                    + "encrypt=true;"
                    + "trustServerCertificate=true;"
                    + "loginTimeout=15;");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return databaseLink;
    }
}
