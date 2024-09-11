package ui;



import models.entities.Contract;
import models.enums.TicketStatus;
import models.enums.TransportType;
import services.implementations.ContractService;
import services.implementations.TicketService;


import java.math.BigDecimal;
import java.sql.Date;
import java.util.Scanner;
import java.util.UUID;

public class TicketUi {
    private final Scanner scanner = new Scanner(System.in);
    private final TicketService ticketService = new TicketService();
    private  final ContractService contractService  = new ContractService();

    public void showMenu() {
        boolean partnerRunning = true;
        while (partnerRunning) {
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
                    partnerRunning = false;
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
        UUID contractId, ticketId;
        BigDecimal purchasePrice , salePrice;
        TransportType transportType;
        TicketStatus ticketStatus;
        Date saleDate;

        System.out.print("Enter Contract ID to add a ticket: ");
        contractId = UUID.fromString(scanner.nextLine());
        Contract contractForTicket = contractService.getContractById(contractId);

        if (contractForTicket != null) {
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
            saleDate = Date.valueOf(scanner.nextLine());
            System.out.print("Ticket Status: ");
            ticketStatus = TicketStatus.valueOf(scanner.nextLine().toUpperCase());

            ticketService.add(transportType, purchasePrice, salePrice, saleDate, ticketStatus, contractId);
        } else {
            System.out.println("Contract not found with ID: " + contractId);
        }
    }
    public void update(){

        System.out.print("Enter Ticket ID to update: ");
        UUID ticketId = UUID.fromString(scanner.nextLine());

        System.out.print("Enter new Transport Type: ");
        TransportType transportType = TransportType.valueOf(scanner.nextLine().toUpperCase());

        System.out.print("Enter new Purchase Price: ");
        BigDecimal purchasePrice = scanner.nextBigDecimal();
        scanner.nextLine();

        System.out.print("Enter new Sale Price: ");
        BigDecimal salePrice = scanner.nextBigDecimal();
        scanner.nextLine();

        System.out.print("Enter new Sale Date (yyyy-mm-dd): ");
        Date saleDate = Date.valueOf(scanner.nextLine());

        System.out.print("Enter new Ticket Status: ");
        TicketStatus ticketStatus = TicketStatus.valueOf(scanner.nextLine().toUpperCase());

        ticketService.edit(ticketId, transportType, purchasePrice, salePrice, saleDate, ticketStatus);

    }
    public void delete() {
        System.out.print("Enter Ticket ID to delete: ");
        UUID ticketId = UUID.fromString(scanner.nextLine());
        ticketService.deleteTicket(ticketId);

    }
    public void display(){
        ticketService.display();
    }
}
