package Controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import entities.Categorie;
import entities.Equipement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import services.CategorieService;
import services.EquipementService;

public class ajouterCategorie {

    @FXML
    private Button btnAjouter;

    @FXML
    private TextField descriptionf;

    @FXML
    private TextField numSerief;

    @FXML
    private void ajouterCategorie() {
        String description = descriptionf.getText();
        String numSerieText = numSerief.getText();

        if (description.isEmpty() || numSerieText.isEmpty()) {
            afficherAlerte("Champs vides", "Veuillez remplir tous les champs.");
            return;
        }

        int numSerie;
        try {
            numSerie = Integer.parseInt(numSerieText);
        } catch (NumberFormatException e) {
            afficherAlerte("Format incorrect", "Le numéro de série doit être un nombre entier.");
            return;
        }

        CategorieService serviceCategorie = new CategorieService();

        try {
            // Vérification d'unicité du numéro de série
            if (serviceCategorie.exists(numSerie)) {
                afficherAlerte("Numéro de série existant", "Ce numéro de série existe déjà.");
                return;
            }

            Categorie categorie = new Categorie();
            categorie.setNumSerie(numSerie);
            categorie.setDescription(description);

            serviceCategorie.add(categorie);

            afficherAlerte("Succès", "Ajout de catégorie avec succès");

            // Effacer les champs après l'ajout réussi
            descriptionf.clear();
            numSerief.clear();

        } catch (SQLException e) {
            afficherAlerte("Échec", "Échec d'ajout de catégorie");
            e.printStackTrace();
        }
    }

    // Méthode pour afficher une alerte
    private void afficherAlerte(String titre, String contenu) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setContentText(contenu);
        alert.showAndWait();
    }
}
