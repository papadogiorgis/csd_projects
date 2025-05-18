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


public class SeatsState extends HttpServlet {
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
        
        GeneralTicketsTable gen = new GeneralTicketsTable();
        VIPTicketsTable vip = new VIPTicketsTable();
        
        ArrayList<String> available_tickets = new ArrayList<>();
        ArrayList<String> bought_tickets = new ArrayList<>();
        
        int a=0, b=0;


        try {
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            
            ResultSet vipAvailable = vip.availableSeats(eventId);
            while(vipAvailable.next()){
                available_tickets.add(vipAvailable.getString("seat_no"));
                a++;
            }
            ResultSet genAvailable = gen.availableSeats(eventId);
            while(genAvailable.next()){
                available_tickets.add(genAvailable.getString("seat_no"));
                a++;
            }
            
            ResultSet vipBought = vip.boughtSeats(eventId);
            while(vipBought.next()){
            	bought_tickets.add(vipBought.getString("seat_no"));
            	b++;
            }
            ResultSet genBought = gen.boughtSeats(eventId);
            while(genBought.next()){
            	bought_tickets.add(genBought.getString("seat_no"));
            	b++;
            }
            
            out.println("<script>");
            out.println("alert('Available & Bought tickets for event with ID: " + eventId + "    Available Tickets:"  +
                        available_tickets.stream().map(Object::toString).collect(Collectors.joining(","))+" ("+a+" in total)    Bought Tickets: "+
                        bought_tickets.stream().map(Object::toString).collect(Collectors.joining(","))+" ("+b+" in total)');");
            out.println("window.history.back();"); // Go back to the original form page
            out.println("</script>");
        } catch (SQLException | ClassNotFoundException e) {
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<h2>Error occurred while finding seats.</h2>");
            out.println("<p>" + e.getMessage() + "</p>");
        }
    }

}