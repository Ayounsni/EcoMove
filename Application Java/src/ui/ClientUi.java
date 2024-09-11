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
          this.clientService = new ClientService(new ClientDao());;
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
        String email = scanner.nextLine();
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
            // Ici, vous pouvez continuer avec les options de réservation
        }
    }

    private void register() {
        System.out.println("Entrez vos informations pour l'inscription :");

        System.out.print("Prénom : ");
        String firstname = scanner.nextLine();

        System.out.print("Nom : ");
        String lastname = scanner.nextLine();

        System.out.print("Email : ");
        String email = scanner.nextLine();

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
}
