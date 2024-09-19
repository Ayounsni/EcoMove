package services.interfaces;

import models.entities.Client;
import models.entities.ReservationTicket;

import java.util.List;
import java.util.UUID;

public interface IReservationTicketService {
    void addReservationTicket(ReservationTicket reservationTicket);
    ReservationTicket getReservationTicketByTicketId(UUID ticketId);
    List<ReservationTicket> getReservationsByClientId(Client client);
}

