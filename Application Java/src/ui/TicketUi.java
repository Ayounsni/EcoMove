package ui;



import models.entities.Contract;
import models.entities.Ticket;
import models.enums.TicketStatus;
import models.enums.TransportType;
import services.implementations.ContractService;
import services.implementations.TicketService;
import services.interfaces.ITicketService;

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


    public TicketUi() {
        this.ticketService = new TicketService();
        this.contractService = new ContractService();

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
       };
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
}
