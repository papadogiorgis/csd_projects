package Servlets;

import mainClasses.Event;
import Tables.CustTable;
import Tables.EventsTable;
import Tables.ReservationsTable;

import java.io.*;
import java.sql.SQLException;
import javax.servlet.*;
import javax.servlet.http.*;

public class EventCreation extends HttpServlet {


    public void init() throws ServletException{
        EventsTable eve = new EventsTable();
        CustTable customers = new CustTable();
        ReservationsTable res = new ReservationsTable();
        
        try{
            customers.createTable();
            eve.createTable();
            res.createTable();
        }catch(Exception e){

        }
        
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("eventName");
        String date = request.getParameter("eventDate");
        String time = request.getParameter("eventTime");
        String type = request.getParameter("eventType");
        int capacity = Integer.parseInt(request.getParameter("totalCapacity"));
        int VIPCapacity = Integer.parseInt(request.getParameter("vipCapacity"));
        int VIPCost = Integer.parseInt(request.getParameter("vipTicketPrice"));
        int GenCapacity = Integer.parseInt(request.getParameter("generalCapacity"));
        int GenCost = Integer.parseInt(request.getParameter("generalTicketPrice"));

        Event newEvent = new Event(name,date,time,type,capacity,VIPCapacity,GenCapacity,VIPCost,GenCost);
        EventsTable eventTable = new EventsTable();

        try {
            int eventID = eventTable.addNewEvent(newEvent);
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<script>");
            out.println("alert('Event created successfully! Event ID: " + eventID + "');");
            out.println("window.history.back();"); // Go back to the original form page
            out.println("window.history.back();"); // Go back to the original menu page
            out.println("</script>");
        } catch (SQLException | ClassNotFoundException e) {
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<h2>Error occurred while creating the event.</h2>");
            out.println("<p>" + e.getMessage() + "</p>");
        }
    }


}