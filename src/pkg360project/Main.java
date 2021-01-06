/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg360project;

import java.sql.*;



/**
 *
 * @author Akis Sougias
 */
public class Main {

    /**
     * @param args the command line arguments
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     */

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost";
        String databaseName = "mysql";
        int port = 3306;
        String username = "root";
        String password = "";
        Connection con = DriverManager.getConnection(
                url + ":" + port + "/" + databaseName + "?characterEncoding=UTF-8", username, password);

        System.out.println("Creating database...");
        Statement stmt = con.createStatement();
        String drop = "DROP DATABASE HOSPITALDB";
        stmt.executeUpdate(drop);

        ResultSet resultSet = con.getMetaData().getCatalogs();


        String db = "HOSPITALDB";
        int exists = 0;
        //iterate each catalog in the ResultSet
        while (resultSet.next()) {
            // Get the database name, which is at position 1
            String databasename = resultSet.getString(1);
            System.out.println(databasename);
            if (databasename.equals(db)) {
                exists = 1;
            }
        }

        if (exists == 0) {
            String sql = "CREATE DATABASE HOSPITALDB";
            stmt.executeUpdate(sql);
            System.out.println("Database created successfully...");
        }
        resultSet.close();
        //Connecting with the HospitalDB
        String DB_URL = "jdbc:mysql://localhost/hospitaldb";
        System.out.println("Connecting to a selected database...");
        con = DriverManager.getConnection(DB_URL, username, password);
        System.out.println("Connected database successfully...");

        Statement stmt2 = con.createStatement();

        // EMPLOYEES TABLE
        String sql = "CREATE TABLE IF NOT EXISTS EMPLOYEE"
                + "(id INTEGER not NULL,"
                + "first VARCHAR(255) not NULL,"
                + "last VARCHAR(255) not NULL,"
                + "type VARCHAR(255) not NULL, "
                + "telephone INTEGER,"
                + "address VARCHAR(255),"
                + "PRIMARY KEY ( id ))";

        stmt2.executeUpdate(sql);
        // DOCTORS TABLE
        sql = "CREATE TABLE IF NOT EXISTS DOCTORS"
                + "(id INTEGER PRIMARY KEY REFERENCES EMPLOYEE(id),"
                + "specialty VARCHAR(255),"
                + "first VARCHAR(255) REFERENCES EMPLOYEE (first),"
                + "last VARCHAR(255) REFERENCES EMPLOYEE (last))";


        stmt2.executeUpdate(sql);
        // MANAGERS TABLE
        sql = "CREATE TABLE IF NOT EXISTS MANAGERS"
                + "(id INTEGER PRIMARY KEY REFERENCES EMPLOYEE(id),"
                + "grade VARCHAR(255),"
                + "first VARCHAR(255) REFERENCES EMPLOYEE (first),"
                + "last VARCHAR(255)  REFERENCES EMPLOYEE (last))";

        stmt2.executeUpdate(sql);

        sql = "INSERT INTO EMPLOYEE(id, first, last, type, telephone, address)"
                + "VALUES(1314, 'Jason', 'Ntagian', 'Doctor', 666, 'Archanes 69')";
        stmt2.executeUpdate(sql);

        sql = "INSERT INTO DOCTORS(id, specialty, first, last)"
                + "VALUES(1314,'Gynaikologos', 'Jason', 'Ntagian' )";
        stmt2.executeUpdate(sql);

        if (con != null) {
            con.close();
        }

    }

}
