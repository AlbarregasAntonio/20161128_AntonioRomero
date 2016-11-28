/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.albarregas.controllers;

import es.albarregas.DAO.IAlumnosDAO;
import es.albarregas.DAO.IEquiposDAO;
import es.albarregas.beans.Alumno;
import es.albarregas.beans.Equipo;
import es.albarregas.daofactory.DAOFactory;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Antonio
 */
@WebServlet(name = "Eleccion", urlPatterns = {"/Eleccion"})
public class Eleccion extends HttpServlet {

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
            String opcion = request.getParameter("opcion");
            String url="";
            ArrayList<Alumno> alumnos = new ArrayList();
            ArrayList<Equipo> equipos = new ArrayList();   
            DAOFactory daof = DAOFactory.getDAOFactory(1);           
            IAlumnosDAO adao = daof.getAlumnosDAO();
            IEquiposDAO edao = daof.getEquiposDAO();
            
            switch (opcion) {
                case "c":
                    equipos = edao.getEquipo();
                    request.setAttribute("equipos", equipos);
                    url = "/JSPX/insertarAlumno.jspx";
                    break;
                case "r":
                    url="JSPX/elegirVisualizar.jspx";
                    break;
                case "upAlu":
                    alumnos = adao.getAlumno("");
                    request.setAttribute("alumnos", alumnos);                  
                    url="JSPX/elegirActualizarAlumno.jspx";
                    break;
                case "upEqui":                    
                    equipos = edao.getEquipo();
                    request.setAttribute("equipos", equipos);
                    url = "/JSPX/elegirActualizarEquipo.jspx";
                    break;
                case "d":
                    url = "/JSPX/elegirEliminar.jspx";
                    break;
            }

            request.setAttribute("opcion", opcion);
            request.getRequestDispatcher(url).forward(request, response);
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

}
