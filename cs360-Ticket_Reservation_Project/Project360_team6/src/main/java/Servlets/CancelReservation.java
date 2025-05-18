package Servlets;

import mainClasses.*;
import Tables.*;
import java.sql.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class CancelReservation extends HttpServlet {

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
        String resIdStr = request.getParameter("resId");
        if (resIdStr == null || resIdStr.isEmpty()) {
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<h2>Error: Reservation ID is missing.</h2>");
        }

        try {
            int resId = Integer.parseInt(resIdStr);

            ReservationsTable resTable = new ReservationsTable(); 
            GeneralTicketsTable genTable = new GeneralTicketsTable();
            VIPTicketsTable vipTable = new VIPTicketsTable();

            try {
                response.setContentType("text/html");
                PrintWriter out = response.getWriter();
                genTable.cancelTicketsWithResId(resId);
                vipTable.cancelTicketsWithResId(resId);
                resTable.cancelReservation(resId);
                out.println("<script>");
                out.println("alert('Reservation with ID: " + resId + " cancelled successfully. The amount has been returned.');");
                out.println("window.history.back();");
                out.println("</script>");
            } catch (SQLException | ClassNotFoundException e) {
                response.setContentType("text/html");
                PrintWriter out = response.getWriter();
                out.println("<h2>Error occurred while canceling the reservation.</h2>");
                out.println("<p>" + e.getMessage() + "</p>");
            }
        } catch (NumberFormatException e) {
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<h2>Error: Invalid reservation ID format.</h2>");
            out.println("<p>Please enter a valid numeric reservation ID.</p>");
        }
    }


}