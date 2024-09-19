package ui;



import dao.implementations.CityDao;
import dao.implementations.ClientDao;
import dao.implementations.ReservationTicketDao;
import dao.interfaces.IReservationTicketDao;
import models.entities.*;
import models.enums.ReservationStatus;
import models.enums.TicketStatus;
import models.enums.TransportType;
import services.implementations.*;
import services.interfaces.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class TicketUi {
    private final Scanner scanner = new Scanner(System.in);
    private final ITicketService ticketService;
    private final ContractService contractService;
    private final PartnerService partnerService = new PartnerService();
    private final ICityService cityService;
    private final IClientService clientService;
    private final IReservationService reservationService;
    private final IReservationTicketService reservationTicketService;




    public TicketUi() {
        this.ticketService = new TicketService();
        this.contractService = new ContractService();
        this.cityService = new CityService(new CityDao());
        this.clientService = new ClientService(new ClientDao());
        this.reservationService = new ReservationService() ;
        this.reservationTicketService = new ReservationTicketService();
    }

    public void showMenu() {
        boolean running = true;
        while (running) {
            System.out.println("\nTicket Manager:");
            System.out.println("1. Add Ticket");
            System.out.println("2. Modify Ticket");
            System.out.println("3. Delete Ticket");
            System.out.println("4. Display All Tickets");
            System.out.println("5. Return to Main Menu");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    create();
                    break;
                case 2:
                    update();
                    break;
                case 3:
                    delete();
                    break;
                case 4:
                    display();
                    break;
                case 5:
                    running = false;
                    break;
                case 6:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    public void create() {
        UUID contractId;
        UUID trajetId;
        BigDecimal purchasePrice, salePrice;
        TransportType transportType;
        LocalDate saleDate, departureDate;
        LocalTime departureTime;

        System.out.print("Enter Contract ID to add a ticket: ");
        contractId = UUID.fromString(scanner.nextLine());
        Contract contractForTicket = contractService.getContractById(contractId);

        if (contractForTicket != null) {
            System.out.print("Enter Trajet ID (required): ");
            trajetId = UUID.fromString(scanner.nextLine());

            System.out.println("Enter ticket details:");
            System.out.print("Transport Type: ");
            transportType = TransportType.valueOf(scanner.nextLine().toUpperCase());
            System.out.print("Purchase Price: ");
            purchasePrice = scanner.nextBigDecimal();
            scanner.nextLine();
            System.out.print("Sale Price: ");
            salePrice = scanner.nextBigDecimal();
            scanner.nextLine();
            System.out.print("Sale Date (YYYY-MM-DD): ");
            saleDate = LocalDate.parse(scanner.nextLine());
            System.out.print("Departure Date (YYYY-MM-DD): ");
            departureDate = LocalDate.parse(scanner.nextLine());
            System.out.print("Departure Time (HH:MM): ");
            departureTime = LocalTime.parse(scanner.nextLine());

            ticketService.add(transportType, purchasePrice, salePrice, saleDate, departureDate, departureTime, contractId, trajetId);
        } else {
            System.out.println("Contract not found with ID: " + contractId);
        }
    }

    public void update() {
        System.out.print("Enter Ticket ID to update: ");
        UUID ticketId = UUID.fromString(scanner.nextLine());

        System.out.print("Enter new Ticket Status ('sold', 'canceled', 'pending'): ");
        TicketStatus ticketStatus = TicketStatus.valueOf(scanner.nextLine().toUpperCase());

        boolean success = ticketService.updateTicketStatus(ticketId, ticketStatus);
        if (success) {
            System.out.println("Ticket status updated successfully.");
        } else {
            System.out.println("Ticket status update failed.");
        }
    }

    public void delete() {
        System.out.print("Enter Ticket ID to delete: ");
        UUID ticketId = UUID.fromString(scanner.nextLine());
       if (ticketService.delete(ticketId)){
           System.out.println("Ticket deleted.");
       }else {
           System.out.println("Ticket could not be deleted.");
       }
    }

    public void display() {
        List<Ticket> tickets = ticketService.getAllTickets();
        if (tickets.isEmpty()) {
            System.out.println("Aucun ticket trouvé.");
        } else {
            for (Ticket ticket : tickets) {
                System.out.println("ID: " + ticket.getId());
                System.out.println("Type de transport: " + ticket.getTransportType());
                System.out.println("Prix d'achat: " + ticket.getPurchasePrice() + " MAD");
                System.out.println("Prix de vente: " + ticket.getSalePrice() + " MAD");
                System.out.println("Date de vente: " + ticket.getSaleDate());
                System.out.println("Date de départ: " + ticket.getDepartureDate());
                System.out.println("Heure de départ: " + ticket.getDepartureTime());
                System.out.println("Statut: " + ticket.getTicketStatus());
                System.out.println("ID Contrat: " + ticket.getContractId());
                if (ticket.getTrajet() != null) {
                    System.out.println("City depart: " + ticket.getTrajet().getCityDepart().getCityName());
                    System.out.println("City arrivee: " + ticket.getTrajet().getCityA().getCityName());
                    System.out.println("Duree: " + ticket.getTrajet().getDuree() +" minutes" );
                } else {
                    System.out.println("Aucun trajet associé.");
                }
                System.out.println("------------------------");
            }
        }
    }

    public void searchAndDisplayTickets() {
        List<City> cities = cityService.findAll();
        System.out.println("Liste des villes disponibles :");
        for (City city : cities) {
            System.out.println(city.getCityName().toLowerCase());
        }
        System.out.print("Entrez la ville de départ : ");
        String departureCity = scanner.nextLine().toLowerCase();

        System.out.print("Entrez la ville de destination : ");
        String arrivalCity = scanner.nextLine();

        System.out.print("Entrez la date de départ (YYYY-MM-DD) : ");
        LocalDate departureDate = LocalDate.parse(scanner.nextLine());

        List<Ticket> tickets = ticketService.searchTickets(departureCity, arrivalCity, departureDate);

        if (tickets.isEmpty()) {
            System.out.println("Aucun billet disponible pour les critères spécifiés.");
        } else {
            for (Ticket ticket : tickets) {
                System.out.println("ID du ticket : " + ticket.getId());
                System.out.println("Company Name : " + partnerService.getPartnerName(ticket.getContractId()));
                System.out.println("Type de transport : " + ticket.getTransportType());
                System.out.println("Prix de vente : " + ticket.getSalePrice());
                System.out.println("Date de vente : " + ticket.getSaleDate());
                System.out.println("Date de départ : " + ticket.getDepartureDate());
                System.out.println("Horaire de départ : " + ticket.getDepartureTime());
                System.out.println("Statut du billet : " + ticket.getTicketStatus());
                System.out.println("Trajet : ");
                System.out.println("   Durée : " + ticket.getTrajet().getDuree()+ "minutes");
                System.out.println("   Ville de départ : " + ticket.getTrajet().getCityDepart().getCityName());
                System.out.println("   Ville d'arrivée : " + ticket.getTrajet().getCityA().getCityName());
                System.out.println("------------------------------------------");
            }
            System.out.println("Voulez-vous réserver un ticket ? (oui/non)");
            String response = scanner.nextLine();
            if (response.equalsIgnoreCase("oui")) {
                reserveTicket();
            } else {
                System.out.println("Retour au menu principal.");
            }
        }
    }

    public void reserveTicket() {
        System.out.print("Enter Ticket ID to reserve: ");
        UUID ticketId = UUID.fromString(scanner.nextLine());

        Ticket ticket = ticketService.findById(ticketId);

        if (ticket != null && ticket.getTicketStatus() == TicketStatus.PENDING) {
            System.out.print("Enter your email to associate this reservation with a client: ");
            String clientEmail = scanner.nextLine();

            Client client = clientService.getClientByEmail(clientEmail);
            if (client != null) {

                Reservation reservation = new Reservation(ticket.getSalePrice(), ReservationStatus.CONFIRMED, client);
                ReservationTicket reservationTicket = new ReservationTicket(ticket, reservation);
                reservationService.reserveTicket(reservation);
                ticketService.updateTicketStatus(ticket.getId(), TicketStatus.SOLD);
                reservationTicketService.addReservationTicket(reservationTicket);

                System.out.println("Reservation created successfully for ticket: " + ticketId);
            } else {
                System.out.println("Client not found.");
            }
        } else {
            System.out.println("Ticket not available for reservation.");
        }
    }

}
