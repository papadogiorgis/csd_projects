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


public class PopularEvent extends HttpServlet {
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
        ReservationsTable res = new ReservationsTable();

        try {
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<script>");
            out.println("alert('The most popular event based on reservations is the event with Id: "+res.findPopularEvent()+"');");
            out.println("window.history.back();"); // Go back to the original form page
            out.println("</script>");
        } catch (SQLException | ClassNotFoundException e) {
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<h2>Error occurred while finding the event.</h2>");
            out.println("<p>" + e.getMessage() + "</p>");
        }
    }

}