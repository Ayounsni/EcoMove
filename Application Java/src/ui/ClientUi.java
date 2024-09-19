package ui;

import dao.implementations.ClientDao;

import dao.implementations.ReservationDao;
import dao.interfaces.IReservationDao;
import models.entities.Client;
import models.entities.Reservation;
import models.entities.ReservationTicket;
import models.enums.ReservationStatus;
import services.implementations.ClientService;
import services.implementations.ReservationService;
import services.implementations.ReservationTicketService;
import services.interfaces.IClientService;
import services.interfaces.IReservationService;
import services.interfaces.IReservationTicketService;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class ClientUi {

    private final Scanner scanner = new Scanner(System.in);
    private final IClientService clientService;
    private final TicketUi ticketUi = new TicketUi();
    private final IReservationService reservationService = new ReservationService();
    private final IReservationTicketService reservationTicketService;


    public ClientUi() {
        this.clientService = new ClientService(new ClientDao());
        this.reservationTicketService = new ReservationTicketService();
    }

    public void showMenu() {
        boolean running = true;
        while (running) {
            System.out.println("\nMenu:");
            System.out.println("1. Connexion");
            System.out.println("2. Inscription");
            System.out.println("3. Quitter");
            System.out.print("Choisissez une option : ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    login();
                    break;
                case 2:
                    register();
                    break;
                case 3:
                    running = false;
                    break;
                default:
                    System.out.println("Choix invalide.");
            }
        }
    }

    private void login() {
        System.out.print("Entrez votre email : ");
        String email = scanner.nextLine().toLowerCase();
        Client client = clientService.getClientByEmail(email);

        if (client == null) {
            System.out.println("Client non trouvé. Voulez-vous créer un nouveau compte ? (oui/non)");
            String response = scanner.nextLine();
            if (response.equalsIgnoreCase("oui")) {
                register();
            } else {
                System.out.println("Retour au menu principal.");
            }
        } else {
            System.out.println("Connexion réussie. Bienvenue, " + client.getFirstname() + " " + client.getLastname());
            boolean running = true;
            while (running) {
                System.out.println("\nMenu:");

                System.out.println("1. Chercher des tickets");
                System.out.println("2. Mes reservations");
                System.out.println("3. Annuler une réservation");
                System.out.println("4. Mon profil");
                System.out.println("5. Modifier mes informations");
                System.out.println("6. Deconnexion");
                System.out.print("Choisissez une option : ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        ticketUi.searchAndDisplayTickets();
                        break;
                    case 2:
                        showClientReservations(client);
                        break;
                    case 3:
                        updateReservationStatus();
                        break;
                    case 4:
                        displayClientInfo(client);
                        break;
                    case 5:
                        updateClientInfo(client);
                        break;
                    case 6:
                        running = false;
                        break;
                    default:
                        System.out.println("Choix invalide.");
                }
            }
        }
    }

    private void register() {
        System.out.println("Entrez vos informations pour l'inscription :");

        System.out.print("Prénom : ");
        String firstname = scanner.nextLine();

        System.out.print("Nom : ");
        String lastname = scanner.nextLine();

        System.out.print("Email : ");
        String email = scanner.nextLine().toLowerCase();

        System.out.print("Numéro de téléphone : ");
        String phone = scanner.nextLine();

        Client newClient = new Client(firstname, lastname, email, phone);

        String registeredEmail = clientService.registerClient(newClient);
        if (registeredEmail != null) {
            System.out.println("Inscription réussie. Vous pouvez maintenant vous connecter avec l'email : " + registeredEmail);
        } else {
            System.out.println("L'email existe déjà. Essayez de vous connecter.");
        }
    }

    private void updateClientInfo(Client client) {
        System.out.println("Modifier vos informations :");

        System.out.print("Prénom (" + client.getFirstname() + ") : ");
        String firstname = scanner.nextLine();
        if (!firstname.isEmpty()) {
            client.setFirstname(firstname);
        }

        System.out.print("Nom (" + client.getLastname() + ") : ");
        String lastname = scanner.nextLine();
        if (!lastname.isEmpty()) {
            client.setLastname(lastname);
        }

        System.out.print("Numéro de téléphone (" + client.getPhone() + ") : ");
        String phone = scanner.nextLine();
        if (!phone.isEmpty()) {
            client.setPhone(phone);
        }

        boolean success = clientService.updateClient(client);
        if (success) {
            System.out.println("Informations mises à jour avec succès.");
        } else {
            System.out.println("Échec de la mise à jour des informations.");
        }
    }

    private void displayClientInfo(Client client) {
        System.out.println("\nVos informations actuelles :");
        System.out.println("Prénom : " + client.getFirstname());
        System.out.println("Nom : " + client.getLastname());
        System.out.println("Email : " + client.getEmail());
        System.out.println("Numéro de téléphone : " + client.getPhone());
    }

    private void showClientReservations(Client client) {
        List<ReservationTicket> reservationTickets = reservationTicketService.getReservationsByClientId(client);

        if (reservationTickets.isEmpty()) {
            System.out.println("Vous n'avez aucune réservation.");
        } else {
            System.out.println("Vos réservations : ");
            for (ReservationTicket reservationTicket : reservationTickets) {
                System.out.println("Reservation ID: " + reservationTicket.getReservation().getId());
                System.out.println("Date de réservation: " + reservationTicket.getReservation().getDateReservation());
                System.out.println("Prix de réservation: " + reservationTicket.getReservation().getPrice());
                System.out.println("Transport Type: " + reservationTicket.getTicket().getTransportType());
                System.out.println("Ville depart: " + reservationTicket.getTicket().getTrajet().getCityDepart().getCityName());
                System.out.println("Ville arrive: " + reservationTicket.getTicket().getTrajet().getCityA().getCityName());
                System.out.println("Date depart:" + reservationTicket.getTicket().getDepartureDate());
                System.out.println("Horaire : " + reservationTicket.getTicket().getDepartureTime());

                System.out.println("-------------------------");
            }
        }
    }
    public void updateReservationStatus() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Entrez l'ID de la réservation que vous voulez annuler :");
        String reservationIdInput = scanner.nextLine();
        UUID reservationId = UUID.fromString(reservationIdInput);

        boolean isUpdated = reservationService.updateReservationStatus(reservationId, ReservationStatus.CANCELED);

        if (isUpdated) {
            System.out.println("La réservation a été annuler avec succès");
        } else {
            System.out.println("L'annulation a échoué.");
        }
    }



}
