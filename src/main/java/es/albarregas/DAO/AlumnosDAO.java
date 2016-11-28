/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.albarregas.DAO;

import es.albarregas.beans.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Antonio
 */
public class AlumnosDAO implements IAlumnosDAO{

    Statement sentencia = null;
    ResultSet resultado = null;
    ConnectionFactory conexion = null;
    
        
    @Override
    public void addAlumno(Alumno alumno) {
        Equipo equipo=new Equipo();
        try {
        sentencia = conexion.getConnection().createStatement();
        if(alumno.getEquipo().getIdEquipo()==0){
          sentencia.executeUpdate("insert into alumnos (idAlumno,nombre,grupo,idEquipo)"
                                + "values(0,'" + alumno.getNombre() + "','" + alumno.getGrupo()
                                + "',null"+")");
        }else{
          sentencia.executeUpdate("insert into alumnos (idAlumno,nombre,grupo,idEquipo)"
                                + "values(0,'" + alumno.getNombre() + "','" + alumno.getGrupo()
                                + "'," + alumno.getEquipo().getIdEquipo()+")");  
        }
        
        } catch (SQLException e) {
            System.out.println("Problemas al insertar");
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Alumno> getAlumno(String where) {
        ArrayList <Alumno> alumnos = new ArrayList();
        
        try {
            sentencia = conexion.getConnection().createStatement();
            resultado = sentencia.executeQuery("select * from alumnos" + where);

            while (resultado.next()) {
                Alumno alumno = new Alumno();
                alumno.setIdAlumno(resultado.getInt("idAlumno"));
                alumno.setNombre(resultado.getString("nombre"));
                alumno.setGrupo(resultado.getString("grupo"));
                
                alumnos.add(alumno);
            }
            ConnectionFactory.closeConnection();
        } catch (SQLException e) {
            System.out.println("Problemas al visualizar");
            e.printStackTrace();
        }
        return alumnos;
    }

    @Override
    public ArrayList<Alumno> getAlumnoEquipo() {
ArrayList <Alumno> alumnos = new ArrayList();
        
        try {
            sentencia = conexion.getConnection().createStatement();
            resultado = sentencia.executeQuery("select * from alumnos left join equipos using(idEquipo)");

            while (resultado.next()) {
                Alumno alumno = new Alumno();
                Equipo equipo = new Equipo();
                alumno.setNombre(resultado.getString("nombre"));
                equipo.setMarca(resultado.getString("marca"));
                equipo.setNumSerie(resultado.getString("numSerie"));
                alumno.setEquipo(equipo);
                
                alumnos.add(alumno);
            }
            ConnectionFactory.closeConnection();
        } catch (SQLException e) {
            System.out.println("Problemas al visualizar");
            e.printStackTrace();
        }
        return alumnos;
    }

    @Override
    public void updateAlumno(Alumno alumno) {
        try {
            sentencia = conexion.getConnection().createStatement();
            sentencia.executeUpdate("Update alumnos set nombre='"+alumno.getNombre()+"', grupo='"+
                    alumno.getGrupo()+"' where idAlumno="+alumno.getIdAlumno());
        } catch (SQLException e) {
            System.out.println("Problemas al actualizar");
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAlumno(String where) {
        try {
            sentencia = conexion.getConnection().createStatement();
            sentencia.executeUpdate("delete from alumnos " + where);
        } catch (SQLException e) {
            System.out.println("Problemas al actualizar");
            e.printStackTrace();
        }
    }
    
}
