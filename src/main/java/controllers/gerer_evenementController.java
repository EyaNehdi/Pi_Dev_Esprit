package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import services.servicesevent;
import entities.event;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Calendar;

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
    private Label dateLabel;

    @FXML
    private Button btn_Song;

    @FXML
    private Button Calendrier;
    private void CalendarManager() {
        // Implémentation de base pour le gestionnaire de calendrier
        Calendar calendar = Calendar.getInstance();
        updateDateLabel(calendar); // Mettre à jour l'étiquette avec la date actuelle
    }

    // Méthode pour mettre à jour l'étiquette avec la date actuelle
    private void updateDateLabel(Calendar calendar) {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Mettre à jour le texte de l'étiquette avec la date actuelle
        dateLabel.setText("Date actuelle : " + year + "-" + month + "-" + day);
    }

    // Méthode pour gérer l'événement associé au bouton "Calendrier"
    @FXML
    void Calendrier(ActionEvent event) {
        CalendarManager(); // Appeler la méthode pour gérer le calendrier
    }

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
   void song(ActionEvent event) {
       String mediaPath = "C:\\Users\\ichraq\\IdeaProjects\\workpidev\\src\\main\\resources\\Images\\Loreen - Tattoo.mp3"; // Assurez-vous de spécifier le chemin complet avec l'extension du fichier
       try {
           Media media = new Media(new File(mediaPath).toURI().toString());
           MediaPlayer mediaPlayer = new MediaPlayer(media);
           mediaPlayer.play();
       } catch (NullPointerException e) {
           showAlert(Alert.AlertType.ERROR, "Erreur de lecture du média", "Le chemin d'accès au média est invalide.");
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
            servicesevent.ajouter(new event(nom, Date.valueOf(dateDebut), Date.valueOf(dateFin)));
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
