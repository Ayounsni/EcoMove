package ui;

import models.entities.Partner;
import models.enums.PartnerStatus;
import models.enums.TransportType;
import services.implementations.PartnerService;
import utils.InputValidator;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class PartnerUi {
    private final Scanner scanner = new Scanner(System.in);
    private final PartnerService partnerService = new PartnerService();

    public void showMenu() {
        boolean partnerRunning = true;
        while (partnerRunning) {
            System.out.println("\nPartner Manager:");
            System.out.println("1. Add Partner");
            System.out.println("2. Modify Partner");
            System.out.println("3. Delete Partner");
            System.out.println("4. Display All Partners");
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
                    listAll();
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

    private void listAll() {
        List<Partner> partners = partnerService.listAllPartners();
        for (Partner partner : partners) {
            System.out.println("models.entities.Partner ID: " + partner.getId());
            System.out.println("Company Name: " + partner.getCompanyName());
            System.out.println("Business Contact: " + partner.getBusinessContact());
            System.out.println("Transport Type: " + partner.getTransportType());
            System.out.println("Geographic Zone: " + partner.getGeographicZone());
            System.out.println("Special Conditions: " + partner.getSpecialConditions());
            System.out.println("models.entities.Partner Status: " + partner.getPartnerStatus());
            System.out.println("Creation Date: " + partner.getCreationDate());
            System.out.println("--------------------------");
        }
    }

    public void create() {
        String companyName, businessContact, geographicZone, specialConditions;
        Date creationDate;
        TransportType transportType;
        PartnerStatus partnerStatus;

        System.out.println("Enter partner details:");

        do {
            System.out.print("Company Name: ");
            companyName = scanner.nextLine();
        } while (!InputValidator.validateEmpty(companyName));

        do {
            System.out.print("Business Contact: ");
            businessContact = scanner.nextLine();
        } while (!InputValidator.validateEmpty(businessContact));

        transportType = getTransportTypeInput();

        do {
            System.out.print("Geographic Zone: ");
            geographicZone = scanner.nextLine();
        } while (!InputValidator.validateEmpty(geographicZone));

        do {
            System.out.print("Special Conditions: ");
            specialConditions = scanner.nextLine();
        } while (!InputValidator.validateEmpty(specialConditions));

        partnerStatus = getPartnerStatusInput();

        creationDate = getCreationDateInput();

        partnerService.addPartner(companyName, businessContact, transportType, geographicZone, specialConditions, partnerStatus, creationDate);
    }

    public void update() {
        System.out.print("Enter Partner ID to modify: ");
        UUID partnerId = UUID.fromString(scanner.nextLine());

        String companyName = getCompanyNameInput();
        String businessContact = getBusinessContactInput();
        TransportType transportType = getTransportTypeInput();
        String geographicZone = getGeographicZoneInput();
        String specialConditions = getSpecialConditionsInput();
        PartnerStatus partnerStatus = getPartnerStatusInput();
        Date creationDate = getCreationDateInput();

        partnerService.modifyPartner(partnerId, companyName, businessContact, transportType, geographicZone, specialConditions, partnerStatus, creationDate);
    }

    public void delete() {
        System.out.print("Enter Partner ID to delete: ");
        UUID partnerId = UUID.fromString(scanner.nextLine());
        partnerService.deletePartner(partnerId);
    }

    private String getCompanyNameInput() {
        System.out.print("Company Name: ");
        return scanner.nextLine();
    }

    private String getBusinessContactInput() {
        System.out.print("Business Contact: ");
        return scanner.nextLine();
    }

    private TransportType getTransportTypeInput() {
        while (true) {
            System.out.print("Transport Type (airplane, train, bus): ");
            String transportTypeInput = scanner.nextLine();
            if (InputValidator.validateEmpty(transportTypeInput)) {
                continue;
            }
            try {
                return TransportType.valueOf(transportTypeInput.toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid Transport Type. Please enter a valid type.");
            }
        }
    }

    private String getGeographicZoneInput() {
        System.out.print("Geographic Zone: ");
        return scanner.nextLine();
    }

    private String getSpecialConditionsInput() {
        System.out.print("Special Conditions: ");
        return scanner.nextLine();
    }

    private PartnerStatus getPartnerStatusInput() {
        while (true) {
            System.out.print("Partner Status (active, inactive): ");
            String partnerStatusInput = scanner.nextLine();
            if (!InputValidator.validateEmpty(partnerStatusInput)) {
                continue;
            }
            try {
                return PartnerStatus.valueOf(partnerStatusInput.toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid Partner Status. Please enter a valid status.");
            }
        }
    }

    private Date getCreationDateInput() {
        while (true) {
            System.out.print("Creation Date (YYYY-MM-DD): ");
            String creationDateInput = scanner.nextLine();
            if (!InputValidator.validateEmpty(creationDateInput)) {
                continue;
            }
            try {
                return Date.valueOf(creationDateInput);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid date format. Please enter the date in YYYY-MM-DD format.");
            }
        }
    }
}
