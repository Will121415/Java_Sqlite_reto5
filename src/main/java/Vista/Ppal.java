/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Controlador.Controller;
import java.sql.SQLException;

/**
 *
 * @author USUARIO
 */
public class Ppal {
    
    public static void main(String[] args) throws SQLException {
        Controller.registrarProducto("Papa",10, "Tuberculo", 2000);
    }
    
}
