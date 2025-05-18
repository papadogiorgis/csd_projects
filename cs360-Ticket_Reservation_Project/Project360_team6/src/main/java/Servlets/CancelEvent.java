package Servlets;

import mainClasses.*;
import Tables.*;
import java.sql.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class CancelEvent extends HttpServlet {

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
        String eventIdStr = request.getParameter("eventId");
        if (eventIdStr == null || eventIdStr.isEmpty()) {
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<h2>Error: Event ID is missing.</h2>");
            return;
        }

        try {
            int eventId = Integer.parseInt(eventIdStr);

            EventsTable eventTable = new EventsTable();
            ReservationsTable resTable = new ReservationsTable();
            GeneralTicketsTable genTable = new GeneralTicketsTable();
            VIPTicketsTable vipTable = new VIPTicketsTable();

            try {
                response.setContentType("text/html");
                PrintWriter out = response.getWriter();
                genTable.deleteTicketsWithEvId(eventId);
                vipTable.deleteTicketsWithEvId(eventId);
                resTable.deleteReservation_with_eventId(eventId);
                eventTable.cancelEvent(eventId);
                out.println("<script>");
                out.println("alert('Event with ID: " + eventId + " cancelled successfully');");
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