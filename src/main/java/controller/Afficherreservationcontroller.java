package controller;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;


import entities.event;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import services.servicesreservation;
import entities.reservation;
import javafx.scene.control.Button;

public class Afficherreservationcontroller {

    services.servicesreservation servicesreservation = new servicesreservation();

    @FXML
    private Button btnGenerateQRCode;
    @FXML
    private TableColumn<reservation, String> col_Nomeleve;

    @FXML
    private TableColumn<reservation, String> col_nomevent;

    @FXML
    private TableView<reservation> tv_reservation;

    @FXML
    void Modifier_reservation(ActionEvent event) {
        reservation selectedReservation = tv_reservation.getSelectionModel().getSelectedItem();

        if (selectedReservation != null) {
            boolean modificationConfirme = showModificationDialog(selectedReservation);

            if (modificationConfirme) {
                try {
                    // Mettez à jour la réservation dans la base de données
                    servicesreservation.modifier(selectedReservation);

                    // Rafraîchir la TableView
                    ObservableList<reservation> reservations = FXCollections.observableList(servicesreservation.afficher());
                    tv_reservation.setItems(reservations);

                    showAlert(Alert.AlertType.INFORMATION, "Réservation Modifiée", "La réservation a été modifiée avec succès.");
                } catch (SQLException e) {
                    showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la modification de la réservation : " + e.getMessage());
                }
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Aucune Réservation Sélectionnée", "Veuillez sélectionner une réservation à modifier.");
        }
    }

   private boolean showModificationDialog(reservation selectedReservation) {
    // Créer une fenêtre de dialogue pour la modification
    Dialog<Boolean> dialog = new Dialog<>();
    dialog.setTitle("Modifier la Réservation");
    dialog.setHeaderText(null);

    // Configuration des boutons
    ButtonType modifierButtonType = new ButtonType("Modifier", ButtonBar.ButtonData.OK_DONE);
    dialog.getDialogPane().getButtonTypes().addAll(modifierButtonType, ButtonType.CANCEL);

    // Création de la TableView temporaire
    TableView<reservation> tempTableView = new TableView<>();
    TableColumn<reservation, String> colEventId = new TableColumn<>("ID de l'événement");
    colEventId.setCellValueFactory(new PropertyValueFactory<>("event_id"));
    TableColumn<reservation, String> colUserId = new TableColumn<>("ID de l'utilisateur");
    colUserId.setCellValueFactory(new PropertyValueFactory<>("id_user"));
    tempTableView.getColumns().addAll(colEventId, colUserId);
    tempTableView.getItems().add(selectedReservation);

    GridPane grid = new GridPane();
    grid.setHgap(10);
    grid.setVgap(10);
    grid.add(tempTableView, 0, 0);

    dialog.getDialogPane().setContent(grid);

    // Activation du bouton de modification lorsque les champs sont valides
    Node modifierButton = dialog.getDialogPane().lookupButton(modifierButtonType);
    modifierButton.setDisable(false); // Le bouton est toujours activé pour modifier une ligne existante

    // Résultat de la fenêtre de dialogue
    dialog.setResultConverter(dialogButton -> dialogButton == modifierButtonType);

    Optional<Boolean> result = dialog.showAndWait();
    if (result.isPresent() && result.get()) {
        // Mettre à jour les propriétés de la réservation avec les données de la TableView temporaire
        selectedReservation.setId_user(tempTableView.getItems().get(0).getId_user());
        selectedReservation.setEvent_id(tempTableView.getItems().get(0).getEvent_id());

        return true;
    } else {
        return false;
    }
}

    private void updateModifierButtonState(Node modifierButton, TextField tf_nom, TextField tf_prenom) {
        modifierButton.setDisable(
                tf_nom.getText().isEmpty() ||
                        tf_prenom.getText().isEmpty()
        );
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
            // Récupérer la liste des réservations depuis le service
            ObservableList<reservation> reservations = FXCollections.observableList(servicesreservation.afficher());

            // Définir les cellules de chaque colonne avec les données appropriées
            col_Nomeleve.setCellValueFactory(new PropertyValueFactory<>("id_user"));
            col_nomevent.setCellValueFactory(new PropertyValueFactory<>("event_id"));

            // Ajouter les réservations à la TableView
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
 /*   @FXML
    private void generateQRCode() {
        reservation selectedReservation = tv_reservation.getSelectionModel().getSelectedItem();

        if (selectedReservation != null) {
            try {
                // Génération du contenu du QR code
                String qrCodeContent = "Nom: " + selectedReservation.getNom() + "\n" +
                        "Nom de l'événement: " + selectedReservation.getEvent_id() + "\n" +
                        "Nom du personne: " + selectedReservation.getId_user() + "\n" +
                        "Date: " + selectedReservation.getDate();

                // Création d'un fichier temporaire pour le QR code
                File tempFile = File.createTempFile("qrcode", ".png");

                // Écriture du contenu du QR code dans le fichier temporaire
                QRCodeWriter qrCodeWriter = new QRCodeWriter();
                BitMatrix bitMatrix = qrCodeWriter.encode(qrCodeContent, BarcodeFormat.QR_CODE, 200, 200);
                MatrixToImageWriter.writeToPath(bitMatrix, "PNG", tempFile.toPath());

                // Affichage d'un message d'information avec titre et contenu
                showAlert(Alert.AlertType.INFORMATION, "QR Code Généré",
                        "Le QR code a été généré avec succès pour le certificat:\n\n" + qrCodeContent);

                // Ouverture du fichier QR code
                Desktop.getDesktop().open(tempFile);
            } catch (IOException | WriterException e) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la génération du QR code : " + e.getMessage());
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Aucun Certificat Sélectionné", "Veuillez sélectionner un certificat pour générer le QR code.");
        }
    }*/

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
