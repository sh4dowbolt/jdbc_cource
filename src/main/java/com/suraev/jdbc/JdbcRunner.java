package com.suraev.jdbc;

import org.postgresql.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcRunner {
    public static void main(String[] args) throws SQLException {

        try (Connection connection = ConnectionManager.get()) {

            System.out.println(connection.getTransactionIsolation());
            //ddl
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
