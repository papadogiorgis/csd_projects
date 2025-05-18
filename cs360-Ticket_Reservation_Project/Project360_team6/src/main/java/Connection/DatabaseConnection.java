package Connection;

import java.sql.*;

public class DatabaseConnection{

    private static String url = new String("jdbc:mysql://localhost");
    private static String databaseName = new String("HY360Project");
    private static int port = 3306;
    private static String username = new String("root");
    private static String password = new String("");
    
    public static Connection createConnection() throws ClassNotFoundException, SQLException{
        Class.forName("com.mysql.cj.jdbc.Driver");
        System.out.println("Connection Established!");
        Connection con = DriverManager.getConnection(
        url + ":" + port + "/" + databaseName + "?characterEncoding=UTF-8", username, password);
        return con;
    }
    

}