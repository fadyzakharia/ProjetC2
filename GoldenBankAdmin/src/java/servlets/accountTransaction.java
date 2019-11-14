/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import goldenbank.Accounts;
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
public class accountTransaction extends HttpServlet {

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
            out.println("<title>Servlet accountTransaction</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet accountTransaction at " + request.getContextPath() + "</h1>");
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
        
        String account_id = request.getParameter("account_id");
        
        Accounts account = accountfacade.find(Long.valueOf(account_id));
        
        List<Accounts> accountsList = accountfacade.findByClientId(account.getClientId().getId());
        
        accountsList.remove(account);
                        
        request.setAttribute("accountsList", accountsList);
        request.setAttribute("account_id", account_id);

        getServletContext().getRequestDispatcher("/accountTransaction.jsp").forward(request,response);
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
        
        String account_id = request.getParameter("account_id");
        String amount = request.getParameter("amount");
        String currency = request.getParameter("rdCurrency");
        String type = request.getParameter("rdType");
        String location = request.getParameter("location");
        
        Accounts account1 = accountfacade.find(Long.valueOf(account_id));
        Accounts account2 = null;
        String account2_id = null;
        
        Transactions transaction = new Transactions();
            
            transaction.setDate(new Date());
            transaction.setAmount(Float.valueOf(amount));
            transaction.setLocation(location);
            transaction.setType(type);
            transaction.setAccount1(account1);
            
            Currency curr = currencyfacade.find(Integer.valueOf(currency));
            transaction.setCurrencyId(curr);
            
        if (request.getParameter("account2_id") == null){
            transactionfacade.create(transaction);
        } else {
        
            account2_id = request.getParameter("account2_id");
            account2 = accountfacade.find(Long.valueOf(account2_id));
            transaction.setAccount2(account2);
            
            transactionfacade.create(transaction);
        }
        
        if (type.trim().equals("Withdrawal")){
            //if the transaction amount currency is equal to the accounts currency
            if (curr.getId() == account1.getCurrencyId().getId()){
                Float debit = Float.valueOf(amount) + account1.getDebit();
                account1.setDebit(debit);
                accountfacade.edit(Long.valueOf(account_id), account1);
            } else {
                if (curr.getId() == 101 && account1.getCurrencyId().getId() == 102){
                    Float debit = (Float.valueOf(amount) * curr.getRate()) + account1.getDebit();
                    account1.setDebit(debit);
                    accountfacade.edit(Long.valueOf(account_id), account1);
                } else if (curr.getId() == 102 && account1.getCurrencyId().getId() == 101){
                    Float debit = (Float.valueOf(amount) / curr.getRate()) + account1.getDebit();
                    account1.setDebit(debit);
                    accountfacade.edit(Long.valueOf(account_id), account1);
                }
            }
        }
        if (type.trim().equals("Deposit")){
            if (curr.getId() == account1.getCurrencyId().getId()){
                Float credit = Float.valueOf(amount) + account1.getCredit();
                account1.setCredit(credit);
                accountfacade.edit(Long.valueOf(account_id), account1);
            } else {
                if (curr.getId() == 101 && account1.getCurrencyId().getId() == 102){
                    Float credit = (Float.valueOf(amount) * curr.getRate()) + account1.getCredit();
                    account1.setCredit(credit);
                    accountfacade.edit(Long.valueOf(account_id), account1);
                } else if (curr.getId() == 102 && account1.getCurrencyId().getId() == 101){
                    Float credit = (Float.valueOf(amount) / curr.getRate()) + account1.getCredit();
                    account1.setCredit(credit);
                    accountfacade.edit(Long.valueOf(account_id), account1);
                }
            }
        }
        if (type.trim().equals("Transfer")){
            
            if (curr.getId() == account1.getCurrencyId().getId()){
                Float debit = Float.valueOf(amount) + account1.getDebit();
                account1.setDebit(debit);
                accountfacade.edit(Long.valueOf(account_id), account1);
            } else {
                if (curr.getId() == 101 && account1.getCurrencyId().getId() == 102){
                    Float debit = (Float.valueOf(amount) * curr.getRate()) + account1.getDebit();
                    account1.setDebit(debit);
                    accountfacade.edit(Long.valueOf(account_id), account1);
                } else if (curr.getId() == 102 && account1.getCurrencyId().getId() == 101){
                    Float debit = (Float.valueOf(amount) / curr.getRate()) + account1.getDebit();
                    account1.setDebit(debit);
                    accountfacade.edit(Long.valueOf(account_id), account1);
                }
            }
            
            if (curr.getId() == account2.getCurrencyId().getId()){
                Float credit = Float.valueOf(amount) + account2.getCredit();
                account2.setCredit(credit);
                accountfacade.edit(Long.valueOf(account2_id), account2);
            } else {
                if (curr.getId() == 101 && account2.getCurrencyId().getId() == 102){
                    Float credit = (Float.valueOf(amount) * curr.getRate()) + account2.getCredit();
                    account2.setCredit(credit);
                    accountfacade.edit(Long.valueOf(account2_id), account2);
                } else if (curr.getId() == 102 && account2.getCurrencyId().getId() == 101){
                    Float credit = (Float.valueOf(amount) / curr.getRate()) + account2.getCredit();
                    account1.setCredit(credit);
                    accountfacade.edit(Long.valueOf(account2_id), account2);
                }
            }
        }        
        
        
        List<Accounts> accountsList = accountfacade.findByClientId(account1.getClientId().getId());
              
        request.setAttribute("accountsList", accountsList);
        request.setAttribute("client_id", account1.getClientId().getId());

        getServletContext().getRequestDispatcher("/accounts.jsp").forward(request, response);
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
