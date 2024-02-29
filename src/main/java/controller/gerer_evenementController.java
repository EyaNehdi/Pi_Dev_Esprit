package controller;

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
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Champs incomplets");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs.");
            alert.showAndWait();
            return;
        }

        if (dateDebut.isBefore(dateActuelle)) {
            // Date de début avant la date actuelle
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Date invalide");
            alert.setHeaderText(null);
            alert.setContentText("La date de début doit être postérieure à la date actuelle.");
            alert.showAndWait();
            return;
        }

        if (dateFin.isBefore(dateDebut)) {
            // Date de fin avant la date de début
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Date invalide");
            alert.setHeaderText(null);
            alert.setContentText("La date de fin doit être postérieure à la date de début.");
            alert.showAndWait();
            return;
        }

        try {
            servicesevent.ajouter(new event(nom, Date.valueOf(dateDebut), Date.valueOf(dateFin)));
            tf_nom.clear();
            tf_datedebut.setValue(null);
            tf_datefin.setValue(null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
