package ui;

import dao.implementations.ClientDao;
import models.entities.Client;
import services.implementations.ClientService;
import services.interfaces.IClientService;

import java.util.Scanner;

public class ClientUi {

    private final Scanner scanner = new Scanner(System.in);
    private final IClientService clientService;

    public ClientUi() {
          this.clientService = new ClientService(new ClientDao());
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
                System.out.println("3. Mon profil");
                System.out.println("4. Modifier mes informations");
                System.out.println("5. Deconnexion");
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
                        displayClientInfo(client);
                        break;
                    case 4:
                        updateClientInfo(client);
                        break;
                    case 5:
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

}
