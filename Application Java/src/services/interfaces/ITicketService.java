package services.interfaces;

import models.entities.Ticket;
import models.enums.TicketStatus;
import models.enums.TransportType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public interface ITicketService {


    Ticket add(TransportType transportType, BigDecimal purchasePrice, BigDecimal salePrice, LocalDate saleDate, LocalDate departureDate, LocalTime departureTime, UUID contractId, UUID trajetId);


    boolean delete(UUID ticketId);

     boolean updateTicketStatus(UUID ticketId, TicketStatus ticketStatus);


    List<Ticket> getAllTickets();

    List<Ticket> searchTickets(String departureCity, String arrivalCity, LocalDate departureDate);
}
