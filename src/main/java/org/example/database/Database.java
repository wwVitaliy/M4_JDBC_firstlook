package org.example.database;

import org.example.prop.PropertyReader;

import java.sql.*;

public class Database {
    private static final Database INSTANCE = new Database();
    private static final String SQL_EX_MESSAGE = "Cannot create connection. Reason: ";

    private static Connection connection;

    private Database() {
        String url = PropertyReader.getConnectionURLForPostgres();
        String user = PropertyReader.getUserForPostgres();
        String password = PropertyReader.getPasswordForPostgres();
        String DbName = PropertyReader.getDBName();

        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println(SQL_EX_MESSAGE + e.getMessage());
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    public static Database getInstance() {
        return INSTANCE;
    }

    public void closeConnection (){
        try{
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int executeUpdate(String query) {
        try (Statement statement = connection.createStatement()) {
            return statement.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println(SQL_EX_MESSAGE + e.getMessage());
        }
        return 0;
    }

    public ResultSet executeQuery(String query){
        try (Statement statement = connection.createStatement()) {
            return statement.executeQuery(query);
        } catch (SQLException e) {
            System.out.println(SQL_EX_MESSAGE + e.getMessage());
        }
        return null;
    }

}
