package com.suraev.jdbc.dao;

import com.suraev.jdbc.ConnectionManager;
import com.suraev.jdbc.entity.Ticket;
import com.suraev.jdbc.exception.DaoException;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    private static final String UPDATE_SQL = """
            UPDATE ticket
            SET passenger_number = ?, passenger_name  = ?, flight_id = ?, seat = ?, price = ?
            WHERE id = ?
            """;
    private static final String SELECT_ALL = """
            SELECT id, passenger_number, passenger_name, flight_id, seat, price FROM ticket
            """;
    private static final String SELECT_SQL = SELECT_ALL +
             """
             WHERE id = ?
             """;


    private TicketDao() {};

    public static TicketDao getInstance() {
        return INSTANCE;
    }

    public List<Ticket> getAllTickets() {

        try(Connection connection = ConnectionManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL);
        ) {
            ResultSet resultSet = statement.executeQuery();
            List<Ticket> tickets = new ArrayList<>();

            while (resultSet.next()) {
                tickets.add(buildTicket(resultSet));
            }
            return tickets;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Ticket> getById(Long id) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_SQL);
        ) {
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            Ticket ticket = null;

            if (resultSet.next()) {
                Long aLong = resultSet.getLong("id");
                String passengerNumber = resultSet.getString("passenger_number");
                String passengerName = resultSet.getString("passenger_name");
                Long flightId = resultSet.getLong("flight_id");
                String seat = resultSet.getString("seat");
                BigDecimal price = resultSet.getBigDecimal("price");


                ticket = new Ticket();
                ticket.setTicketId(aLong);
                ticket.setPassengerName(passengerName);
                ticket.setFlightId(flightId);
                ticket.setPassengerNo(passengerNumber);
                ticket.setPrice(price);
                ticket.setSeatNo(seat);
            }
            return Optional.ofNullable(ticket);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Ticket update(Ticket ticket) {

        try(Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)
        ) {

            preparedStatement.setString(1, ticket.getPassengerNo());
            preparedStatement.setString(2, ticket.getPassengerName());
            preparedStatement.setLong(3, ticket.getFlightId());
            preparedStatement.setString(4, ticket.getSeatNo());
            preparedStatement.setBigDecimal(5, ticket.getPrice());
            preparedStatement.setLong(6, ticket.getTicketId());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return ticket;
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
                PreparedStatement preparedStatement =
                        connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS);
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

    public Ticket buildTicket(ResultSet resultSet) throws SQLException {
        return new Ticket(
                resultSet.getLong("id"),
                resultSet.getString("passenger_number"),
                resultSet.getString("passenger_name"),
                resultSet.getLong("flight_id"),
                resultSet.getString("seat"),
                resultSet.getBigDecimal("price")
        );
        }
    }





