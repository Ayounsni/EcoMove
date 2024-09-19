package services.implementations;

import dao.implementations.ReservationTicketDao;
import dao.interfaces.IReservationTicketDao;
import models.entities.Client;
import models.entities.ReservationTicket;
import services.interfaces.IReservationTicketService;

import java.util.List;
import java.util.UUID;

public class ReservationTicketService implements IReservationTicketService {

    private final IReservationTicketDao reservationTicketDao;

    public ReservationTicketService() {
        this.reservationTicketDao = new ReservationTicketDao();
    }

    @Override
    public void addReservationTicket(ReservationTicket reservationTicket) {
        reservationTicketDao.add(reservationTicket);
    }

    @Override
    public ReservationTicket getReservationTicketByTicketId(UUID ticketId) {
        return reservationTicketDao.getByTicketId(ticketId);
    }

    @Override
    public List<ReservationTicket> getReservationsByClientId(Client client) {
        return reservationTicketDao.getReservationsByClient(client);
    }
}

