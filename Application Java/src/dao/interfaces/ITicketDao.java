package dao.interfaces;

import models.entities.Ticket;
import models.enums.TicketStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ITicketDao {

    Ticket create(Ticket ticket);


    Ticket findById(UUID id);


    List<Ticket> findAll();


    boolean updateTicketStatus(UUID ticketId, TicketStatus ticketStatus);


    boolean delete(UUID id);

    List<Ticket> findAvailableTickets(String departureCity, String arrivalCity, LocalDate departureDate);

    Ticket getById(UUID ticketId);
}

