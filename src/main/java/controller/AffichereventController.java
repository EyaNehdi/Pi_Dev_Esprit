package controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.event.ActionEvent;
import entities.event;
import services.servicesevent;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;

public class AffichereventController {
    services.servicesevent servicesevent = new servicesevent();

    @FXML
    private TableColumn<event, String> col_date;

    @FXML
    private TableColumn<event, String> col_nom;

    @FXML
    private TableView<event> tv_event;

    @FXML
    private TextField tf_id;

    @FXML
    void Modifier_event(ActionEvent event) {

    }


    @FXML
    void Retour(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/gerer_evenement.fxml"));
            tv_event.getScene().setRoot(root);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    @FXML
    void Supprimer_event(ActionEvent event) {
        // Récupérer l'événement sélectionné dans la table
        event selectedEvent = tv_event.getSelectionModel().getSelectedItem();
        if (selectedEvent == null) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez sélectionner un événement à supprimer.");
            return;
        }

        try {
            // Appeler la méthode de service pour supprimer l'événement
            servicesevent.supprimer(selectedEvent.getId());

            // Mise à jour de l'affichage de la table
            tv_event.getItems().remove(selectedEvent);

            // Afficher un message de succès
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Événement supprimé avec succès");
        } catch (SQLException e) {
            // Gérer les exceptions
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la suppression de l'événement: " + e.getMessage());
        }
    }

    @FXML
    void initialize() {
        try {
            ObservableList<event> events = FXCollections.observableList(servicesevent.afficher());
            tv_event.setItems(events);
            col_nom.setCellValueFactory(new PropertyValueFactory<>("nom_evenement"));
            col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

