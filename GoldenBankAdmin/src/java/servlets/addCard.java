/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import goldenbank.ClientCard;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import service.AccountsFacadeREST;
import service.ClientCardFacadeREST;

/**
 *
 * @author Rhea
 */
public class addCard extends HttpServlet {

    @EJB
    ClientCardFacadeREST cardfacade;
    @EJB
    AccountsFacadeREST accountfacade;
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        response.setContentType("text/html;charset=UTF-8");
//        try (PrintWriter out = response.getWriter()) {
//            /* TODO output your page here. You may use following sample code. */
//            out.println("<!DOCTYPE html>");
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Servlet addCard</title>");            
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<h1>Servlet addCard at " + request.getContextPath() + "</h1>");
//            out.println("</body>");
//            out.println("</html>");
//        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
        
        String account_id = request.getParameter("id");
        request.setAttribute("account_id", account_id);
        
        getServletContext().getRequestDispatcher("/newCard.jsp").forward(request,response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
        
        String account_id = request.getParameter("id");
        String monthly = request.getParameter("monthly");
        String daily = request.getParameter("daily");
        String type = request.getParameter("rdType");
        
        Random r = new Random (System.currentTimeMillis());
        Integer number = (1 + r.nextInt(2)) * 10000000 + r.nextInt(10000000);
                
        ClientCard card = new ClientCard();
        
        card.setId(Long.valueOf(number));
        card.setDayLimit(Float.valueOf(monthly));
        card.setMonthlyLimit(Float.valueOf(daily));
        card.setType(type);
        card.setAccountId(accountfacade.find(Long.valueOf(account_id)));
                
        cardfacade.create(card);
        
        List<ClientCard> cardsList = cardfacade.findByAccountId(account_id);
        
        if (cardsList == null){
            request.setAttribute("cardsList", null);
        } else {
            request.setAttribute("cardsList", cardsList);
        }
        
        request.setAttribute("account_id", account_id);
        
        getServletContext().getRequestDispatcher("/cards.jsp").forward(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
