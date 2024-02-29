package tests;

import entities.Categorie;
import entities.Equipement;
import services.CategorieService;
import services.EquipementService;
import utils.MyDatabase;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        MyDatabase.getInstance().getConnection();


        // Test de Categorie
        CategorieService serviceCategorie = new CategorieService();
        Categorie cat1 = new Categorie(1,1, "Catégorie 1 Description");
        Categorie cat2 = new Categorie(2, "Catégorie 2 Description");


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
        EquipementService serviceEquipement = new EquipementService();
        Equipement equip1 = new Equipement(1, "Equipement 1 Nom", "Disponible", "Type 1");
        Equipement equip2 = new Equipement(2, "Equipement 2 Nom", "En maintenance", "Type 2");

        try {
          // Ajout d'un équipement
            serviceEquipement.add(equip1);
            System.out.println("Equipement ajouté : " + equip1);

           // Affichage de tous les équipements
            List<Equipement> equipementList = serviceEquipement.readAll();
            System.out.println("Liste des équipements : " + equipementList);

            // Modification d'un équipement (mettez à jour l'ID en fonction de votre base de données)
            equip1.setNom("Nouveau nom pour Equipement 1");
            serviceEquipement.update(equip1);
            System.out.println("Equipement mis à jour : " + equip1);

            // Suppression d'un équipement (mettez à jour l'ID en fonction de votre base de données)
            serviceEquipement.delete(equip1);
            System.out.println("Equipement supprimé : " + equip1);
        } catch (SQLException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }
}