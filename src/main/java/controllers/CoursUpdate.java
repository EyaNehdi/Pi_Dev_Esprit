package controllers;

import entities.Cours;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.CoursService;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CoursUpdate implements Initializable {

    private int idCu;

    @FXML
    private TextArea contenuf;

    @FXML
    private TextField titref;

    @FXML
    private Button btnRetour;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        LoadData();
    }

    public void LoadData() {
        CoursService us = new CoursService();
        try {
            Cours u = us.findById(this.idCu);
            if (u != null) {
                if (u.getTitre() != null) {
                    titref.setText(u.getTitre());
                }
                if (u.getContenu() != null) {
                    contenuf.setText(u.getContenu());
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public int initData(int id_cours) {
        this.idCu = id_cours;
        this.LoadData();
        System.err.println("ena aaaaaa" + this.idCu);
        return this.idCu;
    }

    @FXML
    private void updateData(ActionEvent event) {
        // Validation de saisie
        if (titref.getText().isEmpty() || contenuf.getText().isEmpty()) {
            afficherAlerte("Veuillez remplir tous les champs!");
            return;
        }

        try {
            // Fetch user data from the database
            CoursService us = new CoursService();
            Cours coursToUpdate = us.findById(idCu);

            // Vérification d'unicité
            if (!coursToUpdate.getTitre().equals(titref.getText()) && coursExisteDeja(titref.getText())) {
                afficherAlerte("Un cours avec ce titre existe déjà.");
                return;
            }

            // Mise à jour des données du cours
            coursToUpdate.setTitre(titref.getText());
            coursToUpdate.setContenu(contenuf.getText());

            // Appel de la méthode de modification pour mettre à jour le cours
            us.update(coursToUpdate);

            // Afficher un message de réussite
            afficherAlerte("Modification réussie !!");

        } catch (SQLException ex) {
            ex.printStackTrace(); // Gérer l'exception de manière appropriée
            // Afficher un message d'erreur
            afficherAlerte("Une erreur s'est produite lors de la mise à jour des données du cours !");
        }
    }

    public void Retour() {
        Stage stage = (Stage) btnRetour.getScene().getWindow();
        SceneChanger.changerScene("/AfficherCours.fxml", stage);
    }

    private boolean coursExisteDeja(String titre) throws SQLException {
        CoursService serviceCours = new CoursService();
        return serviceCours.readAll().stream().anyMatch(c -> c.getTitre().equals(titre));
    }

    private void afficherAlerte(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
