package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import services.servicesevent;
import entities.event;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

public class gerer_evenementController {
    private services.servicesevent servicesevent = new servicesevent();

    @FXML
    private Label lb_event;

    @FXML
    private DatePicker tf_datedebut;

    @FXML
    private DatePicker tf_datefin;

    @FXML
    private TextField tf_nom;

    @FXML
    void Afficher_event(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Afficherevent.fxml"));
            tf_nom.getScene().setRoot(root);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    @FXML
    void Ajouter_event(ActionEvent event) {
        String nom = tf_nom.getText();
        LocalDate dateDebut = tf_datedebut.getValue();
        LocalDate dateFin = tf_datefin.getValue();
        LocalDate dateActuelle = LocalDate.now(); // Date actuelle

        if (nom.isEmpty() || dateDebut == null || dateFin == null) {
            // Afficher une alerte si des champs sont vides
            showAlert(Alert.AlertType.ERROR, "Champs incomplets", "Veuillez remplir tous les champs.");
            return;
        }

        if (dateDebut.isBefore(dateActuelle)) {
            // Afficher une alerte si la date de début est antérieure à la date actuelle
            showAlert(Alert.AlertType.ERROR, "Date invalide", "La date de début doit être postérieure à la date actuelle.");
            return;
        }

        if (dateFin.isBefore(dateDebut)) {
            // Afficher une alerte si la date de fin est antérieure à la date de début
            showAlert(Alert.AlertType.ERROR, "Date invalide", "La date de fin doit être postérieure à la date de début.");
            return;
        }

        try {
            // Vérifier si un événement avec les mêmes données existe déjà
            if (servicesevent.existeEvent(nom, Date.valueOf(dateDebut), Date.valueOf(dateFin))) {
                showAlert(Alert.AlertType.ERROR, "Événement déjà existant", "Un événement avec les mêmes données existe déjà.");
                return;
            }

            // Ajouter l'événement si tout est valide et unique
            servicesevent.add(new event(nom, Date.valueOf(dateDebut), Date.valueOf(dateFin)));
            tf_nom.clear();
            tf_datedebut.setValue(null);
            tf_datefin.setValue(null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }


    @FXML
    void Retour_pageaccueil(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Interface.fxml"));
            tf_nom.getScene().setRoot(root);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void Voir_Abonnements(ActionEvent event) {
        // Code pour visualiser les abonnements
    }

    @FXML
    void Voir_Cours(ActionEvent event) {
        // Code pour visualiser les cours
    }

    @FXML
    void Voir_Equipements(ActionEvent event) {
        // Code pour visualiser les équipements
    }

    @FXML
    void Voir_Evenements(ActionEvent event) {
        // Code pour visualiser les événements
    }

    @FXML
    void Voir_Formations(ActionEvent event) {
        // Code pour visualiser les formations
    }
}
