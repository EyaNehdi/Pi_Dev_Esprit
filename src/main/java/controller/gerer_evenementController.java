package controller;

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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import services.servicesevent;
import entities.event;


public class gerer_evenementController {

    private servicesevent servicesevent = new servicesevent();

    @FXML
    private DatePicker tf_date;

    @FXML
    private TextField tf_nom;

    @FXML
    void Afficher_event(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/gerer_evenement.fxml"));
            tf_nom.getScene().setRoot(root);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


    @FXML
    void Ajouter_event(ActionEvent event) {
        // Vérifier si les champs sont valides avant d'ajouter la formation
        if (validateFields()) {
            try {
                String nom = tf_nom.getText();

                // Vérifier si le nom contient uniquement des lettres
                if (!isAlpha(nom)) {
                    showAlert(Alert.AlertType.WARNING, "Nom invalide", "Le nom de l'event doit contenir uniquement des lettres.");
                    return;
                }

                LocalDate date = tf_date.getValue();

                // Vérifier si la date de la formation est antérieure à la date actuelle
                if (date.isBefore(LocalDate.now())) {
                    showAlert(Alert.AlertType.WARNING, "Date invalide", "La date d'event  ne peut pas être antérieure à la date actuelle.");
                    return;
                }

                servicesevent.ajouter(new event(nom, date));
                showAlert(Alert.AlertType.INFORMATION, "Success", "event ajoutée");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors d'ajoure l'event : " + e.getMessage());
            }
        }
    }

    // Méthode utilitaire pour afficher une boîte de dialogue
    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
    // Méthode pour vérifier si une chaîne contient uniquement des lettres
    private boolean isAlpha(String str) {
        return str.matches("[a-zA-Z]+");
    }

    // Méthode pour valider les champs avant l'ajout
    private boolean validateFields() {
        String nom = tf_nom.getText();
        LocalDate date = tf_date.getValue();

        if (nom.isEmpty() || date == null) {
            showAlert(Alert.AlertType.WARNING, "Champs obligatoires", "Veuillez remplir tous les champs.");
            return false;
        }

        // Ajoutez d'autres contrôles de validation si nécessaire (par exemple, validation du format de la date)

        return true;
    }



    }
