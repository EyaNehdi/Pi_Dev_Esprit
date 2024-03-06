package controllers;

import entities.certificat;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import services.servicescertificat;
import services.servicesformation;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class ajoutcertificatcpntrollers {

    @FXML
    private TextField tf_nomc;

    @FXML
    private TextField tf_nomeleve;

    @FXML
    private TextField tf_nomformateur;

    @FXML
    private DatePicker dp_date;

    @FXML
    private Button btn_ajout;

    @FXML
    private Button bn_afficher;

    @FXML
    private ComboBox<String> nnomformation; // Specify the type of items in ComboBox

    @FXML
    void combo(ActionEvent event) {
        // Handle ComboBox selection event if needed
        System.out.println("Selected: " + nnomformation.getSelectionModel().getSelectedItem());
    }

    @FXML
    void Affichercertificat(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/affichercertificat.fxml"));
            dp_date.getScene().setRoot(root);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void Ajoutercertificat(ActionEvent event) {
        // Get values from text fields and date picker
        String nom = tf_nomc.getText();
        String nomeleve = tf_nomeleve.getText();
        String nomformateur = tf_nomformateur.getText();

        // Get the selected date as a LocalDate
        LocalDate selectedDate = dp_date.getValue();

        // Validate input
        if (isAlphabetic(nomeleve) && isAlphabetic(nomformateur) && isAlphabetic(nom) && isValidDate(selectedDate)) {
            // Add your certificate addition logic here
            servicescertificat serviceCertificat = new servicescertificat();
            try {
                serviceCertificat.add(new certificat(nomeleve, nomformateur, nom, selectedDate.toString()));
                showSuccessAlert("Certificate added successfully");
            } catch (SQLException e) {
                showErrorAlert("Error adding certificate: " + e.getMessage());
            }
        } else {
            // Display an error message if input is not valid
            showErrorAlert("Invalid input. Please check the entered values.");
        }
    }

    // Validate if the string contains only alphabetic characters
    private boolean isAlphabetic(String input) {
        if (input.matches("^[a-zA-Z]+$")) {
            return true;
        } else {
            showErrorAlert("Invalid input. Please enter alphabetic characters only.");
            return false;
        }
    }

    // Validate if the selected date is not before the current date
    private boolean isValidDate(LocalDate selectedDate) {
        LocalDate currentDate = LocalDate.now();
        if (selectedDate != null && !selectedDate.isBefore(currentDate)) {
            return true;
        } else {
            showErrorAlert("Invalid date. Please select a date not before the current date.");
            return false;
        }
    }

    // Helper method to show a success alert
    private void showSuccessAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Helper method to show an error alert
    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void initialize() {
        // This method is called when the controller is initialized

        // Populate the ComboBox with formation names
        servicesformation serviceFormation = new servicesformation();

        try {
            List<String> formationNames = serviceFormation.getAllFormationNames();
            ObservableList<String> observableFormationNames = FXCollections.observableArrayList(formationNames);
            nnomformation.setItems(observableFormationNames);
        } catch (SQLException e) {
            System.out.println("Error getting formation names: " + e.getMessage());
            e.printStackTrace();
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
