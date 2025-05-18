package Servlets;

import mainClasses.Card;
import Tables.CardTable;
import Tables.CustTable;
import Tables.EventsTable;
import Tables.ReservationsTable;

import java.io.*;
import java.sql.SQLException;
import javax.servlet.*;
import javax.servlet.http.*;

public class ExtraCard extends HttpServlet {


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
        int customerId = Integer.parseInt(request.getParameter("customerId"));
        String cardNumber= request.getParameter("cardNumber");
        String expiryDate = request.getParameter("expiryDate");
        String cvv = request.getParameter("cvv");


        CardTable cards = new CardTable();

        try {
            cards.createTable();
            Card card = new Card(customerId,cardNumber,expiryDate,cvv);
            cards.addNewCard(card, customerId);
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<script>");
            out.println("alert('Card added successfully! Customer ID: " + customerId + "');");
            out.println("window.history.back();"); // Go back to the original form page
            out.println("</script>");
        } catch (SQLException | ClassNotFoundException e) {
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<h2>Error occurred while adding the card.</h2>");
            out.println("<p>" + e.getMessage() + "</p>");
        }
    }

}