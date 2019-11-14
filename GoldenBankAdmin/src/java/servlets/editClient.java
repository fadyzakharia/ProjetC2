/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import goldenbank.Clients;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import service.ClientsFacadeREST;

/**
 *
 * @author Rhea
 */
public class editClient extends HttpServlet {

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
//            out.println("<title>Servlet editClient</title>");            
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<h1>Servlet editClient at " + request.getContextPath() + "</h1>");
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
        
        String clientId = request.getParameter("id");
        Clients client = clientsfacade.find(Integer.parseInt(clientId));
        
        request.setAttribute("client", client);
        //System.out.println(client.getEmail());
        getServletContext().getRequestDispatcher("/editClient.jsp").forward(request,response);
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
        
        HttpSession session = request.getSession(true);
        
        if (null == session.getAttribute("admin")){
            response.sendRedirect("GoldenBank/login.jsp");

        } else {
            String clientId = request.getParameter("id");
            Clients client = clientsfacade.find(Integer.parseInt(clientId));
                      
            String firstName = request.getParameter("firstname");
            String lastName = request.getParameter("lastname");
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            BigInteger phone = new BigInteger(request.getParameter("phonenumber"));
            String email = request.getParameter("email");
            String dateOfBirthStr = request.getParameter("birthdate");
		
            Date dateOfBirth = null;
		try {
                    dateOfBirth = new SimpleDateFormat("yyyy-MM-dd").parse(dateOfBirthStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
               		
            client.setFirstName(firstName);
            client.setLastName(lastName);
            client.setUsername(username);
            client.setPassword(password);
            client.setPhoneNumber(phone);
            client.setEmail(email);
            client.setDateOfBirth(dateOfBirth);
		
            clientsfacade.editClient(Integer.valueOf(clientId), client);
                
            List<Clients> clientsList = clientsfacade.findAll();
            request.setAttribute("clientsList", clientsList);

            getServletContext().getRequestDispatcher("/clients.jsp").forward(request, response);
        }
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
