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


public class RevenueByEvent extends HttpServlet {
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
        ReservationsTable res = new ReservationsTable();
        String cost_string;
        double sum=0.0;
        double parsed;


        try {
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            
            ResultSet ResCosts = res.findResByEvent(eventId);
            while(ResCosts.next()){
                cost_string = ResCosts.getString("reserv_cost");
                parsed = Double.parseDouble(cost_string);
                sum = sum + parsed;
            }
            
            out.println("<script>");
            out.println("alert('The total revenue from event with ID " + eventId + " is: "+sum+"');");
            out.println("window.history.back();"); // Go back to the original form page
            out.println("</script>");
        } catch (SQLException | ClassNotFoundException e) {
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<h2>Error occurred while finding revenues.</h2>");
            out.println("<p>" + e.getMessage() + "</p>");
        }
    }

}