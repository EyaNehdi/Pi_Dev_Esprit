package controllers;

import java.io.IOException;
import java.sql.SQLException;

import entities.reservation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.servicesreservation;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

public class AjouterreservationController {

    private services.servicesreservation servicesreservation = new servicesreservation();

    @FXML
    private Label lb_event;

    @FXML
    private Text lb_user;

    @FXML
    private TextField tf_idevent;

    @FXML
    private TextField tf_iduser;

    /*
    @FXML
    void Afficher_reservation(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Afficherreservation.fxml"));
            tf_idevent.getScene().setRoot(root);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }*/

@FXML
    public void AfficherReservation(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Afficherreservation.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }






    @FXML
    void Ajouter_reservation(ActionEvent event) {
        String idEventText = tf_idevent.getText().trim();
        String idUserText = tf_iduser.getText().trim();

        // Vérifier si les champs ne sont pas vides
        if (idEventText.isEmpty() || idUserText.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Champs incomplets", "Veuillez remplir tous les champs.");
            return;
        }

        // Vérifier si les identifiants contiennent uniquement des chiffres
        if (!idEventText.matches("\\d+") || !idUserText.matches("\\d+")) {
            showAlert(Alert.AlertType.ERROR, "Format incorrect", "Les identifiants doivent contenir uniquement des chiffres.");
            return;
        }

        int idEvent = Integer.parseInt(idEventText);
        int idUser = Integer.parseInt(idUserText);
        // Ajouter la réservation en utilisant votre service
        try {
            servicesreservation.add(new reservation(idEvent, idUser));
            showAlert(Alert.AlertType.INFORMATION, "Réservation ajoutée", "La réservation a été ajoutée avec succès.");
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur lors de l'ajout", "Une erreur s'est produite lors de l'ajout de la réservation : " + e.getMessage());
        }
    }
    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

        @FXML
        void Voir_Abonnements(ActionEvent event) {

        }

        @FXML
        void Voir_Cours(ActionEvent event) {

        }

        @FXML
        void Voir_Equipements(ActionEvent event) {

        }

        @FXML
        void Voir_Evenements(ActionEvent event) {

        }

        @FXML
        void Voir_Formations(ActionEvent event) {

        }

        @FXML
        void retour(ActionEvent event) {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/Afficherevent.fxml"));
                tf_iduser.getScene().setRoot(root);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }

        }


    }



