package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import services.servicesformation;
import entities.formation;

public class ajouterformationcontrollers {

    servicesformation Serviceformation = new servicesformation();

    @FXML
    private TextField tfnom;

    @FXML
    private DatePicker tfdate;

    @FXML
    private Button btnRetour;

    @FXML
    void retournerAfficherFormation(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/afficherformation.fxml"));
            btnRetour.getScene().setRoot(root);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void Afficherformation(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/afficherformation.fxml"));
            tfdate.getScene().setRoot(root);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void Ajouterformation(ActionEvent event) {
        // Vérifier si les champs sont valides avant d'ajouter la formation
        if (validateFields()) {
            try {
                String nom = tfnom.getText();

                // Vérifier si le nom contient uniquement des lettres
                if (!isAlpha(nom)) {
                    showAlert(Alert.AlertType.WARNING, "Nom invalide", "Le nom de la formation doit contenir uniquement des lettres.");
                    return;
                }

                LocalDate date = tfdate.getValue();

                // Vérifier si la date de la formation est antérieure à la date actuelle
                if (date.isBefore(LocalDate.now())) {
                    showAlert(Alert.AlertType.WARNING, "Date invalide", "La date de la formation ne peut pas être antérieure à la date actuelle.");
                    return;
                }

                Serviceformation.ajouter(new formation(nom, date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
                showAlert(Alert.AlertType.INFORMATION, "Success", "Formation ajoutée");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de l'ajout de la formation : " + e.getMessage());
            }
        }
    }

    // Méthode pour valider les champs avant l'ajout
    private boolean validateFields() {
        String nom = tfnom.getText();
        LocalDate date = tfdate.getValue();

        if (nom.isEmpty() || date == null) {
            showAlert(Alert.AlertType.WARNING, "Champs obligatoires", "Veuillez remplir tous les champs.");
            return false;
        }

        // Ajoutez d'autres contrôles de validation si nécessaire (par exemple, validation du format de la date)

        return true;
    }

    // Méthode pour vérifier si une chaîne contient uniquement des lettres
    private boolean isAlpha(String str) {
        return str.matches("[a-zA-Z]+");
    }

    // Méthode utilitaire pour afficher une boîte de dialogue
    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private Button btnAjoutCertificat;

    public void naviguerVersAjoutCertificat(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ajoutcertificat.fxml"));
            btnAjoutCertificat.getScene().setRoot(root);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void Voir_Abonnements(ActionEvent actionEvent) {
    }

    public void Voir_Equipements(ActionEvent actionEvent) {
    }

    public void Voir_Formations(ActionEvent actionEvent) {
    }

    public void Voir_Evenements(ActionEvent actionEvent) {
    }

    public void Voir_Cours(ActionEvent actionEvent) {
    }
}
