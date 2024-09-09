package services;

import dao.TicketDao;
import models.entities.Ticket;
import models.enums.TicketStatus;
import models.enums.TransportType;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public class TicketService {
    private final TicketDao ticketDao;

    public TicketService() {
        this.ticketDao = new TicketDao();
    }

    public void add(TransportType transportType, BigDecimal purchasePrice, BigDecimal salePrice, Date saleDate, TicketStatus ticketStatus, UUID contractId) {
        Ticket ticket = new Ticket(transportType, purchasePrice, salePrice, saleDate, ticketStatus, contractId);
        ticketDao.addTicket(ticket);
    }

    public void edit(UUID ticketId, TransportType transportType, BigDecimal purchasePrice, BigDecimal salePrice, Date saleDate, TicketStatus ticketStatus) {
        ticketDao.updateTicket(ticketId, transportType, purchasePrice, salePrice, saleDate, ticketStatus);
    }

    public void deleteTicket(UUID ticketId) {
        ticketDao.deleteTicket(ticketId);
    }

    public void display() {
        ticketDao.displayTickets();
    }


}
