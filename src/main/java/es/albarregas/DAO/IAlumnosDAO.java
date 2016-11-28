/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.albarregas.DAO;

import es.albarregas.beans.Alumno;
import java.util.ArrayList;

/**
 *
 * @author Antonio
 */
public interface IAlumnosDAO {
    public void addAlumno(Alumno alumno);
    public ArrayList<Alumno> getAlumno(String where);
    public ArrayList<Alumno> getAlumnoEquipo();
    public void updateAlumno(Alumno alumno);
    public void deleteAlumno(String where);
}
