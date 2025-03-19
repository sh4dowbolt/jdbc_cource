package com.suraev.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

public class JdbcRunner {
    public static void main(String[] args) throws SQLException {

        try (Connection connection = ConnectionManager.get()) {

            System.out.println(connection.getTransactionIsolation());
            //ddl1
            String sql = """
                    create table if not exists info (
                    id serial primary key,
                    data text not null
                    );
                    """;
            try (var statement = connection.createStatement()) {
                var result = statement.execute(sql);
                System.out.println(connection.getSchema());
                System.out.println(result);
            }
        }

    }
}
