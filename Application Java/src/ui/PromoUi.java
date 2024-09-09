package ui;



import models.entities.Contract;
import models.enums.DiscountType;
import models.enums.OfferStatus;
import services.ContractService;
import services.PromoService;



import java.sql.Date;
import java.util.Scanner;
import java.util.UUID;

public class PromoUi {
    private final Scanner scanner = new Scanner(System.in);
    private final PromoService promoService = new PromoService();
    private  final ContractService contractService  = new ContractService();

    public void showMenu() {
        boolean partnerRunning = true;
        while (partnerRunning) {
            System.out.println("\nPromo Manager:");
            System.out.println("1. Add Promo");
            System.out.println("2. Modify Promo");
            System.out.println("3. Delete Promo");
            System.out.println("4. Display All Promos");
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
        UUID contractId , promoId;
        String offerName,description,conditions;
        Date startDate , endDate;
        DiscountType discountType;
        OfferStatus offerStatus;
        System.out.print("Enter Contract ID to add a promo: ");
        contractId = UUID.fromString(scanner.nextLine());
        Contract contractForPromo = contractService.getContractById(contractId);

        if (contractForPromo != null) {
            System.out.println("Enter promo details:");
            System.out.print("Offer Name: ");
            offerName = scanner.nextLine();
            System.out.print("Description: ");
            description = scanner.nextLine();
            System.out.print("Contract Start Date (YYYY-MM-DD): ");
            startDate = Date.valueOf(scanner.nextLine());
            System.out.print("Contract End Date (YYYY-MM-DD): ");
            endDate = Date.valueOf(scanner.nextLine());
            System.out.print("Conditions: ");
            conditions = scanner.nextLine();
            System.out.print("Discount Type: ");
            discountType = DiscountType.valueOf(scanner.nextLine().toUpperCase());
            System.out.print("Offer Status: ");
            offerStatus = OfferStatus.valueOf(scanner.nextLine().toUpperCase());

            promoService.add(offerName, description, startDate,endDate, discountType, conditions,  offerStatus, contractId);
        } else {
            System.out.println("Contract not found with ID: " + contractId);
        }

    }
    public void update(){

        System.out.print("Enter Promo ID to update: ");
        UUID promoId = UUID.fromString(scanner.nextLine());

        System.out.print("Enter new Offer Name: ");
        String offerName = scanner.nextLine();

        System.out.print("Enter new Description: ");
        String description = scanner.nextLine();

        System.out.print("Enter new Start Date (yyyy-mm-dd): ");
        Date startDate = Date.valueOf(scanner.nextLine());

        System.out.print("Enter new End Date (yyyy-mm-dd): ");
        Date endDate = Date.valueOf(scanner.nextLine());

        System.out.print("Enter new Conditions: ");
        String conditions = scanner.nextLine();

        System.out.print("Enter new Discount Type (e.g., percentage, fixed): ");
        DiscountType discountType = DiscountType.valueOf(scanner.nextLine().toUpperCase());

        System.out.print("Enter new Offer Status (e.g., active, expired): ");
        OfferStatus offerStatus = OfferStatus.valueOf(scanner.nextLine().toUpperCase());

        promoService.edit(promoId, offerName, description, startDate, endDate,  discountType, conditions, offerStatus);

    }
    public void delete() {
        System.out.print("Enter Promo ID to delete: ");
        UUID promoId = UUID.fromString(scanner.nextLine());
        promoService.delete(promoId);
    }
    public void display(){
        promoService.display();
    }
}
