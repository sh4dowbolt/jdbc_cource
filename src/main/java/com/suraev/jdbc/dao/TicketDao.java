package com.suraev.jdbc.dao;

import com.suraev.jdbc.ConnectionManager;
import com.suraev.jdbc.entity.Ticket;
import com.suraev.jdbc.exception.DaoException;

import java.sql.*;

public class TicketDao {

    private static final TicketDao INSTANCE = new TicketDao();
    private static final String DELETE_SQL = """
                DELETE FROM ticket
                WHERE id = ?
                """;
    private static final String SAVE_SQL = """
                INSERT INTO ticket (passenger_number, passenger_name, flight_id, seat, price)
                VALUES (?, ?, ?, ?, ?)
                """;

    private TicketDao() {};

    public static TicketDao getInstance() {
        return INSTANCE;
    }


    public boolean delete (Long id) {

        try (
                var connection = ConnectionManager.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL);
        ) {
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate()>0;

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public Ticket save (Ticket ticket) {
        try(
                var connection = ConnectionManager.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS);
        ) {
            preparedStatement.setString(1, ticket.getPassengerNo());
            preparedStatement.setString(2, ticket.getPassengerName());
            preparedStatement.setLong(3, ticket.getFlightId());
            preparedStatement.setString(4, ticket.getSeatNo());
            preparedStatement.setBigDecimal(5, ticket.getPrice());

            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

            if(!generatedKeys.next()) {
                throw new SQLException("Failed to insert ticket");
            }

            Long generatedId = generatedKeys.getLong("id");
            ticket.setTicketId(generatedId);
            return ticket;


        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }




}
