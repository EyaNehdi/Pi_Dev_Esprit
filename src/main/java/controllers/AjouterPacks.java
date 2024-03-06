package controllers;

import java.awt.event.ActionEvent;
import java.io.File;
import java.sql.SQLException;

import entities.Packs;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import services.PacksService;
import javafx.scene.media.MediaView;

public class AjouterPacks {


    @FXML
    private Button btnAjouter;

    @FXML
    private TextField descriptionf;

    @FXML
    private TextField nomf;

    @FXML
    private TextField prixf;
    @FXML
    private MediaView video;
    @FXML
    private TextField typef;

    @FXML
    private void ajouterPacks() {
        String nom = nomf.getText().trim();
        String description = descriptionf.getText().trim();
        String type = typef.getText().trim();
        String prixText = prixf.getText().trim();

        // Vérifier si les champs sont vides
        if (nom.isEmpty() || description.isEmpty() || type.isEmpty() || prixText.isEmpty()) {
            afficherAlerte("Erreur", "Veuillez remplir tous les champs.");
            return;
        }

        // Vérifier si le prix est un entier valide
        int prix;
        try {
            prix = Integer.parseInt(prixText);
            if (prix <= 0) {
                afficherAlerte("Erreur", "Le prix doit être un entier positif.");
                return;
            }
        } catch (NumberFormatException e) {
            afficherAlerte("Erreur", "Le prix doit être un nombre entier.");
            return;
        }

        // Si toutes les vérifications passent, créer et ajouter le pack
        Packs packs = new Packs();
        packs.setPrix(prix);
        packs.setNom(nom);
        packs.setDescription(description);
        packs.setType(type);

        PacksService servicepacks= new PacksService();

        try {
            servicepacks.add(packs);

            afficherAlerte("Succès", "Ajout de pack avec succès");

            Stage stage = (Stage) btnAjouter.getScene().getWindow();
            stage.close();
            // Changer de scène après l'ajout
            // Stage stage = (Stage) btnAjouter.getScene().getWindow();
            // SceneChanger.changerScene("/AfficherCours.fxml", stage);

        } catch (SQLException e) {
            afficherAlerte("Erreur", "Échec d'ajout de pack");
            e.printStackTrace();
        }
    }
    @FXML
    private void chooseVideoFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une vidéo");
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            String videoPath = file.toURI().toString();
            Media media = new Media(videoPath);
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            video.setMediaPlayer(mediaPlayer);
            mediaPlayer.play();
        }
    }
    private void afficherAlerte(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
