package controllers;

import entities.event;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.Node;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Optional;
import java.time.format.DateTimeFormatter;

public class AffichereventController {
    services.servicesevent servicesevent = new services.servicesevent();

    @FXML
    private Button btn_modifier;

    @FXML
    private Button btn_supprimer;

    @FXML
    private TextField fxrecherche;

    @FXML
    private TableColumn<event, String> col_nom;

    @FXML
    private TableColumn<event, Date> col_date;

    @FXML
    private TableColumn<event, Date> col_datefin;

    @FXML
    private Label lb_event;

    @FXML
    private TableView<event> tv_event;

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
    void chercher(ActionEvent event) {
        String rechercheText = fxrecherche.getText().trim().toLowerCase();

        try {
            ObservableList<event> formations = FXCollections.observableArrayList(servicesevent.afficher());

            if (rechercheText.isEmpty()) {
                tv_event.setItems(formations);
            } else {
                // Filtrer par nom d'événement uniquement
                ObservableList<event> filteredFormations = formations.filtered(f ->
                        f.getNom_evenement().toLowerCase().contains(rechercheText)
                );

                tv_event.setItems(filteredFormations);
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la recherche de l'événement : " + e.getMessage());
        }
    }

    @FXML
    void btn_reservation(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Ajouterreservation.fxml"));
            tv_event.getScene().setRoot(root);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void Supprimer_event(ActionEvent event) {
        event selectedEvent = tv_event.getSelectionModel().getSelectedItem();
        if (selectedEvent != null) {
            try {
                servicesevent.supprimer(selectedEvent.getId());
                tv_event.getItems().remove(selectedEvent);
                showAlert(Alert.AlertType.INFORMATION, "Événement Supprimé", "L'événement a été supprimé avec succès.");
            } catch (SQLException e) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la suppression de l'événement : " + e.getMessage());
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Aucun Événement Sélectionné", "Veuillez sélectionner un événement à supprimer.");
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
            ObservableList<event> events = FXCollections.observableList(servicesevent.afficher());

            // Définir les cellules de chaque colonne avec les données appropriées
            col_nom.setCellValueFactory(new PropertyValueFactory<>("nom_evenement"));
            col_date.setCellValueFactory(new PropertyValueFactory<>("Date_debut"));
            col_datefin.setCellValueFactory(new PropertyValueFactory<>("Date_fin"));

            // Convertir les dates en format lisible dans les cellules
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            col_date.setCellFactory(column -> new TableCell<event, Date>() {
                @Override
                protected void updateItem(Date date, boolean empty) {
                    super.updateItem(date, empty);
                    if (empty || date == null) {
                        setText(null);
                    } else {
                        setText(dateFormatter.format(date.toLocalDate()));
                    }
                }
            });

            col_datefin.setCellFactory(column -> new TableCell<event, Date>() {
                @Override
                protected void updateItem(Date date, boolean empty) {
                    super.updateItem(date, empty);
                    if (empty || date == null) {
                        setText(null);
                    } else {
                        setText(dateFormatter.format(date.toLocalDate()));
                    }
                }
            });

            // Ajouter les événements à la TableView
            tv_event.setItems(events);
        } catch (SQLException e) {
            // Gérer les exceptions
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de l'affichage des événements : " + e.getMessage());
        }
    }

    @FXML
    void Modifier_event(ActionEvent event) {
        event selectedEvent = tv_event.getSelectionModel().getSelectedItem();
        services.servicesevent servicesevent = new services.servicesevent();

        if (selectedEvent != null) {
            boolean modificationConfirme = showModificationDialog(selectedEvent);

            if (modificationConfirme) {
                try {
                    // Mettez à jour l'événement dans la base de données
                    boolean modificationReussie = servicesevent.modifier(selectedEvent);

                    if (modificationReussie) {
                        // Rafraîchir la TableView
                        ObservableList<event> events = FXCollections.observableList(servicesevent.afficher());
                        tv_event.setItems(events);

                        showAlert(Alert.AlertType.INFORMATION, "Événement Modifié", "L'événement a été modifié avec succès.");
                    } else {
                        showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la modification de l'événement.");
                    }
                } catch (SQLException e) {
                    showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la modification de l'événement : " + e.getMessage());
                }
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Aucun Événement Sélectionné", "Veuillez sélectionner un événement à modifier.");
        }
    }

    private boolean showModificationDialog(event selectedEvent) {
        // Créer une fenêtre de dialogue pour la modification
        Dialog<Boolean> dialog = new Dialog<>();
        dialog.setTitle("Modifier l'Événement");
        dialog.setHeaderText(null);

        // Configuration des boutons
        ButtonType modifierButtonType = new ButtonType("Modifier", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(modifierButtonType, ButtonType.CANCEL);

        // Création des champs de saisie
        TextField tf_nom = new TextField(selectedEvent.getNom_evenement());
        DatePicker dp_datedebut = new DatePicker(selectedEvent.getDate_debut().toLocalDate());
        DatePicker dp_datefin = new DatePicker(selectedEvent.getDate_fin().toLocalDate());

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(new Label("Nom de l'événement:"), 0, 0);
        grid.add(tf_nom, 1, 0);
        grid.add(new Label("Date de début:"), 0, 1);
        grid.add(dp_datedebut, 1, 1);
        grid.add(new Label("Date de fin:"), 0, 2);
        grid.add(dp_datefin, 1, 2);

        dialog.getDialogPane().setContent(grid);

        // Activation du bouton de modification lorsque les champs sont valides
        Node modifierButton = dialog.getDialogPane().lookupButton(modifierButtonType);
        updateModifierButtonState(modifierButton, tf_nom, dp_datedebut, dp_datefin);

        // Ajout d'un écouteur pour activer le bouton lors de la saisie
        tf_nom.textProperty().addListener((observable, oldValue, newValue) ->
                updateModifierButtonState(modifierButton, tf_nom, dp_datedebut, dp_datefin));
        dp_datedebut.valueProperty().addListener((observable, oldValue, newValue) ->
                updateModifierButtonState(modifierButton, tf_nom, dp_datedebut, dp_datefin));
        dp_datefin.valueProperty().addListener((observable, oldValue, newValue) ->
                updateModifierButtonState(modifierButton, tf_nom, dp_datedebut, dp_datefin));

        // Set initial button state
        updateModifierButtonState(modifierButton, tf_nom, dp_datedebut, dp_datefin);

        // Résultat de la fenêtre de dialogue
        dialog.setResultConverter(dialogButton -> dialogButton == modifierButtonType);

        Optional<Boolean> result = dialog.showAndWait();
        if (result.isPresent() && result.get()) {
            // Mettre à jour les propriétés de l'événement
            selectedEvent.setNom_evenement(tf_nom.getText());
            selectedEvent.setDate_debut(Date.valueOf(dp_datedebut.getValue()));
            selectedEvent.setDate_fin(Date.valueOf(dp_datefin.getValue()));

            return true;
        } else {
            return false;
        }
    }

    private void updateModifierButtonState(Node modifierButton, TextField tf_nom, DatePicker dp_datedebut, DatePicker dp_datefin) {
        modifierButton.setDisable(
                tf_nom.getText().isEmpty() ||
                        dp_datedebut.getValue() == null ||
                        dp_datefin.getValue() == null
        );
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
