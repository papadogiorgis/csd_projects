package Servlets;

import Tables.CardTable;
import Tables.CustTable;
import Tables.EventsTable;
import Tables.ReservationsTable;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;



public class MostProfitable extends HttpServlet {


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
        try{
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            String mostProfit = res.findMostProfitable(startDate, endDate);
            out.println("<script>");
            out.println("alert('Most profitable:\\n" +mostProfit+ "');");
            out.println("window.history.back();"); // Go back to the original form page
            out.println("</script>");
        } catch (Exception e) {
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<h2>Error occurred while finding the event.</h2>");
            out.println("<p>" + e.getMessage() + "</p>");
        }
    }


    
}
