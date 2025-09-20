package com.suraev.jdbc;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.List;



public class JdbcRunner {
    public static void main(String[] args) throws SQLException {
        //Long flightId=1L;
        //LocalDateTime from = LocalDateTime.now().minusHours(10);
        //LocalDateTime to = LocalDateTime.now().plusHours(1);
        //fillOfData();
        //var result = getTicketsBetween(from, to);
       // System.out.println(result);

       try {
            checkMetaData();
       } finally {
            ConnectionManager.closeConectionPool();
       }
    

    }


        private static List<Long> getTicketsByFlightId(Long flightId) throws SQLException {
            String sql = """
            SELECT id 
            FROM tickets
            where id = ?
            """;

            List<Long> result = new ArrayList<>();

            try (var connection = ConnectionManager.getConnection();
                var prepareStatement = connection.prepareStatement(sql)) {
    

                prepareStatement.setLong(1, flightId);

                final var resultSet = prepareStatement.executeQuery();
                while(resultSet.next()) {
                    result.add(resultSet.getObject("id", Long.class)); //NULL SAFE
                }
            }

        return result;
     
        }

        private static void checkMetaData() throws SQLException {
        try (var connection = ConnectionManager.getConnection()) {
            var metaData = connection.getMetaData();
            var catalogs = metaData.getCatalogs();

            while(catalogs.next()) {
                var catalog = catalogs.getString(1);

                var schemas= metaData.getSchemas();

                while (schemas.next()) {

                    var schema = schemas.getString("TABLE_SCHEM");

                    var tables = metaData.getTables(catalog, schema,"%", new String []{"TABLE"});


                    if(schema.equals("public")) {
                    while (tables.next()) {
                        var tableName = tables.getString("TABLE_NAME");

                        var columns =metaData.getColumns(catalog, schema, tableName, "%");

                            while (columns.next()) {
                                System.out.println(columns.getString("COLUMN_NAME"));
                                
                            }

                    }
                    
                }

            }
            
        } 
    }
        }

        private static List<Long> getTicketsBetween(LocalDateTime from, LocalDateTime to) throws SQLException{
            String sql = """
                    SELECT * 
                    FROM tickets
                    WHERE departure_date 
                    BETWEEN ? and ?
                    """;
            List <Long> resList = new ArrayList<>();
            
            try (var connection = ConnectionManager.getConnection();
                 var prepareStatement = connection.prepareStatement(sql)) {

                    prepareStatement.setFetchSize(50);
                    prepareStatement.setMaxRows(100);
                    
                    System.out.println(prepareStatement);
                    prepareStatement.setTimestamp(1, Timestamp.valueOf(from));
                    System.out.println(prepareStatement);
                    prepareStatement.setTimestamp(2, Timestamp.valueOf(to));
                    System.out.println(prepareStatement);

                    final var resultSet = prepareStatement.executeQuery();

                    while (resultSet.next()) {
                        resList.add(resultSet.getObject("id",Long.class));
                    }
                }

            return resList;
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
                    (2),;
                    (3)
                    """;
            try (var connection = ConnectionManager.getConnection();
                 var statement = connection.createStatement()) {
                    statement.execute(sql);
                    statement.execute(insertSql);
            } 
        }
    }
