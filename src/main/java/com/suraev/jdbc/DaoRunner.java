package com.suraev.jdbc;

import com.suraev.jdbc.dao.TicketDao;
import com.suraev.jdbc.dto.TickeFilter;
import com.suraev.jdbc.entity.Ticket;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class DaoRunner {

    public static void main(String[] args) {

        var ticket = TicketDao.getInstance().getById(5L);
        System.out.println(ticket);


       // findByIdTest(instance);
       // updateTest(instance);
        //findByFilter(instance);
        // getAllTickets(instance);



   /*     instance.save(new Ticket(null, "P007",
                "Vitaly Sureav", 3L,"16E", BigDecimal.valueOf(4800.00)));

        instance.delete(11L);*/
    }

    private static void findByFilter(TicketDao instance) {
        TickeFilter filter =new TickeFilter(5, 1, null,"1");
        List<Ticket> all = instance.findAll(filter);
        all.forEach(System.out::println);
    }

    private static void getAllTickets(TicketDao instance) {
        List<Ticket> allTickets = instance.getAllTickets();
        allTickets.forEach(System.out::println);
    }

    private static void findByIdTest(TicketDao instance) {
        Optional<Ticket> byId = instance.getById(12L);
        System.out.println(byId.toString());
    }
    private static void updateTest(TicketDao instance) {
        Ticket ticketToUpdate = new Ticket(12L, "P008","Andrey Shustikov", null, "16A", BigDecimal.valueOf(5000L));
        Ticket update = instance.update(ticketToUpdate);
        System.out.println(update.toString());
    }
}
