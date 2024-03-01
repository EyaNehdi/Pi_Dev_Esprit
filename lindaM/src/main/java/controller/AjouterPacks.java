package controller;

import java.sql.SQLException;

import entities.Packs;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import services.PacksService;

public class AjouterPacks {

    @FXML
    private Button btnAjouter;

    @FXML
    private TextField descriptionf;

    @FXML
    private TextField nomf;

    @FXML
    private TextField prixf;

    @FXML
    private TextField typef;

    @FXML
    private void ajouterPacks() {
        String nom = nomf.getText().trim();
        String description = descriptionf.getText().trim();
        String type = typef.getText().trim();
        String prixText = prixf.getText().trim();

        // Vérifier si les champs sont vides
        if (nom.isEmpty() || description.isEmpty() || type.isEmpty() || prixText.isEmpty()) {
            afficherAlerte("Erreur", "Veuillez remplir tous les champs.");
            return;
        }

        // Vérifier si le prix est un entier valide
        int prix;
        try {
            prix = Integer.parseInt(prixText);
            if (prix <= 0) {
                afficherAlerte("Erreur", "Le prix doit être un entier positif.");
                return;
            }
        } catch (NumberFormatException e) {
            afficherAlerte("Erreur", "Le prix doit être un nombre entier.");
            return;
        }

        // Si toutes les vérifications passent, créer et ajouter le pack
        Packs packs = new Packs();
        packs.setPrix(prix);
        packs.setNom(nom);
        packs.setDescription(description);
        packs.setType(type);

        PacksService servicepacks= new PacksService();

        try {
            servicepacks.add(packs);

            afficherAlerte("Succès", "Ajout de pack avec succès");
            // Changer de scène après l'ajout
            // Stage stage = (Stage) btnAjouter.getScene().getWindow();
            // SceneChanger.changerScene("/AfficherCours.fxml", stage);

        } catch (SQLException e) {
            afficherAlerte("Erreur", "Échec d'ajout de pack");
            e.printStackTrace();
        }
    }

    private void afficherAlerte(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
