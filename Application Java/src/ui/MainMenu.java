package ui;

import db.DbFunctions;


import java.util.Scanner;

public class MainMenu {
    private final Scanner scanner = new Scanner(System.in);

    private final DbFunctions db = DbFunctions.getInstance();

    private final PartnerUi partnerUi = new PartnerUi();
    private final ContractUi contractUi = new ContractUi();
    private final PromoUi promoUi = new PromoUi();
    private final TicketUi ticketUi = new TicketUi();

    public void showMainMenu() {
        boolean running = true;


        while (running) {
            System.out.println("\nMain Menu:");
            System.out.println("1. Partner Manager");
            System.out.println("2. Contract Manager");
            System.out.println("3. Promo Manager");
            System.out.println("4. Ticket Manager");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int mainChoice = scanner.nextInt();
            scanner.nextLine();


            switch (mainChoice) {
                case 1:
                    partnerUi.showMenu();
                    break;
                case 2:
                    contractUi.showMenu();
                    break;
                case 3:
                    promoUi.showMenu();
                    break;
                case 4:
                    ticketUi.showMenu();
                    break;
                case 5:
                    running = false;
                    System.out.println("Exiting the system.");
                    break;
                default:
                    System.out.println("Invalid choice. Please choose again.");
            }
        }
        scanner.close();
        db.closeConnection();
    }

}
