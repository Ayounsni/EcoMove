package ui;

import dao.implementations.CityDao;
import dao.implementations.TrajetDao;
import models.entities.City;
import models.entities.Trajet;
import services.implementations.TrajetService;
import services.interfaces.ICityService;
import services.interfaces.ITrajetService;
import services.implementations.CityService;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class TrajetUi {

    private final Scanner scanner = new Scanner(System.in);
    private final ITrajetService trajetService;
    private final ICityService cityService;

    public TrajetUi() {
        this.trajetService = new TrajetService(new TrajetDao()) ;
        this.cityService = new CityService(new CityDao());
    }

    public void showMenu() {
        boolean running = true;
        while (running) {
            System.out.println("\nMenu Trajet:");
            System.out.println("1. Créer un trajet");
            System.out.println("2. Voir tous les trajets");
            System.out.println("3. Modifier un trajet");
            System.out.println("4. Supprimer un trajet");
            System.out.println("5. Quitter");
            System.out.print("Choisissez une option : ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    createTrajet();
                    break;
                case 2:
                    viewAllTrajets();
                    break;
                case 3:
                    updateTrajet();
                    break;
                case 4:
                    deleteTrajet();
                    break;
                case 5:
                    running = false;
                    break;
                default:
                    System.out.println("Choix invalide.");
            }
        }
    }

    private void createTrajet() {

        List<City> cities = cityService.findAll();
        System.out.println("Liste des villes disponibles :");
        for (City city : cities) {
            System.out.println(city.getId() + ": " + city.getCityName());
        }


        System.out.print("Entrez l'ID de la ville de départ : ");
        int cityDId = Integer.parseInt(scanner.nextLine());
        City cityD = cityService.findById(cityDId);


        System.out.print("Entrez l'ID de la ville d'arrivée : ");
        int cityAId = Integer.parseInt(scanner.nextLine());
        City cityA = cityService.findById(cityAId);


        System.out.print("Durée du trajet (en minutes) : ");
        int duree = Integer.parseInt(scanner.nextLine());


        Trajet trajet = new Trajet(duree, cityD, cityA);
        trajetService.create(trajet);
        System.out.println("Trajet créé avec succès !");
    }

    private void viewAllTrajets() {
        List<Trajet> trajets = trajetService.findAll();
        if (trajets.isEmpty()) {
            System.out.println("Aucun trajet trouvé.");
        } else {
            for (Trajet trajet : trajets) {
                System.out.println("ID: " + trajet.getId() +
                        ", Durée: " + trajet.getDuree() + " minutes " +
                        ", Ville de départ: " + trajet.getCityDepart().getCityName() +
                        ", Ville d'arrivée: " + trajet.getCityA().getCityName());
            }
        }
    }

    private void updateTrajet() {
        System.out.print("ID du trajet à modifier : ");
        UUID id;
        try {
            id = UUID.fromString(scanner.nextLine());
        } catch (IllegalArgumentException e) {
            System.out.println("ID invalide.");
            return;
        }

        Trajet existingTrajet = trajetService.findById(id);
        if (existingTrajet == null) {
            System.out.println("Le trajet avec cet ID n'existe pas.");
            return;
        }

        System.out.print("Nouvelle durée du trajet : ");
        int duree;
        try {
            duree = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Durée invalide.");
            return;
        }

        List<City> cities = cityService.findAll();
        System.out.println("Liste des villes disponibles :");
        for (City city : cities) {
            System.out.println(city.getId() + ": " + city.getCityName());
        }

        System.out.print("Nouvelle ville de départ (ID) : ");
        int cityDId;
        City cityD;
        try {
            cityDId = Integer.parseInt(scanner.nextLine());
            cityD = cityService.findById(cityDId);
            if (cityD == null) {
                System.out.println("Ville de départ invalide.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("ID de ville de départ invalide.");
            return;
        }

        System.out.print("Nouvelle ville d'arrivée (ID) : ");
        int cityAId;
        City cityA;
        try {
            cityAId = Integer.parseInt(scanner.nextLine());
            cityA = cityService.findById(cityAId);
            if (cityA == null) {
                System.out.println("Ville d'arrivée invalide.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("ID de ville d'arrivée invalide.");
            return;
        }

        Trajet trajet = new Trajet(duree, cityD, cityA, id);

        if (trajetService.update(trajet)) {
            System.out.println("Trajet mis à jour !");
        } else {
            System.out.println("Échec de la mise à jour du trajet.");
        }
    }



    private void deleteTrajet() {
        System.out.print("ID du trajet à supprimer : ");
        UUID id = UUID.fromString(scanner.nextLine());

        if (trajetService.delete(id)) {
            System.out.println("Trajet supprimé.");
        } else {
            System.out.println("Erreur lors de la suppression du trajet.");
        }
    }
}
