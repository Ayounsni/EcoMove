

package models.entities;

public class ReservationTicket {

    private Ticket ticket;
    private Reservation reservation;

    public ReservationTicket() {
    }

    public ReservationTicket(Ticket ticket, Reservation reservation) {
        this.ticket = ticket;
        this.reservation = reservation;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }
}

