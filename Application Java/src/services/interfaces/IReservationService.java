package services.interfaces;

import models.entities.Reservation;

import java.util.UUID;

public interface IReservationService {
    void reserveTicket(Reservation reservation);
    Reservation getReservationById(UUID reservationId);
}
