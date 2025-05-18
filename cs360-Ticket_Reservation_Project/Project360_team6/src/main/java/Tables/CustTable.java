package Tables;

import Connection.*;
import mainClasses.*;
import java.sql.*;

public class CustTable {

    public void createTable() throws SQLException, ClassNotFoundException {
    	Connection con = DatabaseConnection.createConnection();
        Statement stmt = con.createStatement();
        String sql = "CREATE TABLE IF NOT EXISTS customers (" +
                 "cust_id INT AUTO_INCREMENT PRIMARY KEY, " +
                 "name VARCHAR(100) NOT NULL, " +
                 "email VARCHAR(100) NOT NULL UNIQUE);";
        stmt.executeUpdate(sql);
        stmt.close();
        con.close();
 
    }

    public int addNewCustomer(Cust customer) throws SQLException, ClassNotFoundException {
        Connection con = DatabaseConnection.createConnection();
        Statement stmt = con.createStatement();
        String insertQuery= "INSERT INTO"
                +" customers (name, email)"
                +" VALUES ("
                +"'" + customer.getName()+"',"
                +"'" + customer.getEmail()+"'"
                + ")";
        stmt.executeUpdate(insertQuery);
        
        String getCustID="SELECT cust_id FROM customers WHERE email = '"+customer.getEmail()+"'";
        ResultSet rs = stmt.executeQuery(getCustID);
        rs.next();
        int cust_id= rs.getInt("cust_id");


        stmt.close();
        con.close();
        return cust_id;
    }
}