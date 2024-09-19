package services.interfaces;

import models.entities.Reservation;
import models.enums.ReservationStatus;

import java.util.UUID;

public interface IReservationService {
    void reserveTicket(Reservation reservation);
    Reservation getReservationById(UUID reservationId);
    boolean updateReservationStatus(UUID reservationId, ReservationStatus reservationStatus);
}
