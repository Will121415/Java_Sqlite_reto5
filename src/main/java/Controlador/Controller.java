/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Clases.Producto;
import Modelo.Persistencia.CRUD;
import Modelo.Persistencia.DbConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author USUARIO
 */
public abstract class Controller {

    public static boolean registrarProducto(String nombre, int cantidad, String categoria, double precio) throws SQLException {
        Producto p = new Producto(nombre, cantidad, categoria, precio);
        CRUD.setConnection(DbConnection.ConexionBD());
        String sentencia = "INSERT INTO productos(nombre,cantidad,categoria,precio) "
                + " VALUES ('" + p.getNombre() + "','" + p.getCantidad() + "','" + p.getCategoria() + "','" + p.getPrecio() + "');";
               if (CRUD.setAutoCommitBD(false)) {
            if (CRUD.insertarBD(sentencia)) {
                CRUD.commitBD();
                CRUD.cerrarConexion();
                return true;
            } else {
                CRUD.rollbackBD();
                CRUD.cerrarConexion();
                return false;
            }
        } else {
            CRUD.cerrarConexion();
            return false;
        }
    }
    
     public static List<Producto> listarProductos() {
        CRUD.setConnection(DbConnection.ConexionBD());
        List<Producto> listaProductos = new ArrayList<>();
        try {
            String sql = "select * from productos";

            ResultSet rs = CRUD.consultarBD(sql);
            
            while (rs.next()) {
                Producto p = new Producto();
                p.setId(rs.getInt("id"));
                p.setNombre(rs.getString("nombre"));
                p.setCantidad(rs.getInt("cantidad"));
                p.setCategoria(rs.getString("categoria"));
                p.setPrecio(rs.getDouble("precio"));
                listaProductos.add(p);
                        
            }

        } catch (SQLException ex) {
            System.out.println(ex);
            listaProductos = null;
        } finally {
            CRUD.cerrarConexion();
        }

        return listaProductos;
    }
     
    public static Producto obtenerProducto(int id) {
        CRUD.setConnection(DbConnection.ConexionBD());
        String sql = "SELECT * FROM productos WHERE id=" + id + ";";
        ResultSet rs = CRUD.consultarBD(sql);
        Producto p =  new Producto();
        try {
            if (rs.next()) {
                p.setId(rs.getInt("id"));
                p.setNombre(rs.getString("nombre"));
                p.setCantidad(rs.getInt("cantidad"));
                p.setCategoria(rs.getString("categoria"));
                p.setPrecio(rs.getDouble("precio"));
            } else {
                p = null;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            p = null;
        } finally {
            CRUD.cerrarConexion();
        }
        return p;
    }
    
    public static boolean borrarProducto(int id) {
        boolean correctTransaction;
        CRUD.setConnection(DbConnection.ConexionBD());
        String Sentencia = "DELETE FROM productos WHERE id='" + id + "';";
        try {
            if (CRUD.setAutoCommitBD(false)) {
                if (CRUD.borrarBD(Sentencia)) {
                    CRUD.commitBD();
                    correctTransaction = true;
                } else {
                    CRUD.rollbackBD();
                    correctTransaction = false;
                }
            } else {

                correctTransaction = false;
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            correctTransaction = false;
        } finally {
            CRUD.cerrarConexion();
        }

        return correctTransaction;
    }
    
     public static boolean actualizarProducto(int id, String nombre, int cantidad, String categoria, double precio) {
        Producto p = new Producto(id,nombre, cantidad, categoria, precio);
        CRUD.setConnection(DbConnection.ConexionBD());
         System.out.println(p.getNombre());
        String sentencia = "UPDATE `productos` SET nombre='" + p.getNombre() + "',cantidad='" + p.getCantidad()+ 
                "',categoria='" + p.getCategoria() + "',precio='" + p.getPrecio()
                + "' WHERE id=" + p.getId() + ";";
               if (CRUD.setAutoCommitBD(false)) {
            if (CRUD.actualizarBD(sentencia)) {
                CRUD.commitBD();
                CRUD.cerrarConexion();
                return true;
            } else {
                CRUD.rollbackBD();
                CRUD.cerrarConexion();
                return false;
            }
        } else {
            CRUD.cerrarConexion();
            return false;
        }
    }
}
