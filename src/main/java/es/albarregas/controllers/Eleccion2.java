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
@WebServlet(name = "Eleccion2", urlPatterns = {"/Eleccion2"})
public class Eleccion2 extends HttpServlet {

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
            String opcion = request.getParameter("opcion");
            String url="";
            ArrayList<Alumno> alumnos = new ArrayList();
            ArrayList<Equipo> equipos = new ArrayList();   
            DAOFactory daof = DAOFactory.getDAOFactory(1);           
            IAlumnosDAO adao = daof.getAlumnosDAO();
            IEquiposDAO edao = daof.getEquiposDAO();
            
            out.println(opcion);
            
            switch (opcion) {
                case "verAluEqui":
                    url = "/Operacion";
                    break;
                case "verAlu":
                    url = "/JSPX/elegirVerAlumnos.jspx";
                    break;
                case "upAlu":
                    if(request.getParameter("registro")==null){
                        request.setAttribute("error", "No se ha seleccionado ningún alumno");
                        url="/JSPX/error.jspx";
                    }else{
                    Alumno alumno=new Alumno();
                    alumnos=adao.getAlumno("");
                    for(int i=0;i<alumnos.size();i++){
                        if(alumnos.get(i).getIdAlumno()==Integer.parseInt(request.getParameter("registro"))){
                            alumno.setIdAlumno(alumnos.get(i).getIdAlumno());
                            alumno.setNombre(alumnos.get(i).getNombre());
                            alumno.setGrupo(alumnos.get(i).getGrupo());
                        }
                    }
                    request.setAttribute("alumno", alumno);                    
                    url="/JSPX/actualizarAlumno.jspx";
                    }
                    break;                   
                case "upEqui":
                    if(request.getParameter("registro")==null){
                        request.setAttribute("error", "No se ha seleccionado ningún equipo");
                        url="/JSPX/error.jspx";
                    }else{
                        Equipo equipo = new Equipo();
                        equipos=edao.getEquipo();
                    for(int i=0;i<equipos.size();i++){
                        if(equipos.get(i).getIdEquipo()==Integer.parseInt(request.getParameter("registro"))){
                            equipo.setIdEquipo(equipos.get(i).getIdEquipo());
                            equipo.setMarca(equipos.get(i).getMarca());
                            equipo.setNumSerie(equipos.get(i).getNumSerie());
                        }
                    }                    
                    request.setAttribute("equipo", equipo);
                    url = "/JSPX/actualizarEquipo.jspx";
                    }
                    break;
                case "dAlu":
                    alumnos = adao.getAlumno("");
                    request.setAttribute("alumnos", alumnos);
                    url = "/JSPX/eliminarAlumnos.jspx";
                    break;
                case "dEqui":
                    equipos = edao.getEquipo();
                    request.setAttribute("equipos", equipos);
                    url = "/JSPX/eliminarEquipos.jspx";
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
