package com.suraev.jdbc;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class JdbcRunner {
    public static void main(String[] args) throws SQLException {
        String flightId="2; DROP TABLE tickets";
        //fillOfData();
        var result = getTicketsByFlightId(flightId);
        System.out.println(result);

    }


        private static List<Long> getTicketsByFlightId(String flightId) throws SQLException {
            String sql = """
            SELECT ID 
            FROM tickets
            where id = %s
            """.formatted(flightId);

            List<Long> result = new ArrayList<>();

            try (var connection = ConnectionManager.get();
                var statement = connection.createStatement()) {

                final var resultSet = statement.executeQuery(sql);
                while(resultSet.next()) {
                    result.add(resultSet.getObject("id", Long.class)); //NULL SAFE
                }
            }

        return result;
     
        }

        private static void fillOfData() throws SQLException {
            String sql = """
                    CREATE TABLE IF NOT EXISTS tickets (
                    id BIGSERIAL PRIMARY KEY
                    ) 
                    """;
            String insertSql= """
                    INSERT INTO tickets (id) VALUES 
                    (1),
                    (2),
                    (3)
                    """;
            try (var connection = ConnectionManager.get();
                 var statement = connection.createStatement()) {
                    statement.execute(sql);
                    statement.execute(insertSql);
            } 
        }
    }

