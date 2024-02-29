package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;




public class DBConnection {

    final String URL="jdbc:mysql://localhost:3306/esprit3a20";
    final String USERNAME="root";
    final String PASSWORD="";
    static DBConnection instance;
    Connection connection;
    private DBConnection(){
        try {
            connection= DriverManager.getConnection(URL,USERNAME,PASSWORD);
            System.out.println("Connexion etablie");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public  static DBConnection getInstance(){
        if(instance==null){
            instance= new DBConnection();
        }
        return instance;

    }

    public Connection getConnection() {
        return connection;
    }
}
