package controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import entities.Equipement;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.EquipementService;
import controllers.SceneChanger;

public class ajouterEquipement {

    @FXML
    private Button btnAjouter;

    @FXML
    private TextField nomf;

    @FXML
    private TextField typef;

    @FXML
    private TextField statutf;

    private EquipementService equipementService;

    public ajouterEquipement() {
        this.equipementService = new EquipementService();
    }

    @FXML
    private void ajouterEquipement() {
        String nom = nomf.getText();
        String type = typef.getText();
        String statut = statutf.getText();

        if (nom.isEmpty() || type.isEmpty() || statut.isEmpty()) {
            afficherAlerte("Champs vides", "Veuillez remplir tous les champs.");
            return;
        }

        try {
            // Vérification d'unicité du nom
            if (equipementService.existeNom(nom)) {
                afficherAlerte("Nom existant", "Ce nom d'équipement existe déjà.");
                return;
            }

            Equipement equipement = new Equipement();
            equipement.setNom(nom);
            equipement.setType(type);
            equipement.setStatut(statut);

            equipementService.add(equipement);

            afficherAlerte("Succès", "Ajout d'équipement avec succès");
            Stage stage = (Stage) btnAjouter.getScene().getWindow();
            SceneChanger.changerScene("/ListEquipement.fxml", stage);

            // Effacer les champs après l'ajout réussi
            nomf.clear();
            typef.clear();
            statutf.clear();

        } catch (SQLException e) {
            afficherAlerte("Échec", "Échec d'ajout d'équipement");
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
