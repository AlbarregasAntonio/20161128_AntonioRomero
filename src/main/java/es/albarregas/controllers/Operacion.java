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
@WebServlet(name = "Operacion", urlPatterns = {"/Operacion"})
public class Operacion extends HttpServlet {

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

            ArrayList<Alumno> alumnos = new ArrayList();
            ArrayList<Equipo> equipos = new ArrayList();
            DAOFactory daof = DAOFactory.getDAOFactory(1);
            IAlumnosDAO adao = daof.getAlumnosDAO();
            IEquiposDAO edao = daof.getEquiposDAO();
            String url = "/";
            String where = "";
            Equipo equipo=null;
            Alumno alumno=null;
            StringBuilder clausulaWhere = new StringBuilder();
            String opcion=request.getParameter("opcion");

            if (request.getParameter("cancelar") == null) {
                
                switch (opcion) {
                    case "verAluEqui":
                        alumnos = adao.getAlumnoEquipo();
                        request.setAttribute("alumnos", alumnos);
                        url = "/JSPX/verAlumnosEquipos.jspx";
                        break;
                    case "verAlu":
                        if (!request.getParameter("numero").equals("")) {
                            where = " limit " + request.getParameter("numero");
                        }
                        alumnos = adao.getAlumno(where);
                        request.setAttribute("alumnos", alumnos);
                        url = "/JSPX/verAlumnos.jspx";
                        break;
                    case "c":
                        equipo = new Equipo();
                        alumno = new Alumno();
                        alumno.setNombre(request.getParameter("nombre"));
                        alumno.setGrupo(request.getParameter("grupo"));
                        equipo.setIdEquipo(Integer.parseInt(request.getParameter("equipo")));
                        equipos = edao.getEquipo();
                        for (int i = 0; i < equipos.size(); i++) {
                            if (equipo.getIdEquipo() == equipos.get(i).getIdEquipo()) {
                                equipo.setMarca(equipos.get(i).getMarca());
                            }
                        }
                        alumno.setEquipo(equipo);
                        adao.addAlumno(alumno);                       
                        request.setAttribute("alumno", alumno);
                        url = "/JSPX/finInsertar.jspx";
                        break;
                    case "upAlu":
                        alumno = new Alumno();
                        where = " where idAlumno=" + request.getParameter("idAlumno");
                        alumnos = adao.getAlumno(where);
                        if (!alumnos.get(0).getGrupo().equals(request.getParameter("grupo")) || !alumnos.get(0).getNombre().equals(request.getParameter("nombre"))) {
                            alumno.setIdAlumno(Integer.parseInt(request.getParameter("idAlumno")));
                            alumno.setNombre(request.getParameter("nombre"));
                            alumno.setGrupo(request.getParameter("grupo"));
                            adao.updateAlumno(alumno);
                        }
                        request.setAttribute("alumno", alumno);
                        url = "/JSPX/finActualizarAlu.jspx";
                        break;
                    case "upEqui":
                        equipo = new Equipo();
                        equipos = edao.getEquipo();
                        out.println("llegamos");
                        for (int i = 0; i < equipos.size(); i++) {
                            if (equipos.get(i).getIdEquipo() == Integer.parseInt(request.getParameter("idEquipo"))) {
                                out.println("encontramos equipo");
                                if (!equipos.get(i).getMarca().equals(request.getParameter("marca")) || !equipos.get(i).getNumSerie().equals(request.getParameter("numSerie"))) {
                                    equipo.setIdEquipo(Integer.parseInt(request.getParameter("idEquipo")));
                                    equipo.setMarca(request.getParameter("marca"));
                                    equipo.setNumSerie(request.getParameter("numSerie"));
                                    edao.updateEquipo(equipo);
                                }
                            }
                        }                       
                        request.setAttribute("equipo", equipo);
                        url = "/JSPX/finActualizarEqui.jspx";
                        break;
                    case "dAlu":
                        if (request.getParameter("registro") == null) {
                            request.setAttribute("error", "No se ha seleccionado ningún alumno");
                            url = "/JSPX/error.jspx";
                        } else {
                            String[] listado = request.getParameterValues("registro");
                            clausulaWhere = new StringBuilder(" where idAlumno in (");
                            for (String idAlumno : listado) {
                                clausulaWhere.append("'");
                                clausulaWhere.append(idAlumno);
                                clausulaWhere.append("',");
                            }
                            clausulaWhere.replace(clausulaWhere.length() - 1, clausulaWhere.length(), ")");
                            adao.deleteAlumno(clausulaWhere.toString());
                            request.setAttribute("numero", listado.length);
                            url = "/JSPX/finEliminarAlumnos.jspx";
                        }
                        break;
                    case "dEqui":
                        if (request.getParameter("registro") == null) {
                            request.setAttribute("error", "No se ha seleccionado ningún alumno");
                            url = "/JSPX/error.jspx";
                        } else {
                            String[] listado = request.getParameterValues("registro");
                            clausulaWhere = new StringBuilder(" where idEquipo in (");
                            for (String idEquipo : listado) {
                                clausulaWhere.append("'");
                                clausulaWhere.append(idEquipo);
                                clausulaWhere.append("',");
                            }
                            clausulaWhere.replace(clausulaWhere.length() - 1, clausulaWhere.length(), ")");
                            edao.deleteEquipo(clausulaWhere.toString());
                            request.setAttribute("numero", listado.length);
                            url = "/JSPX/finEliminarEquipos.jspx";
                        }
                        break;
                }
            }
                
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
