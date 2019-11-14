/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import goldenbank.AtmBranch;
import goldenbank.BankBranch;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import service.AtmBranchFacadeREST;
import service.BankBranchFacadeREST;

/**
 *
 * @author Rhea
 */
public class addAtm extends HttpServlet {

    @EJB
    BankBranchFacadeREST bankfacade;
    @EJB
    AtmBranchFacadeREST atmfacade;
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
            out.println("<title>Servlet addAtm</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet addAtm at " + request.getContextPath() + "</h1>");
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
        //super.service(req, resp); //To change body of generated methods, choose Tools | Templates.
        
        String longitude = req.getParameter("longitude");
        String latitude = req.getParameter("latitude");
        String description = req.getParameter("description");
        String genre = req.getParameter("rdGenre");
        String nature = req.getParameter("rdNature");
        
        AtmBranch branch = new AtmBranch();
        
        branch.setLongitude(Float.valueOf(longitude));
        branch.setLatitude(Float.valueOf(latitude));
        branch.setDescription(description);
        branch.setGenre(genre);
        branch.setNature(nature);
        
        atmfacade.create(branch);
        
        List<BankBranch> bankList = bankList = bankfacade.findAll();
        List<AtmBranch> atmList = atmList = atmfacade.findAll();
                
        req.setAttribute("bankList", bankList);
        req.setAttribute("atmList", atmList);

        getServletContext().getRequestDispatcher("/branches.jsp").forward(req,resp);
    }
}