package dev.hammet.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    public static Connection getConnection() {
        //jdbc:postgresql://localhost:5432/postgres?user=postgres&password=password&currentSchema=ers_schema
      //  String url = "jdbc:postgresql://localhost:5432/postgres?user=postgres&password=password&currentSchema=ers_schema";
                //"jdbc:postgresql://postgre_container:5432/postgres?currentSchema=ers_schema" ;
                //POSTGRES_SQL_DB=jdbc:postgresql://postgre_container:5432/postgres?currentSchema=ers_schema -e DB_USERNAME=postgres -e PASSWORD=password
                // "jdbc:postgresql://localhost:5432/postgres?user=postgres&password=password&currentSchema=ers_schema";


         String url = System.getenv("POSTGRES_SQL_DB");
      //  String url = "jdbc:postgresql://localhost:5432/postgres?user=postgres&password=password&currentSchema=ers_schema";


        String username = System.getenv("DB_USERNAME");
        String password = System.getenv("PASSWORD");
        try {
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}



//    public static Connection getConnection()  {
//        try {
//            Connection connection = DriverManager.getConnection(System.getenv("POSTGRES_SQL_DB"));
//            return connection;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }




//String url = "jdbc:postgresql://localhost:5432/postgres?user=" + username + "&password=" + password + "&currentSchema=ers_schema";
//      POSTGRES_SQL_DB

// UNSAFE WAY vvv
//        //For compatibility with other technologies, w need to register our PostgreSQL Driver
//        //This process makes the application aware of what database Driver (SQL dialect) we're using
//        try {
//            Class.forName("org.postgresql.Driver");//try to find and return PostgreSQL Driver class
//        }
//        catch (ClassNotFoundException e) {
//            System.out.println("CLASS WASN'T FOUND");
//            e.printStackTrace();//this will print the exception message to the console
//        }
//
//        //we need to provide our database credentials
//        //well hard code them for now, but ill show a way to hide the credentials in environment variables
//
//        //the url to my database schema
//        // THE ONLY THINGS YOU MIGHT have to change is the PORT and the schema (maybe database if yours isnt called postgres for some reason)
//        String url = "jdbc:postgresql://localhost:5432/postgres?currentSchema=ers_schema";
//        //                                         ^ port  ^database               ^ schema
//        // Something to keep in mind in regards to your postgres database and schema, DONT USE SPACES OR Slashes or UNDERSCORES or Capitals
//        //Your Postgres username
//        String username = "postgres";
//        //your Postgres password
//        String password = "password";
//
//        //this is what actually returns our Connection Object (note how this method has a return type of connection)
//        return DriverManager.getConnection(url, username, password);
//
//    }
//}
