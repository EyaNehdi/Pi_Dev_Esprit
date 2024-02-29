package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import entities.event;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import services.servicesreservation;
import entities.reservation;
import javafx.scene.control.TableView;

public class Afficherreservationcontroller {

    services.servicesreservation servicesreservation = new servicesreservation();


    @FXML
    private TableColumn<reservation, String> col_Nomeleve;

    @FXML
    private TableColumn<reservation, String> col_nomevent;

    @FXML
    private TableView<reservation> tv_reservation;

    @FXML
    void Modifier_reservation(ActionEvent event) {

    }

    @FXML
    void Supprimer_reservation(ActionEvent event) {
        reservation selectedReservation = tv_reservation.getSelectionModel().getSelectedItem();
        if (selectedReservation == null) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez sélectionner un événement à supprimer.");
            return;
        }

        try {
            servicesreservation.supprimer(selectedReservation.getId());

            tv_reservation.getItems().remove(selectedReservation);

            // Afficher un message de succès
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Événement supprimé avec succès");
        } catch (SQLException e) {
            // Gérer les exceptions
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la suppression de l'événement: " + e.getMessage());
        }
    }


    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    void initialize() {
        try {
            // Récupérer la liste des événements depuis le service
            ObservableList<reservation> reservations = FXCollections.observableList(servicesreservation.afficher());

            // Définir les cellules de chaque colonne avec les données appropriées
            col_Nomeleve.setCellValueFactory(new PropertyValueFactory<>("ID_USER"));
            col_nomevent.setCellValueFactory(new PropertyValueFactory<>("ID_EVENT"));

            // Ajouter les événements à la TableView
            tv_reservation.setItems(reservations);
        } catch (SQLException e) {
            // Gérer les exceptions
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de l'affichage des réservations : " + e.getMessage());
        }

    }

    @FXML
    void Retour_reservation(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Ajouterreservation.fxml"));
            tv_reservation.getScene().setRoot(root);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

}
