/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import goldenbank.Clients;
import java.io.IOException;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import service.ClientsFacadeREST;

/**
 *
 * @author Rhea
 */
public class newClient extends HttpServlet {
    @EJB
    ClientsFacadeREST clientsfacade;
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
//            out.println("<title>Servlet newClient</title>");            
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<h1>Servlet newClient at " + request.getContextPath() + "</h1>");
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
        
        String firstName = req.getParameter("firstname");
        String lastName = req.getParameter("lastname");
        String username = req.getParameter("user");
        String password = req.getParameter("pass");
        Integer phone = Integer.parseInt(req.getParameter("phonenumber"));
        String email = req.getParameter("email");
        String dateOfBirthStr = req.getParameter("birthdate");
		
        Date dateOfBirth = null;
	try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            dateOfBirth = sdf.parse(dateOfBirthStr);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
	}
               		
        Clients client = new Clients();
        
        client.setFirstName(firstName);
        client.setLastName(lastName);
        client.setUsername(username);
        client.setPassword(password);
        client.setPhoneNumber(BigInteger.valueOf(phone));
        client.setEmail(email);
        client.setDateOfBirth(dateOfBirth);
		
        clientsfacade.create(client);
                
        List<Clients> clientsList = clientsfacade.findAll();;
        req.setAttribute("clientsList", clientsList);

        getServletContext().getRequestDispatcher("/clients.jsp").forward(req, resp);
    }
}
