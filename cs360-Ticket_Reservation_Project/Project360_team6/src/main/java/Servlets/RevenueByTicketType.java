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


public class RevenueByTicketType extends HttpServlet {
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
        String ticketType = request.getParameter("ticketType");
        boolean allEvents = request.getParameter("allEvents") != null;
        ReservationsTable res = new ReservationsTable();
        GeneralTicketsTable gen = new GeneralTicketsTable();
        VIPTicketsTable vip = new VIPTicketsTable();
        String cost_string;
        double amount=0.0;
        double parsed;


        try {
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            
            if(allEvents) {
            	if(ticketType.equals("VIP")) {
            		amount = vip.totalProfits(0);
                    out.println("<script>");
                    out.println("alert('The revenue of VIP tickets from all events is: "+amount+"');");
                    out.println("window.history.back();"); // Go back to the original form page
                    out.println("</script>");
            	}else {
            		amount = gen.totalProfits(0);
                    out.println("<script>");
                    out.println("alert('The revenue of general tickets from all events is: "+amount+"');");
                    out.println("window.history.back();"); // Go back to the original form page
                    out.println("</script>");
            	}
            }else {
            	int eventId = Integer.parseInt(request.getParameter("eventId"));
            	if(ticketType.equals("VIP")) {
            		amount = vip.totalProfits(eventId);
                    out.println("<script>");
                    out.println("alert('The revenue of VIP tickets from event with ID " + eventId + " is: "+amount+"');");
                    out.println("window.history.back();"); // Go back to the original form page
                    out.println("</script>");
            	}else {
            		amount = gen.totalProfits(eventId);
                    out.println("<script>");
                    out.println("alert('The revenue of general tickets from event with ID " + eventId + " is: "+amount+"');");
                    out.println("window.history.back();"); // Go back to the original form page
                    out.println("</script>");
            	}
            }
        } catch (SQLException | ClassNotFoundException e) {
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<h2>Error occurred while finding revenues.</h2>");
            out.println("<p>" + e.getMessage() + "</p>");
        }
    }
}