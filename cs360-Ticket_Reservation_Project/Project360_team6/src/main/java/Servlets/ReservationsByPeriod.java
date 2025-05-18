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


public class ReservationsByPeriod extends HttpServlet {
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
    	String startDate = request.getParameter("startDate");
    	String endDate = request.getParameter("endDate");
        ReservationsTable res = new ReservationsTable();
        ArrayList<String> res_array = new ArrayList<>();
        int a=0;

        try {
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            
            ResultSet resByDate = res.findReservationsByDateRange(startDate, endDate);
            while(resByDate.next()){
            	res_array.add(resByDate.getString("reserv_id"));
            	a++;
            }
            
            out.println("<script>");
            out.println("alert('The reservations made between " + startDate + " and "  + endDate + " are these: "+
            		res_array.stream().map(Object::toString).collect(Collectors.joining(","))+" ("+a+" in total)');");
            out.println("window.history.back();"); // Go back to the original form page
            out.println("</script>");
        } catch (SQLException | ClassNotFoundException e) {
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<h2>Error occurred while viewing reservations.</h2>");
            out.println("<p>" + e.getMessage() + "</p>");
        }
    }
}