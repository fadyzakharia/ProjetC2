/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import goldenbank.Accounts;
import goldenbank.BankBranch;
import goldenbank.Clients;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import service.AccountsFacadeREST;
import service.BankBranchFacadeREST;
import service.ClientsFacadeREST;
import service.CurrencyFacadeREST;

/**
 *
 * @author Rhea
 */
public class addAccount extends HttpServlet {

    @EJB
    ClientsFacadeREST clientsfacade;
    @EJB
    AccountsFacadeREST accountsfacade;
    @EJB 
    BankBranchFacadeREST branchfacade;
    @EJB 
    CurrencyFacadeREST currencyfacade;
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
//            out.println("<title>Servlet addAccount</title>");            
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<h1>Servlet addAccount at " + request.getContextPath() + "</h1>");
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
        
        String client_id = request.getParameter("id");
        request.setAttribute("client_id", client_id);
        
        List<BankBranch> branches = branchfacade.findAll();
        request.setAttribute("branches", branches);
        
        getServletContext().getRequestDispatcher("/newAccount.jsp").forward(request,response);
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
        
        String currency = request.getParameter("rdCurrency");
        String amount = request.getParameter("amount");
        String branch = request.getParameter("branch");
        String client_id = request.getParameter("id");
        String type = request.getParameter("rdType");
        
        Random r = new Random (System.currentTimeMillis());
        Integer number = (1 + r.nextInt(2)) * 1000 + r.nextInt(1000);
       
        String account_id = currency + client_id + number.toString() + "00" + branch;
                
        Accounts account = new Accounts();
        account.setId(Long.valueOf(account_id));
        account.setClientId(clientsfacade.find(Integer.valueOf(client_id)));
        account.setCurrencyId(currencyfacade.find(Integer.valueOf(currency)));
        account.setCredit(Float.valueOf(amount));
        account.setDebit(Float.valueOf("0"));
        account.setBranchId(branchfacade.find(Integer.valueOf(branch)));
        account.setType(type);
                
        accountsfacade.create(account);
        
        List<Accounts> accountsList = accountsfacade.findByClientId(Integer.valueOf(client_id));
        request.setAttribute("accountsList", accountsList);
        request.setAttribute("client_id", client_id);
        
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
