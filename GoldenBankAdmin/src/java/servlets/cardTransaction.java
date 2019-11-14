/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import goldenbank.ClientCard;
import goldenbank.Currency;
import goldenbank.Transactions;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import service.AccountsFacadeREST;
import service.ClientCardFacadeREST;
import service.CurrencyFacadeREST;
import service.TransactionsFacadeREST;

/**
 *
 * @author Rhea
 */
public class cardTransaction extends HttpServlet {

    @EJB
    CurrencyFacadeREST currencyfacade;
    @EJB
    TransactionsFacadeREST transactionfacade;
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
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet cardTransaction</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet cardTransaction at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
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
        
        String card_id = request.getParameter("card_id");
        
        request.setAttribute("card_id", card_id);

        getServletContext().getRequestDispatcher("/cardTransaction.jsp").forward(request,response);
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
        
        String card_id = request.getParameter("card_id");
        String amount = request.getParameter("amount");
        String currency = request.getParameter("rdCurrency");
        String type = request.getParameter("rdType");
        String location = request.getParameter("location");
        
        Transactions transaction = new Transactions();
        ClientCard card = cardfacade.find(Long.valueOf(card_id));
        
        transaction.setDate(new Date());
        transaction.setAmount(Float.valueOf(amount));
        transaction.setLocation(location);
        transaction.setType(type);
        transaction.setCardId(card);
        
        Currency curr = currencyfacade.find(Integer.valueOf(currency));
        transaction.setCurrencyId(curr);
        
        transactionfacade.create(transaction);
                
        String account_id = card.getAccountId().getId().toString();
        
        if (type.trim().equals("Withdrawal")){
            if (curr.getId() == card.getAccountId().getCurrencyId().getId()){
                Float debit = Float.valueOf(amount) + card.getAccountId().getDebit();
                card.getAccountId().setDebit(debit);
                accountfacade.edit(Long.valueOf(account_id), card.getAccountId());
            } else {
                if (curr.getId() == 101 && card.getAccountId().getCurrencyId().getId() == 102){
                    Float debit = (Float.valueOf(amount) * curr.getRate()) + card.getAccountId().getDebit();
                    card.getAccountId().setDebit(debit);
                    accountfacade.edit(Long.valueOf(account_id), card.getAccountId());
                } else if (curr.getId() == 102 && card.getAccountId().getCurrencyId().getId() == 101){
                    Float debit = (Float.valueOf(amount) / curr.getRate()) + card.getAccountId().getDebit();
                    card.getAccountId().setDebit(debit);
                    accountfacade.edit(Long.valueOf(account_id), card.getAccountId());
                }
            }
        }
        if (type.trim().equals("Deposit")){
            if (curr.getId() == card.getAccountId().getCurrencyId().getId()){
                Float credit = Float.valueOf(amount) + card.getAccountId().getCredit();
                card.getAccountId().setCredit(credit);
                accountfacade.edit(Long.valueOf(account_id), card.getAccountId());
            } else {
                if (curr.getId() == 101 && card.getAccountId().getCurrencyId().getId() == 102){
                    Float credit = (Float.valueOf(amount) * curr.getRate()) + card.getAccountId().getCredit();
                    card.getAccountId().setCredit(credit);
                    accountfacade.edit(Long.valueOf(account_id), card.getAccountId());
                } else if (curr.getId() == 102 && card.getAccountId().getCurrencyId().getId() == 101){
                    Float credit = (Float.valueOf(amount) / curr.getRate()) + card.getAccountId().getCredit();
                    card.getAccountId().setCredit(credit);
                    accountfacade.edit(Long.valueOf(account_id), card.getAccountId());
                }
            }
        }
        
        
        List<ClientCard> cardsList = cardfacade.findByAccountId(account_id);
        
        if (cardsList == null){
            request.setAttribute("cardsList", null);
        } else {
            request.setAttribute("cardsList", cardsList);
        }
        
        request.setAttribute("account_id", account_id);

        getServletContext().getRequestDispatcher("/cards.jsp").forward(request,response);
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
