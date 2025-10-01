package com.suraev.jdbc;

import com.suraev.jdbc.dao.TicketDao;
import com.suraev.jdbc.entity.Ticket;

import java.math.BigDecimal;

public class DaoRunner {

    public static void main(String[] args) {

        TicketDao instance = TicketDao.getInstance();


        instance.save(new Ticket(null, "P007",
                "Vitaly Sureav", 3L,"16E", BigDecimal.valueOf(4800.00)));

        instance.delete(11L);
    }
}
