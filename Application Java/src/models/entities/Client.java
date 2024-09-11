package models.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Client {

    private UUID id;
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private final List<Reservation> reservations;

    public Client() {

        this.reservations = new ArrayList<>();
    }

    public Client(String firstname, String lastname, String email, String phone) {
        this.id = UUID.randomUUID();
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phone = phone;
        this.reservations = new ArrayList<>();
    }

    // Getters et Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public List<Reservation> getReservations() {
        return reservations;
    }
}

