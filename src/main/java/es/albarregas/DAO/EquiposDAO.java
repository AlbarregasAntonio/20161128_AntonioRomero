/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.albarregas.DAO;

import es.albarregas.beans.Equipo;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Antonio
 */
public class EquiposDAO implements IEquiposDAO{
    
    Statement sentencia = null;
    ResultSet resultado = null;
    ConnectionFactory conexion = null;

    @Override
    public ArrayList<Equipo> getEquipo() {
        ArrayList <Equipo> equipos= new ArrayList();
        try {
            sentencia = conexion.getConnection().createStatement();
            resultado = sentencia.executeQuery("select * from equipos");

            while (resultado.next()) {
                Equipo equipo = new Equipo();
                equipo.setIdEquipo(resultado.getInt("idEquipo"));
                equipo.setMarca(resultado.getString("marca"));
                equipo.setNumSerie(resultado.getString("numSerie"));
                equipos.add(equipo);
            }
            ConnectionFactory.closeConnection();
        } catch (SQLException e) {
            System.out.println("Problemas al visualizar");
            e.printStackTrace();
        }
        return equipos;
    }

    @Override
    public void deleteEquipo(String where) {
        try {
            sentencia = conexion.getConnection().createStatement();
            sentencia.executeUpdate("delete from equipos " + where);
        } catch (SQLException e) {
            System.out.println("Problemas al actualizar");
            e.printStackTrace();
        }
    }

    @Override
    public void updateEquipo(Equipo equipo) {
        try {
            sentencia = conexion.getConnection().createStatement();
            sentencia.executeUpdate("Update equipos set marca='"+equipo.getMarca()+"', numSerie='"+
                    equipo.getNumSerie()+"' where idEquipo="+equipo.getIdEquipo());
        } catch (SQLException e) {
            System.out.println("Problemas al actualizar");
            e.printStackTrace();
        }
    }

}
