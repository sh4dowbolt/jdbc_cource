package com.suraev.jdbc.dto;

public record TickeFilter(int limit,
                          int offset,
                          String passengerName,
                          String seatNo) {
}
