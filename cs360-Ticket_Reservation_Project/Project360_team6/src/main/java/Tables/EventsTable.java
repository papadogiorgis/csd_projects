package Tables;

import Connection.*;
import mainClasses.*;
import java.sql.*;


public class EventsTable {

    public void createTable() throws SQLException,ClassNotFoundException{
        Connection con = DatabaseConnection.createConnection();
        String createTableSQL = "CREATE TABLE events(" +
                                "event_id INT AUTO_INCREMENT PRIMARY KEY," +
                                "event_name varchar(100) NOT NULL UNIQUE," +
                                "event_date DATE NOT NULL,"+
                                "event_time time NOT NULL,"+
                                "event_type varchar(100) NOT NULL," +
                                "event_capacity int NOT NULL,"+
                                "event_vip_capacity int NOT NULL,"+
                                "event_general_capacity int NOT NULL,"+
                                "event_vip_price int NOT NULL,"+
                                "event_general_price int NOT NULL);";
        Statement stmt = con.createStatement();
        stmt.executeUpdate(createTableSQL); 
        stmt.close();
        con.close();
    }


    public int addNewEvent(Event event)throws SQLException,ClassNotFoundException{
        Connection con = DatabaseConnection.createConnection();
        Statement stmt = con.createStatement();
        String addEventSQL ="INSERT INTO events(event_name, event_date, event_time, event_type, event_capacity," +
                            "event_vip_capacity, event_general_capacity, event_vip_price, event_general_price)" +
                            "VALUES('" + event.getName() + "','" + event.getDate() + "','" + event.getTime() + "','" + event.getType() +
                            "','" + event.getCapacity() + "','" + event.getVipCapacity() + "','" + event.getGeneralCapacity() +
                            "','" + event.getVipPrice() + "','" + event.getGeneralPrice() + "');";
                    
        stmt.executeUpdate(addEventSQL); 
        String getIdSQL ="SELECT event_id FROM events WHERE event_name = '" + event.getName() +
                        "' AND event_date = '" + event.getDate() + "' AND event_time ='" + event.getTime() + "'";
        ResultSet rs = stmt.executeQuery(getIdSQL);
        rs.next();
        int event_id = rs.getInt("event_id");
        VIPTicketsTable vip = new VIPTicketsTable();
        vip.createTable();
        vip.createTicketsForEvent(event_id, event.getVipCapacity());
        GeneralTicketsTable gen = new GeneralTicketsTable();
        gen.createTable();
        gen.createTicketsForEvent(event_id, event.getGeneralCapacity());
        stmt.close();
        con.close();
        return event_id;
    }

    public void cancelEvent(Event event)throws SQLException,ClassNotFoundException{
        Connection con = DatabaseConnection.createConnection();
        Statement stmt = con.createStatement();
        String cancelEventSQL = "DELETE FROM events E WHERE event_id = '" + event.getId() + "';";
        String getCreditAndRefundSQL = "SELECT C.card_number, R.reserv_cost" +      
                                       "FROM reservations R" +
                                       "JOIN cust C ON R.cust_id = C.cust_id" +
                                       "JOIN cards Cc ON C.cust_id = Cc.cust_id" +
                                       "WHERE E.event_id = R.event_id;";
        String deleteReservationsSQL = "DELETE FROM Reservations R WHERE R.event_id = '" + event.getId() + "';";
        stmt.executeUpdate(cancelEventSQL); 
        stmt.executeUpdate(getCreditAndRefundSQL); 
        stmt.executeUpdate(deleteReservationsSQL);
        stmt.close();
        con.close();
    }
    
    public void cancelEvent(int eventId)throws SQLException, ClassNotFoundException{
    	Connection con = DatabaseConnection.createConnection();
    	Statement stmt = con.createStatement();
    	String canceleventSQL = "DELETE FROM events WHERE event_id =" + eventId;
    	stmt.executeUpdate(canceleventSQL);
    	stmt.close();
    	con.close();
    }
}