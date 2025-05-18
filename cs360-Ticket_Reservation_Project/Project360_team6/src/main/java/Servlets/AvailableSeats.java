package Servlets;

import Tables.CardTable;
import Tables.CustTable;
import Tables.EventsTable;
import Tables.GeneralTicketsTable;
import Tables.ReservationsTable;
import Tables.VIPTicketsTable;
import java.util.stream.Collectors;
import java.sql.*;
import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;
import java.util.ArrayList;
import java.util.Arrays;


public class AvailableSeats extends HttpServlet {


    public void init() throws ServletException{
        EventsTable eve = new EventsTable();
        CustTable customers = new CustTable();
        ReservationsTable res = new ReservationsTable();
        CardTable cards = new CardTable();
        
        try{
            customers.createTable();
            eve.createTable();
            res.createTable();
            cards.createTable();
        }catch(Exception e){

        }
        
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int eventId = Integer.parseInt(request.getParameter("eventId"));
        String[] seatTypes = request.getParameterValues("seatType");
        
        GeneralTicketsTable gen = new GeneralTicketsTable();
        VIPTicketsTable vip = new VIPTicketsTable();
        
        ArrayList<String> tickets = new ArrayList<>();


        try {
            
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            if(seatTypes!=null && Arrays.asList(seatTypes).contains("vip")){
                ResultSet vipTickets = vip.availableSeats(eventId);
                while(vipTickets.next()){
                    tickets.add(vipTickets.getString("seat_no"));
                }
            }
    
            if(seatTypes!= null && Arrays.asList(seatTypes).contains("general")){
                ResultSet genTickets = gen.availableSeats(eventId);
                while(genTickets.next()){
                    tickets.add(genTickets.getString("seat_no"));
                }
            }
            out.println("<script>");
            out.println("alert('Available tickets for event with ID:" + eventId + " Tickets:"  +
                        tickets.stream().map(Object::toString).collect(Collectors.joining(","))+"');");
            out.println("window.history.back();"); // Go back to the original form page
            out.println("</script>");
        } catch (SQLException | ClassNotFoundException e) {
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<h2>Error occurred while registering the customer.</h2>");
            out.println("<p>" + e.getMessage() + "</p>");
        }
    }



}