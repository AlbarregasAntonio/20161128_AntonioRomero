/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.albarregas.DAO;

import es.albarregas.beans.Equipo;
import java.util.ArrayList;

/**
 *
 * @author Antonio
 */
public interface IEquiposDAO {
    public ArrayList<Equipo> getEquipo();
    public void updateEquipo(Equipo equipo);
    public void deleteEquipo(String where);
}
