/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import goldenbank.ProductsCards;
import goldenbank.ProductsLoans;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import service.ProductsCardsFacadeREST;
import service.ProductsLoansFacadeREST;

/**
 *
 * @author Rhea
 */
public class addPCard extends HttpServlet {
    
    @EJB
    ProductsLoansFacadeREST loansfacade;
    @EJB
    ProductsCardsFacadeREST cardsfacade;
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
            out.println("<title>Servlet addPCard</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet addPCard at " + request.getContextPath() + "</h1>");
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
     //   super.service(req, resp); //To change body of generated methods, choose Tools | Templates.
        
        String name = req.getParameter("name");
        String type = req.getParameter("rdType");
        String monthly = req.getParameter("monthly");
        String daily = req.getParameter("daily");
        String description = req.getParameter("description");
        
        ProductsCards card = new ProductsCards();
        
        card.setName(name);
        card.setType(type);
        card.setMonthlyLimit(Float.valueOf(monthly));
        card.setDayLimit(Float.valueOf(daily));
        card.setDescription(description);
        
        cardsfacade.create(card);
        
        List<ProductsLoans> loans = loansfacade.findAll();
        List<ProductsCards> cards = cardsfacade.findAll();
                
        req.setAttribute("loans", loans);
        req.setAttribute("cards", cards);

        getServletContext().getRequestDispatcher("/products.jsp").forward(req,resp);
    }
}
