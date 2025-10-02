package com.suraev.jdbc.dao;

import com.suraev.jdbc.ConnectionManager;
import com.suraev.jdbc.dto.TickeFilter;
import com.suraev.jdbc.entity.Flight;
import com.suraev.jdbc.entity.Ticket;
import com.suraev.jdbc.exception.DaoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.joining;

public interface Dao <K,E> {

    List<E> findAll(TickeFilter filter);

    List<E> getAllTickets();

    Optional<E> getById(K id);

    E update(E ticket);

    boolean delete(K id);

    E save(E ticket);

}