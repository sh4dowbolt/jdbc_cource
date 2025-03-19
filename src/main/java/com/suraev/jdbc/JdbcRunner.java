package com.suraev.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

public class JdbcRunner {
    public static void main(String[] args) throws SQLException {

        try (Connection connection = ConnectionManager.get()) {

            System.out.println(connection.getTransactionIsolation());
            String sql = """
                    update info
                    set data = 'testTest'
                    where id = 10
                    """;
            try (var statement = connection.createStatement()) {
                var result = statement.executeUpdate(sql);
                System.out.println(connection.getSchema());
                System.out.println(result);
                //System.out.println(statement.getUpdateCount());
            }
        }

    }
}
