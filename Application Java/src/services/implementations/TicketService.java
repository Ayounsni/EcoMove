package services.implementations;

import dao.implementations.TicketDao;
import dao.implementations.TrajetDao;
import dao.interfaces.ITicketDao;
import models.entities.Ticket;
import models.enums.TicketStatus;
import models.enums.TransportType;
import services.interfaces.ITicketService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public class TicketService implements ITicketService {
    private final ITicketDao ticketDao;

    public TicketService() {
        this.ticketDao = new TicketDao();
    }

    @Override
    public Ticket add(TransportType transportType, BigDecimal purchasePrice, BigDecimal salePrice, LocalDate saleDate, LocalDate departureDate, LocalTime departureTime, UUID contractId, UUID trajetId) {
        Ticket ticket = new Ticket(
                transportType,
                purchasePrice,
                salePrice,
                saleDate,
                departureDate,
                departureTime,
                contractId,
                trajetId != null ? new TrajetDao().findById(trajetId) : null
        );
        return ticketDao.create(ticket);
    }

    @Override
    public boolean updateTicketStatus(UUID ticketId, TicketStatus ticketStatus) {
        Ticket ticket = ticketDao.findById(ticketId);
        if (ticket == null) {
            return false;
        }
        ticket.setTicketStatus(ticketStatus);
        return ticketDao.updateTicketStatus(ticketId, ticketStatus);
    }


    @Override
    public boolean delete(UUID ticketId) {
        return ticketDao.delete(ticketId);
    }

    @Override
    public List<Ticket> getAllTickets() {
        return ticketDao.findAll();
    }
}
