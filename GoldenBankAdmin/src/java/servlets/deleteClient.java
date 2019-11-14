/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import goldenbank.Accounts;
import goldenbank.ClientCard;
import goldenbank.Clients;
import goldenbank.Transactions;
import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import service.AccountsFacadeREST;
import service.ClientCardFacadeREST;
import service.ClientsFacadeREST;
import service.TransactionsFacadeREST;

/**
 *
 * @author Rhea
 */
public class deleteClient extends HttpServlet {

    @EJB
    ClientsFacadeREST clientsfacade;
    @EJB
    AccountsFacadeREST accountsfacade;
    @EJB
    ClientCardFacadeREST cardsfacade;
    @EJB 
    TransactionsFacadeREST transactionsfacade;
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
//            out.println("<title>Servlet deleteClient</title>");            
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<h1>Servlet deleteClient at " + request.getContextPath() + "</h1>");
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
        
        String clientId = req.getParameter("id");
		
	Clients client = clientsfacade.find(Integer.valueOf(clientId));
		
        if (accountsfacade.findByClientId(Integer.valueOf(clientId)) != null){
            List<Accounts> accounts = accountsfacade.findByClientId(Integer.valueOf(clientId));
            
            for (Accounts account : accounts){
                List<Transactions> accountrans = account.getTransactionsList();
                if (!accountrans.isEmpty()){
                    for(Transactions t : accountrans){
                        transactionsfacade.remove(t);
                    }
                }
                
                for(ClientCard card : account.getClientCardList()){
                    List<Transactions> cardtrans = card.getTransactionsList();
                    if (!cardtrans.isEmpty()){
                        for(Transactions t : cardtrans){
                            transactionsfacade.remove(t);
                        }
                    }
                    cardsfacade.remove(card);
                }
                
                accountsfacade.remove(account);
            }
        }
	
	clientsfacade.remove(client);
        
	List<Clients> clientsList = clientsfacade.findAll();;
        req.setAttribute("clientsList", clientsList);

        getServletContext().getRequestDispatcher("/clients.jsp").forward(req,resp);
    }
}
