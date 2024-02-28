package controllers;

import entities.Cours;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.CoursService;

import java.sql.SQLException;
import java.util.List;

public class AjouterCours {

    @FXML
    private Button btnLister;

    @FXML
    private Button btnAjouter;

    @FXML
    private Button btnAnnuler;

    @FXML
    private TextArea fieldContenu;

    @FXML
    private TextField fieldTitre;

    @FXML
    public void Lister() {
        Stage stage = (Stage) btnLister.getScene().getWindow();
        SceneChanger.changerScene("/AfficherCours.fxml", stage);
    }

    @FXML
    private void AjouterCours() {
        // Validation de saisie
        if (fieldTitre.getText().isEmpty() || fieldContenu.getText().isEmpty()) {
            afficherAlerte("Veuillez remplir tous les champs.");
            return;
        }

        // Vérification d'unicité
        if (coursExisteDeja(fieldTitre.getText())) {
            afficherAlerte("Un cours avec ce titre existe déjà.");
            return;
        }

        // Ajout du cours
        Cours cours = new Cours(fieldTitre.getText(), fieldContenu.getText());
        CoursService serviceCours = new CoursService();

        try {
            serviceCours.add(cours);
            afficherAlerte("Ajout de cours avec succès");

            // Changer de scène après l'ajout
            Stage stage = (Stage) btnAjouter.getScene().getWindow();
            SceneChanger.changerScene("/AfficherCours.fxml", stage);

        } catch (SQLException e) {
            afficherAlerte("Échec d'ajout de cours. " + e.getMessage());
            System.out.println(e.getMessage());
        }
    }

    private boolean coursExisteDeja(String titre) {
        // Vérifie si un cours avec le même titre existe déjà
        CoursService serviceCours = new CoursService();
        List<Cours> coursList;

        try {
            coursList = serviceCours.readAll();
        } catch (SQLException e) {
            afficherAlerte("Erreur lors de la récupération des cours. " + e.getMessage());
            return true; // On considère que le cours existe déjà en cas d'erreur.
        }

        for (Cours cours : coursList) {
            if (cours.getTitre().equals(titre)) {
                return true; // Le cours existe déjà
            }
        }

        return false; // Le cours n'existe pas encore
    }

    private void afficherAlerte(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
