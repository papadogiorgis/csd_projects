package Tables;

import Connection.*;
import mainClasses.*;
import java.sql.*;

public class ReservationsTable {
    public void createTable() throws SQLException, ClassNotFoundException {
        Connection con = DatabaseConnection.createConnection();
        Statement stmt = con.createStatement();
        String createTableSQL = "CREATE TABLE IF NOT EXISTS reservations"
                + "(reserv_id INT AUTO_INCREMENT PRIMARY KEY, "
                + "cust_id INT, "
                + "event_id INT,"
                + "number_of_tickets_gen INT NOT NULL,"
                + "number_of_tickets_vip INT NOT NULL,"
                + "reserv_date DATE NOT NULL,"
                + "reserv_cost DECIMAL(10, 2) NOT NULL,"
                + "FOREIGN KEY (cust_id) REFERENCES customers(cust_id),"
                + "FOREIGN KEY (event_id) REFERENCES events(event_id)); ";
        stmt.execute(createTableSQL);
        stmt.close();
        con.close();
    }
    
    public int addNewReservation(Reservations reservation) throws SQLException, ClassNotFoundException {
        Connection con = DatabaseConnection.createConnection();
        Statement stmt = con.createStatement();
        
        String getResCostSQL= "SELECT event_vip_price, event_general_price FROM events WHERE event_id="+reservation.get_event_id();
        ResultSet eventRS= stmt.executeQuery(getResCostSQL);
        double vipTicketCost= 0.0;
        double generalTicketCost= 0.0;
        if(eventRS.next()) {
        	vipTicketCost= eventRS.getDouble("event_vip_price");
        	generalTicketCost= eventRS.getDouble("event_general_price");
        }
        double totalCost= (vipTicketCost * reservation.get_numtick_vip())+(generalTicketCost * reservation.get_numtick_gen());
        reservation.set_reserv_cost(totalCost);

        String addReservationSQL = "INSERT INTO "
                + " reservations (cust_id, event_id, number_of_tickets_gen, number_of_tickets_vip, reserv_date, reserv_cost)"
                + " VALUES ("
                + reservation.get_cust_id() + ", "
                + reservation.get_event_id() + ", "
                + reservation.get_numtick_gen() + ", "
                + reservation.get_numtick_vip() + ", '"
                + reservation.get_res_date() + "', "
                + reservation.get_reserv_cost()
                + ")";
        stmt.executeUpdate(addReservationSQL);
        System.out.println("The Reservation was successfully added in the database.");


        String getReservationID = "SELECT reserv_id FROM reservations WHERE cust_id="+reservation.get_cust_id()+
        		" AND event_id="+reservation.get_event_id()+" AND number_of_tickets_gen="+reservation.get_numtick_gen()+
        		" AND number_of_tickets_vip="+reservation.get_numtick_vip()+" AND reserv_date='"+reservation.get_res_date()+
        		"' AND reserv_cost="+reservation.get_reserv_cost();
        ResultSet rs = stmt.executeQuery(getReservationID);
        rs.next();
        int reserv_id = rs.getInt("reserv_id");
        VIPTicketsTable vip = new VIPTicketsTable();
        GeneralTicketsTable gen = new GeneralTicketsTable();
        vip.buyTickets(reservation.get_numtick_vip(), reserv_id, reservation.get_event_id());
        gen.buyTickets(reservation.get_numtick_gen(), reserv_id,reservation.get_event_id());

        stmt.close();
        con.close();
        return reserv_id;
    }
    
    public void cancelReservation(int resid)throws SQLException, ClassNotFoundException{
    	Connection con = DatabaseConnection.createConnection();
    	Statement stmt = con.createStatement();
    	String cancelresSQL = "DELETE FROM reservations WHERE reserv_id =" + resid;
    	stmt.executeUpdate(cancelresSQL);
    	stmt.close();
    	con.close();
    }
    
    public void deleteReservation_with_eventId(int eventId)throws SQLException, ClassNotFoundException{
    	Connection con = DatabaseConnection.createConnection();
    	Statement stmt = con.createStatement();
    	String deleteresSQL = "DELETE FROM reservations WHERE event_id =" + eventId;
    	stmt.executeUpdate(deleteresSQL);
    	stmt.close();
    	con.close();
    }
    
    public ResultSet findResByEvent(int eventId)throws SQLException, ClassNotFoundException{
        Connection con = DatabaseConnection.createConnection();
    	Statement stmt = con.createStatement();
        String findResSQL = "SELECT reserv_cost FROM reservations WHERE event_id=" + eventId;
        ResultSet rs = stmt.executeQuery(findResSQL);
        return rs;
    }
    
    public String findPopularEvent() throws SQLException, ClassNotFoundException {
        String eventId = null;
        Connection con = DatabaseConnection.createConnection();
    	Statement stmt = con.createStatement();
        String findPopSQL = "SELECT event_id, SUM(number_of_tickets_gen + number_of_tickets_vip) AS total_tickets "
                          + "FROM reservations "
                          + "GROUP BY event_id "
                          + "ORDER BY total_tickets DESC "
                          + "LIMIT 1";
        ResultSet rs = stmt.executeQuery(findPopSQL);
        if (rs.next()) {
            eventId = rs.getString("event_id"); // Retrieve event_id from the result set
        }
        return eventId;  // Return the event_id or null if no result is found
    }
    
    public ResultSet findReservationsByDateRange(String startDate, String endDate) throws SQLException, ClassNotFoundException {
        Connection con = DatabaseConnection.createConnection();
        Statement stmt = con.createStatement();
        String findResByDateSQL = "SELECT reserv_id FROM reservations "
                                + "WHERE reserv_date BETWEEN '" + startDate + "' AND '" + endDate + "'";
        ResultSet rs = stmt.executeQuery(findResByDateSQL);
        return rs;
    }

    public String findMostProfitable(String date1, String date2)throws SQLException, ClassNotFoundException{
        Connection con = DatabaseConnection.createConnection();
        Statement stmt = con.createStatement();

        String mostProfit = "SELECT E.event_name, E.event_id , SUM(R.reserv_cost) as profits "+
                            "FROM events E " +
                            "INNER JOIN reservations R ON E.event_id = R.event_id " +
                            "WHERE E.event_date BETWEEN '" + date1 + "' AND '" + date2 +"' "+
                            "GROUP BY E.event_name, E.event_id "+
                            "ORDER BY profits DESC LIMIT 1";
        ResultSet rs = stmt.executeQuery(mostProfit);
        rs.next();
        String result = "Event Name:" + rs.getString("event_name") + "\\nEvent Id:" +
                        rs.getString("event_id") + "\\nTotal profits:" + rs.getString("profits");

        return result;
    }
}