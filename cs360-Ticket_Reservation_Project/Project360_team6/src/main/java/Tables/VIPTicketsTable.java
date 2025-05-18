package Tables;

import Connection.*;
import mainClasses.*;
import java.sql.*;

public class VIPTicketsTable {
    public void createTable() throws SQLException, ClassNotFoundException {
        Connection con = DatabaseConnection.createConnection();
        Statement stmt = con.createStatement();
        String createTableSQL = "CREATE TABLE IF NOT EXISTS vip_tickets"
                + "(viptickets_id INTEGER PRIMARY KEY AUTO_INCREMENT, "
                + "reserv_id INT, "
                + "event_id INT, "
                + "seat_no VARCHAR(10) NOT NULL, "
                + "availability TINYINT NOT NULL); ";
        stmt.execute(createTableSQL);
        stmt.close();
        con.close();
    }

    public int addNewVIPTicket(VIPTickets ticket) throws SQLException, ClassNotFoundException {
        Connection con = DatabaseConnection.createConnection();
        Statement stmt = con.createStatement();

        String addVIPTicketSQL = "INSERT INTO "
                + " vip_tickets (reserv_id, event_id, seat_no, availability)"
                + " VALUES ("
                + ticket.get_reserv_id() + ","
                + ticket.get_event_id() + ","
                + "'" + ticket.get_seatno() + "',"
                + (ticket.get_availability() ? 1 : 0)
                + ")";
        stmt.executeUpdate(addVIPTicketSQL);
        System.out.println("The VIP ticket was successfully added in the database.");

        String getVIPTicketID = "SELECT viptickets_id FROM vip_tickets WHERE reserv_id="+ticket.get_reserv_id()+
        		" AND event_id="+ticket.get_event_id()+" AND seat_no='"+ticket.get_seatno()+"' AND availability="+(ticket.get_availability() ? 1 : 0);
        ResultSet rs = stmt.executeQuery(getVIPTicketID);
        rs.next();
        int viptickets_id = rs.getInt("viptickets_id");


        stmt.close();
        con.close();
        return viptickets_id;
    }
    
    public void createTicketsForEvent(int event_id, int general_capacity) throws SQLException, ClassNotFoundException{
    	Connection con = DatabaseConnection.createConnection();
    	Statement stmt = con.createStatement();
    	int i;
    	for(i=1;i<=general_capacity;i++) {
    		String seatno = "V"+i;
    		VIPTickets ticket= new VIPTickets(event_id, seatno);
    		addNewVIPTicket(ticket);
    	}
    	System.out.println(general_capacity+" general tickets created for event_id: "+ event_id);
    	stmt.close();
    	con.close();
    }

    public ResultSet availableSeats(int eventId)throws SQLException, ClassNotFoundException{
        Connection con = DatabaseConnection.createConnection();
    	Statement stmt = con.createStatement();
        String availableSeatsSQL = "SELECT seat_no FROM vip_tickets" +
                                   " WHERE event_id=" + eventId + " AND availability=1";
        ResultSet rs = stmt.executeQuery(availableSeatsSQL);
        return rs;
    }
    
    public ResultSet boughtSeats(int eventId)throws SQLException, ClassNotFoundException{
        Connection con = DatabaseConnection.createConnection();
    	Statement stmt = con.createStatement();
        String boughtSeatsSQL = "SELECT seat_no FROM vip_tickets" +
                                   " WHERE event_id=" + eventId + " AND availability=0";
        ResultSet rs = stmt.executeQuery(boughtSeatsSQL);
        return rs;
    }

    public void buyTickets(int count, int reservId, int eventId)throws SQLException, ClassNotFoundException{
        Connection con = DatabaseConnection.createConnection();
    	Statement stmt = con.createStatement();
        String buyTicketsSQL = "UPDATE vip_tickets SET availability=0, reserv_id = "+reservId + " WHERE availability=1 AND event_id =" +
        eventId + " LIMIT 1;";
        for (int i=0;i<count;i++){
            stmt.executeUpdate(buyTicketsSQL);
        }
        stmt.close();
    	con.close();
    }
    
    public void cancelTicketsWithResId(int resid)throws SQLException, ClassNotFoundException{
    	Connection con = DatabaseConnection.createConnection();
    	Statement stmt = con.createStatement();
    	String cancelticksSQL = "UPDATE vip_tickets SET availability=1, reserv_id = 0 WHERE reserv_id=" + resid;
    	stmt.executeUpdate(cancelticksSQL);
    	stmt.close();
    	con.close();
    }
    
    public void deleteTicketsWithEvId(int eventId)throws SQLException, ClassNotFoundException{
    	Connection con = DatabaseConnection.createConnection();
    	Statement stmt = con.createStatement();
    	String deleteticksSQL = "DELETE FROM vip_tickets WHERE event_id =" + eventId;
    	stmt.executeUpdate(deleteticksSQL);
    	stmt.close();
    	con.close();
    }
    
    public double totalProfits(int eventId) throws SQLException, ClassNotFoundException{
        Connection con = DatabaseConnection.createConnection();
        Statement stmt = con.createStatement();
        double result = 0.0;
        if(eventId==0){
            String allEventsSQL = "SELECT E.event_vip_price, COUNT(*) AS amount FROM vip_tickets V "+
                                  "INNER JOIN events E ON E.event_id = V.event_id " +
                                  "WHERE V.availability=0 "+
                                  "GROUP BY E.event_id,E.event_vip_price "+
                                  "ORDER BY amount DESC;";
            ResultSet rs = stmt.executeQuery(allEventsSQL);
            while(rs.next()){
                int amount = rs.getInt("amount");
                double price = rs.getDouble("event_vip_price");
                result +=  amount * price;
            }
        }else{
            String specificEventSQL = "SELECT E.event_vip_price, COUNT(V.event_id) AS amount FROM vip_tickets V "+
                                  "JOIN events E ON E.event_id = V.event_id " +
                                  "WHERE V.availability=0 AND E.event_id =" +eventId+
                                  " GROUP BY E.event_id,E.event_vip_price ";
            ResultSet rs = stmt.executeQuery(specificEventSQL);
            while(rs.next()){
            	int amount = rs.getInt("amount");
                double price = rs.getDouble("event_vip_price");
                result +=  amount * price;
            }
        }
        stmt.close();
        con.close();
        return result;
    }
}