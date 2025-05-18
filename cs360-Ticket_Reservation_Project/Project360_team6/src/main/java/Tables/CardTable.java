package Tables;

import Connection.*;
import mainClasses.*;
import java.sql.*;

public class CardTable {

    public void createTable() throws SQLException, ClassNotFoundException {
    Connection con = DatabaseConnection.createConnection();
        Statement stmt = con.createStatement();
        String sql = "CREATE TABLE IF NOT EXISTS cards "
                + "(cust_id INTEGER not NULL , "
                + "card_number VARCHAR(20) not null, "
                + "card_exp_date VARCHAR(5) not NULL,"
                + "cvv VARCHAR(4) not null, "
                + "FOREIGN KEY (cust_id) REFERENCES customers(cust_id))";
        stmt.executeUpdate(sql); 
        stmt.close();
        con.close();
    }

    public int addNewCard(Card card,int customerId) throws SQLException, ClassNotFoundException {
    Connection con = DatabaseConnection.createConnection();
        Statement stmt = con.createStatement();
        String insertQuery= "INSERT INTO"
                +" cards (cust_id,card_number, card_exp_date,cvv)"
                +" VALUES ("
                +"'" + customerId +"',"
                +"'" + card.getCard_number() +"',"
                +"'" + card.getCard_exp_date() +"',"
                +"'" + card.getCvv() +"'"
                + ")";
        stmt.executeUpdate(insertQuery); 
        String getCustID="SELECT cust_id FROM cards WHERE card_number = '" + card.getCard_number() +
                        "' AND card_exp_date = '" + card.getCard_exp_date() + "' AND cvv ='" + card.getCvv() + "'";
        ResultSet rs = stmt.executeQuery(getCustID);
        rs.next();
        int cust_id= rs.getInt("cust_id");
        stmt.close();
        con.close();
        return cust_id;
    } 
}