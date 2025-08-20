package com.suraev.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class TransactionRunner {

    public static void main(String[] args)  throws Exception{

        long flightId=4;
        var deleteFlightSql = "DELETE FROM flight where id = "+flightId;
        var deleteTicketsSql = "DELETE FROM ticket where flight_id = "+flightId;

        Connection connection = null;
        //PreparedStatement deleteFlightStatement = null; 
        //PreparedStatement deleteTicketsStatement = null;
        Statement statement = null;

        try {
        
            connection = ConnectionManager.get();
    
            connection.setAutoCommit(false);

            statement = connection.createStatement();

            statement.addBatch(deleteTicketsSql);
            statement.addBatch(deleteFlightSql);
            
            int [] resultsOfBatch = statement.executeBatch();

            connection.commit();

        } catch (Exception e) {

            if(connection !=null) {
                connection.rollback();
            }

            throw e;
         
        } finally {
            if(connection !=null) {
                connection.close();
            }

            if(statement != null) {
                statement.close();
            }

        }
}
}
