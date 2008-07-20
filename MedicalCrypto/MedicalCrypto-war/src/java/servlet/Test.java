/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import beans.test.TestRemote;
import entities.medical.Persons;
import entities.medical.Treatment;
import entities.medical.Visit;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Piotrek
 */
public class Test extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Test</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Test at " + request.getContextPath() + "</h1>");
            Object o = new InitialContext().lookup("beans.test.TestRemote");
            TestRemote tr = (TestRemote) PortableRemoteObject.narrow(o, TestRemote.class);
            byte[] iv = new byte[16];
            BigInteger idKeyManifest = new BigInteger("11");
            BigInteger idPerson = new BigInteger("2");
            Persons p = new Persons("jan", "123", "Jan", "Kowalski", "Nieznana", 1, "Warszawa", 12345, 1234567, 84010101122L, "cos", iv);
            out.println("dodaje osobe:= " + tr.addPerson(p, idKeyManifest));
            Visit v = new Visit("nieznana", "brak diagnozy", Calendar.getInstance().getTime(), iv);
            //out.println("dodaje wizyte:= " + tr.addVisit(v, idPerson, idPerson, idKeyManifest));
            Treatment t = new Treatment("apap", "2x na dobe", iv);
            //out.println("dodaje leczenie:= " + tr.addTreatment(t, new BigInteger("2"), idKeyManifest));
            out.println("</body>");
            out.println("</html>");

        } catch (NamingException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            out.println(ex.getMessage());
        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     */
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
