package com.suraev.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcRunner {
    public static void main(String[] args) throws SQLException {

        try (Connection connection = ConnectionManager.get()) {

            System.out.println(connection.getTransactionIsolation());
            String sql = """
                    select * from drive_license
                    """;
            try (var statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
                var result = statement.executeQuery(sql);
                //System.out.println(connection.getSchema());
                System.out.println(result);
                while(result.next()) {
                    System.out.println(result.getString("first_name"));
                    System.out.println(result.getString("last_name"));
                    System.out.println(result.getString("birthday"));
                    System.out.println(result.getBigDecimal("count_cars"));

                }

                //System.out.println(statement.getUpdateCount());
            }
        }

    }
}
