package com.suraev.jdbc.entity;

import java.math.BigDecimal;
import java.util.Objects;

public class Ticket {

    public Ticket(Long ticketId, String passengerNo, String passengerName, Flight flight, String seatNo, BigDecimal price) {
        this.ticketId = ticketId;
        this.passengerNo = passengerNo;
        this.passengerName = passengerName;
        this.flight = flight;
        this.seatNo = seatNo;
        this.price = price;
    }
    public Ticket() {};
    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public String getPassengerNo() {
        return passengerNo;
    }

    public void setPassengerNo(String passengerNo) {
        this.passengerNo = passengerNo;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public String getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(String seatNo) {
        this.seatNo = seatNo;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return Objects.equals(ticketId, ticket.ticketId) && Objects.equals(passengerNo, ticket.passengerNo) && Objects.equals(passengerName, ticket.passengerName) && Objects.equals(flight, ticket.flight) && Objects.equals(seatNo, ticket.seatNo) && Objects.equals(price, ticket.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ticketId, passengerNo, passengerName, flight, seatNo, price);
    }

    @Override
    public String toString() {
        return "Ticket{" +
               "ticketId=" + ticketId +
               ", passengerNo='" + passengerNo + '\'' +
               ", passengerName='" + passengerName + '\'' +
               ", flightId=" + flight +
               ", seatNo='" + seatNo + '\'' +
               ", price=" + price +
               '}';
    }

    private Long ticketId;
    private String passengerNo;
    private String passengerName;
    private Flight flight;
    private String seatNo;
    private BigDecimal price;
}

