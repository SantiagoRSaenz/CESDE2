/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

 package com.mycompany.apisantiago;

 import java.sql.*;
 import javax.swing.*;
 import java.awt.*;
 //import java.sql.PreparedStatement;
 /**
  *
  * @author Santiago Ramírez Sáenz
  */
 public class ApiSantiago {
 
     private static Connection connection;
 
     public static void main (String[] args) {
         conectarBaseDeDatos();
     }
 
     private static void conectarBaseDeDatos() {
         String conexionNexascore="jdbc:sqlserver://172.30.30.35:1433;"
         + "databaseName=BIBLIOTECA;"
         + "user=root;"
         + "password=root;"
         + "encrypt=true;"
         + "trustServerCertificate=true;";
         
         try {
         connection = DriverManager.getConnection(conexionNexascore);
         System.out.println("La conexión fue correcta. Felicitaciones!!");
         
         //Funciones de trabajo
         insertarLibros(connection);
         leerLibro(connection);
         
         }catch (SQLException e) {
             System.out.println("La conexión fue incorrecta. Lee el error y sigue intentando" + e.getMessage());
         }
     }
     
     public static void insertarLibros(Connection connection){
         
         String insertarLibros = "INSERT INTO LIBRO(Titulo, Autor, Fecha, ISBN) VALUES(?, ?, ?, ?)";
         try{
                 PreparedStatement stmt = connection.prepareStatement(insertarLibros);
                 
                 stmt.setString(1, "WEB ||");
                 stmt.setString(2, "Santiago Ramírez Sáenz");
                 stmt.setString(3, "2025-02-15");
                 stmt.setString(4, "032899");
                 stmt.executeUpdate();
                 System.out.println("El libro fue insertado correctamente");        
         }catch(SQLException e){
             System.out.println("Error al insertar el libro" + e.getMessage());
         }
     }
         
     public static void leerLibro(Connection connection){
        //consulta para obtener solo el primer libro
        String consultaLibroSql = "SELECT TOP 1 Id_Libro, Autor, Titulo, Fecha, ISBN from libro";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(consultaLibroSql)) {
            //Verificar si hay resultados y obterner el primer registro
            if(rs.next()) {
                int id = rs.getInt("Id_Libro");
                String nombre = rs.getString("Titulo");
                String autor = rs.getString("Autor");
                
                System.out.println("ID:" + id + ", Nombre" + nombre + ", Autor:" + autor);
            }else {               
                System.out.println("No se pudo leer ningun dato de la tabla LIBROS");
            }
        }catch (SQLException e) {
            System.out.println("Error al ejecutar la consulta: " + e.getMessage());
        }
     }
  }