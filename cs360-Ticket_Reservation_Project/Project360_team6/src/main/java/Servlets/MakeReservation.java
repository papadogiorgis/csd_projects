package Servlets;

import mainClasses.*;
import Tables.CardTable;
import Tables.CustTable;
import Tables.EventsTable;

import Tables.ReservationsTable;

import java.sql.*;
import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;



public class MakeReservation extends HttpServlet {


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
        int custId = Integer.parseInt(request.getParameter("customerId"));
        int eventId = Integer.parseInt(request.getParameter("eventId"));
        int genTicketsCount = Integer.parseInt(request.getParameter("generalTickets"));
        int vipTicketsCount = Integer.parseInt(request.getParameter("vipTickets"));

        Reservations res = new Reservations(custId,eventId,genTicketsCount,vipTicketsCount);
        ReservationsTable resTable = new ReservationsTable();

        try {
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            int resId = resTable.addNewReservation(res);
            out.println("<script>");
            out.println("alert('Reservation with ID:" + resId + "added succesfully! Total Cost:"+res.get_reserv_cost()+"');");
            out.println("window.history.back();"); // Go back to the original form page
            out.println("</script>");
        } catch (SQLException | ClassNotFoundException e) {
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<h2>Error occurred while reserving the reservation.</h2>");
            out.println("<p>" + e.getMessage() + "</p>");
        }
    }


}
