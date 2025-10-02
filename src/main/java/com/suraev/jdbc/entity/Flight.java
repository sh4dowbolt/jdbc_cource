package com.suraev.jdbc.entity;

import java.time.LocalDateTime;

public record Flight(Long id,
                     LocalDateTime departureDate,
                     String departureAirportCode,
                     String arrivalAirportCode,
                     LocalDateTime arrivalDate,
                     String status,
                     Integer aircraftId) {
}
