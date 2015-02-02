/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.sql.DataSource;

/**
 *
 * @author iabaños
 *
 * SessionBean para administrar la coneccion con la base de datos
 */
public class DBConnection {

    private DataSource monitorCertDigDS; //Datasourse del pool de conecciones a AS400
    private Connection connection;// Objeto que referencia la coneccion

    /**
     * metodo que retorna la coneccion a la base de datos
     */
    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Context ctx = new InitialContext();
                DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/sgcDS");
                connection = ds.getConnection();
            }
        } catch (SQLException ex) {
        } catch (NamingException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

        return connection;
    }

    public void shutdown() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ex) {
            }
        }
    }
}
