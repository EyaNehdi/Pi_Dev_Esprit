package tests;

import entities.Packs;
import entities.Abonnement;
import services.AbonnementService;
import services.PacksService;
import utils.MyDatabase;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        MyDatabase.getInstance().getConnection();


        // Test de Categorie
        AbonnementService serviceabonnement = new AbonnementService();
        Abonnement abo1= new Abonnement(1,"ali","amari" , "aliamaari@gmail.com" , 5  );
        Abonnement abo2 = new Abonnement(2, "meriem" , "haj taieb" , "meriemhajtaieb@gmail.com" , 44);


      /*  // Ajout d'une catégorie
        serviceCategorie.add(cat1);
        System.out.println("Catégorie ajoutée : " + cat1);*/

        /*// Affichage de toutes les catégories
        List<Categorie> categorieList = serviceCategorie.readAll();
        System.out.println("Liste des catégories : " + categorieList);
            // Modification d'une catégorie (mettez à jour l'ID en fonction de votre base de données)
            cat1.setDescription("Nouvelle description pour Catégorie 1");
            serviceCategorie.update(cat1);
            System.out.println("Catégorie mise à jour : " + cat1);

        // Suppression d'une catégorie (mettez à jour l'ID en fonction de votre base de données)
        serviceCategorie.delete(cat1);
        System.out.println("Catégorie supprimée : " + cat1); */

        // Test d'Equipement
        PacksService servicepacks = new PacksService();
        Packs p1 = new Packs("meriem", "nehdi", 1450f, "silver");
        Packs p2 = new Packs("ramez", "En zorgui", 445f, "gold");


        try {
            // Ajout d'un équipement
            servicepacks.add(p1);
            System.out.println("packs ajouté : " + p1);

            // Affichage de tous les équipements
            List<Packs> packsList = servicepacks.readAll();
            System.out.println("Liste des packs : " + packsList);

            // Modification d'un équipement (mettez à jour l'ID en fonction de votre base de données)
            p1.setNom("Nouveau nom pour packs 1");
            servicepacks.update(p1);
            System.out.println("packs mis à jour : " + p1);

            // Suppression d'un équipement (mettez à jour l'ID en fonction de votre base de données)
            servicepacks.delete(p1);
            System.out.println("Equipement supprimé : " + p1);
        } catch (SQLException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }
}
