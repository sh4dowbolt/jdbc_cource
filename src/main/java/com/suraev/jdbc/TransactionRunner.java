package com.suraev.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionRunner {

    public static void main(String[] args)  throws SQLException{

        long flightId=5;
        var deleteFlightSql = "DELETE FROM flight where id = ?";


        try (Connection connection =ConnectionManager.get();
        var deleteFlightStatement = connection.prepareStatement(deleteFlightSql);) {

            deleteFlightStatement.setLong(1, flightId);

            deleteFlightStatement.executeUpdate();
            
      
    }
}
}
