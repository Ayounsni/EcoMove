package dao.interfaces;

import models.entities.Client;
import models.entities.ReservationTicket;

import java.util.List;
import java.util.UUID;

public interface IReservationTicketDao {
    void add(ReservationTicket reservationTicket);
    ReservationTicket getByTicketId(UUID ticketId);
    List<ReservationTicket> getReservationsByClient(Client client);
}

