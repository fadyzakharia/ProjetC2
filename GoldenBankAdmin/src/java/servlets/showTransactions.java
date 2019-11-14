/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import goldenbank.Transactions;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import service.TransactionsFacadeREST;

/**
 *
 * @author Rhea
 */
public class showTransactions extends HttpServlet {

    @EJB
    TransactionsFacadeREST transfacade;
    
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
//            out.println("<title>Servlet showTransactions</title>");            
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<h1>Servlet showTransactions at " + request.getContextPath() + "</h1>");
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
        processRequest(request, response);
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
        processRequest(request, response);
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

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.service(req, resp); //To change body of generated methods, choose Tools | Templates.
    
        String account_id = req.getParameter("account_id");
        String card_id = req.getParameter("card_id");
        
        if (account_id == null){
            List<Transactions> cardtrans = transfacade.findByCardId(card_id);
            
            if (cardtrans == null){
                req.setAttribute("cardtrans", null);
            }else {
                req.setAttribute("cardtrans", cardtrans);
            }
        } else {
            List<Transactions> transactionsFrom = transfacade.findByAccount1(account_id);
            List<Transactions> transactionsTo = transfacade.findByAccount2(account_id);
        
            if (transactionsFrom == null){
                req.setAttribute("transactionsFrom", null);
            } else {
                req.setAttribute("transactionsFrom", transactionsFrom);
            }
            if (transactionsTo == null){
                req.setAttribute("transactionsTo", null);
            } else {
                req.setAttribute("transactionsTo", transactionsTo);
            }
        }
        
        getServletContext().getRequestDispatcher("/transactions.jsp").forward(req,resp);
    }

    @Override
    public String toString() {
        return super.toString(); //To change body of generated methods, choose Tools | Templates.
    }
}
