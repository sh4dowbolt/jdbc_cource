package com.suraev.jdbc.dao;

import com.suraev.jdbc.ConnectionManager;
import com.suraev.jdbc.dto.TickeFilter;
import com.suraev.jdbc.entity.Flight;
import com.suraev.jdbc.entity.Ticket;
import com.suraev.jdbc.exception.DaoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class FlightDao implements Dao<Long, Flight> {

    private static final FlightDao INSTANCE = new FlightDao();

    private static final String FIND_BY_ID_SQL= """
            SELECT id,
            departure_date,
            departure_airport_code,
            arrival_airport_code,
            arrival_date,
            status,
            aircraft_id
            FROM flight
            WHERE id=?
            """;

    private FlightDao() {}

    public static FlightDao getInstance() {
        return INSTANCE;
    }


    @Override
    public List<Flight> findAll(TickeFilter filter) {
        return List.of();
    }

    @Override
    public List<Flight> getAllTickets() {
        return List.of();
    }

    @Override
    public Optional<Flight> getById(Long id) {
        try (
                Connection connection = ConnectionManager.getConnection();
        ) {

            return getById(id, connection);

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public Optional<Flight> getById(Long id, Connection connection) {
        try (
                var preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL);
        ) {
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            Flight flight = null;

            if(resultSet.next()) {
                flight = buildFlight(resultSet);
            }

            return Optional.ofNullable(flight);

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }


    @Override
    public Flight update(Flight ticket) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public Flight save(Flight ticket) {
        return null;
    }

    private Flight buildFlight(ResultSet rs) throws SQLException {
        return new Flight(
                rs.getLong("id"),
                rs.getTimestamp("departure_date").toLocalDateTime(),
                rs.getString("departure_airport_code"),
                rs.getString("arrival_airport_code"),
                rs.getTimestamp("arrival_date").toLocalDateTime(),
                rs.getString("status"),
                rs.getInt("aircraft_id")
        );
    }
}
