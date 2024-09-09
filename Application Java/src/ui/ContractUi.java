package ui;



import models.entities.Partner;
import models.enums.ContractStatus;

import services.ContractService;
import services.PartnerService;
import utils.InputValidator;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Scanner;
import java.util.UUID;
public class ContractUi {
    private final Scanner scanner = new Scanner(System.in);
    private final ContractService contractService = new ContractService();
    private  final PartnerService manager = new PartnerService();

    public void showMenu() {
        boolean partnerRunning = true;
        while (partnerRunning) {
            System.out.println("\nContract Manager:");
            System.out.println("1. Add Contract");
            System.out.println("2. Modify Contract");
            System.out.println("3. Delete Contract");
            System.out.println("4. Display All Contracts");
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

        UUID partnerId;
        Date startDate;
        Date endDate;
        BigDecimal specialRate;
        String agreementConditions;
        boolean renewable;
        ContractStatus contractStatus;

        System.out.print("Enter Partner ID to add a contract: ");
        partnerId = UUID.fromString(scanner.nextLine());
        Partner partnerToUpdate = manager.getPartnerById(partnerId);

        if (partnerToUpdate != null) {
            System.out.println("Enter contract details:");
            while (true) {
                System.out.print("Contract Start Date (YYYY-MM-DD): ");
                String startDateInput = scanner.nextLine();

                if(!InputValidator.validateEmpty(startDateInput)) {
                    continue;
                }
                try {
                    startDate = Date.valueOf(startDateInput);
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid date format. Please enter the date in YYYY-MM-DD format.");
                }

            }
            do {
                System.out.print("Contract End Date (YYYY-MM-DD): ");
                String endDateInput = scanner.nextLine();

                if (!InputValidator.validateEmpty(endDateInput)) {
                    continue;
                }
                try {
                    endDate = Date.valueOf(endDateInput);

                    if (InputValidator.validateDateRange(startDate, endDate)) {
                        break;
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("Error: Invalid date format. Please enter the date in the format YYYY-MM-DD.");
                }

            } while (true);

            System.out.print("Special Rate: ");
            specialRate = scanner.nextBigDecimal();
            scanner.nextLine();
            do {
                System.out.print("Agreement Conditions: ");
                agreementConditions = scanner.nextLine();
            } while (!InputValidator.validateEmpty(agreementConditions));
            System.out.print("Is Renewable (true/false): ");
            renewable = scanner.nextBoolean();
            scanner.nextLine();

            while (true) {
                System.out.print("Contrat Statuts (ongoing, terminated , suspended ): ");
                String contractStatusInput = scanner.nextLine();
                if(!InputValidator.validateEmpty(contractStatusInput)) {
                    continue;
                }
                try {
                    contractStatus = ContractStatus.valueOf(contractStatusInput.toUpperCase());
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid Contrat Status. Please enter a valid status (ongoing, terminated , suspended).");
                }
            }

            contractService.add(startDate, endDate, specialRate, agreementConditions, renewable, contractStatus, partnerId);
        } else {
            System.out.println("Partner not found with ID: " + partnerId);
        }

    }
    public void update(){
        System.out.print("Enter Contract ID: ");
         UUID contractId = UUID.fromString(scanner.next());

        System.out.print("Enter new Start Date (yyyy-mm-dd): ");
        Date startDate = Date.valueOf(scanner.next());

        System.out.print("Enter new End Date (yyyy-mm-dd): ");
        Date endDate = Date.valueOf(scanner.next());

        System.out.print("Enter new Special Rate: ");
        BigDecimal specialRate = scanner.nextBigDecimal();

        System.out.print("Enter new Agreement Conditions: ");
        scanner.nextLine();
        String agreementConditions = scanner.nextLine();

        System.out.print("Is the contract renewable? (true/false): ");
        boolean renewable = scanner.nextBoolean();

        System.out.print("Enter new Contract Status (e.g., active, pending): ");
        ContractStatus contractStatus = ContractStatus.valueOf(scanner.next().toUpperCase());

        contractService.edit(contractId, startDate, endDate, specialRate, agreementConditions, renewable, contractStatus);

    }
    public void delete() {
        System.out.print("Enter Contract ID to delete: ");
        UUID contractId = UUID.fromString(scanner.next());
        contractService.deleteContract(contractId);
    }
    public void display(){
        contractService.display();
    }
}
