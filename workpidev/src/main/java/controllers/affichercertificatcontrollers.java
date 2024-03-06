package controllers;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.SnapshotResult;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.EnumMap;
import entities.certificat;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import org.apache.commons.io.FileUtils;
import services.servicescertificat;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Optional;

public class affichercertificatcontrollers {
    @FXML
    private Button capture;
    @FXML
    private TableView<certificat> tf_tableview;
    @FXML
    private Button print;
    @FXML
    private TableColumn<certificat, String> tf_cert;

    @FXML
    private TableColumn<certificat, String> tf_nom;

    @FXML
    private TableColumn<certificat, String> tf_nomf;

    @FXML
    private TableColumn<certificat, String> tf_date;

    @FXML
    private Button bn_sup;

    @FXML
    private Button modcer;
    @FXML
    void print(ActionEvent event) {
        certificat selectedCertificat = tf_tableview.getSelectionModel().getSelectedItem();

        if (selectedCertificat != null) {
            printCertificate(selectedCertificat);
        } else {
            showAlert(Alert.AlertType.WARNING, "Aucun Certificat Sélectionné", "Veuillez sélectionner un certificat à imprimer.");
        }
    }
    @FXML
    void capture(ActionEvent event) { try {
        // Récupérez la scène actuelle
        Scene scene = tf_tableview.getScene();

        // Créez un objet WritableImage pour la capture d'écran
        WritableImage image = scene.snapshot(null);

        // Obtenez le chemin du bureau
        String desktopPath = System.getProperty("user.home") + "/Desktop";

        // Créez un chemin complet pour le fichier sur le bureau
        String imagePath = desktopPath + "/capture_ecran.png";

        // Créez un fichier pour le chemin sur le bureau
        File file = new File(imagePath);

        // Créez une instance de PixelReader pour lire les pixels de l'image
        PixelReader pixelReader = image.getPixelReader();

        // Créez une image BufferedImage avec le même nombre de pixels que l'image JavaFX
        BufferedImage bufferedImage = new BufferedImage((int) image.getWidth(), (int) image.getHeight(), BufferedImage.TYPE_INT_ARGB);

        // Pour chaque pixel, définissez la couleur dans l'image BufferedImage
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                javafx.scene.paint.Color color = ((PixelReader) pixelReader).getColor(x, y);
                int argb = (int) (color.getOpacity() * 255) << 24 |
                        (int) (color.getRed() * 255) << 16 |
                        (int) (color.getGreen() * 255) << 8 |
                        (int) (color.getBlue() * 255);
                bufferedImage.setRGB(x, y, argb);
            }
        }

        // Enregistrez l'image BufferedImage dans le fichier sur le bureau
        ImageIO.write(bufferedImage, "png", file);

        // Affichez un message d'information
        showAlert(Alert.AlertType.INFORMATION, "Capture d'écran réussie", "La capture d'écran a été enregistrée avec succès sur le bureau.");

    } catch (Exception e) {
        showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la capture d'écran : " + e.getMessage());
    }
    }
    private void printCertificate(certificat selectedCertificat) {
        try {
            // Préparer le contenu HTML pour l'impression
            String htmlContent =
                    "<!DOCTYPE html>\n" +
                            "<html lang=\"en\">\n" +
                            "<head>\n" +
                            "    <meta charset=\"UTF-8\">\n" +
                            "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                            "    <title>Certificate of Completion</title>\n" +
                            "    <style>\n" +
                            "        body {\n" +
                            "            font-family: 'Arial', sans-serif;\n" +
                            "            background-color: #f2f2f2;\n" +
                            "            margin: 0;\n" +
                            "            padding: 20px;\n" +
                            "        }\n" +
                            "\n" +
                            "        .certificate {\n" +
                            "            max-width: 600px;\n" +
                            "            margin: 0 auto;\n" +
                            "            background-color: #fff;\n" +
                            "            border: 1px solid #ccc;\n" +
                            "            padding: 20px;\n" +
                            "            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);\n" +
                            "        }\n" +
                            "\n" +
                            "        h1 {\n" +
                            "            color: #333;\n" +
                            "            text-align: center;\n" +
                            "        }\n" +
                            "\n" +
                            "        .details {\n" +
                            "            margin-top: 30px;\n" +
                            "        }\n" +
                            "\n" +
                            "        .details p {\n" +
                            "            margin: 10px 0;\n" +
                            "            color: #555;\n" +
                            "        }\n" +
                            "\n" +
                            "        .signature {\n" +
                            "            display: block;\n" +
                            "            margin-top: 50px;\n" +
                            "            text-align: center;\n" +
                            "        }\n" +
                            "\n" +
                            "        .signature img {\n" +
                            "            max-width: 150px;\n" +
                            "            height: auto;\n" +
                            "        }\n" +
                            "    </style>\n" +
                            "</head>\n" +
                            "<body>\n" +
                            "    <div class=\"certificate\">\n" +
                            "        <h1>Certificate of Completion</h1>\n" +
                            "        \n" +
                            "        <div class=\"details\">\n" +
                            "            <p><strong>Name:</strong> " + selectedCertificat.getNomeleve() + "</p>\n" +
                            "            <p><strong>Instructor:</strong> " + selectedCertificat.getNomformateur() + "</p>\n" +
                            "            <p><strong>Course:</strong> " + selectedCertificat.getNom() + "</p>\n" +
                            "            <p><strong>Date:</strong> " + selectedCertificat.getDate() + "</p>\n" +
                            "        </div>\n" +
                            "\n" +
                            "        <div class=\"signature\">\n" +
                            "            <img src=\"Assets/sig.png\" alt=\"Signature\">\n" +
                            "        </div>\n" +
                            "    </div>\n" +
                            "</body>\n" +
                            "</html>";


            // Créer un fichier HTML temporaire
            File tempHtmlFile = File.createTempFile("certificate", ".html");
            FileUtils.writeStringToFile(tempHtmlFile, htmlContent, StandardCharsets.UTF_8);

            // Ouvrir le fichier HTML dans le navigateur par défaut du système
            Desktop.getDesktop().browse(tempHtmlFile.toURI());
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de l'impression du certificat : " + e.getMessage());
        }
    }


    @FXML
    void supprimercertif() {
        certificat selectedCertificat = tf_tableview.getSelectionModel().getSelectedItem();

        if (selectedCertificat != null) {
            try {
                // Utilisation du serviceCertificat pour supprimer le certificat
                servicescertificat serviceCertificat = new servicescertificat();
                serviceCertificat.supprimer(selectedCertificat.getId());
                tf_tableview.getItems().remove(selectedCertificat);
                showAlert(Alert.AlertType.INFORMATION, "Certificat Supprimé", "Le certificat a été supprimé avec succès.");
            } catch (SQLException e) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la suppression du certificat : " + e.getMessage());
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Aucun Certificat Sélectionné", "Veuillez sélectionner un certificat à supprimer.");
        }
    }

    @FXML
    void initialize() {
        try {
            servicescertificat serviceCertificat = new servicescertificat();
            ObservableList<certificat> certificats = FXCollections.observableList(serviceCertificat.afficher());

            // Utilisation des noms corrects des propriétés de la classe certificat
            tf_cert.setCellValueFactory(new PropertyValueFactory<>("nom"));
            tf_nom.setCellValueFactory(new PropertyValueFactory<>("nomeleve"));
            tf_nomf.setCellValueFactory(new PropertyValueFactory<>("nomformateur"));
            tf_date.setCellValueFactory(new PropertyValueFactory<>("date"));

            tf_tableview.setItems(certificats);
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur d'initialisation", "Erreur lors de l'initialisation : " + e.getMessage());
        }
    }

    // Méthode auxiliaire pour afficher une alerte
    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    void modifiercer(javafx.event.ActionEvent event) {
        certificat selectedCertificat = tf_tableview.getSelectionModel().getSelectedItem();

        if (selectedCertificat != null) {
            boolean modificationConfirme = showModificationDialog(selectedCertificat);

            if (modificationConfirme) {
                try {
                    // Mettez à jour le certificat dans la base de données
                    servicescertificat serviceCertificat = new servicescertificat();
                    serviceCertificat.modifier(selectedCertificat);

                    // Rafraîchir la TableView
                    ObservableList<certificat> certificats = FXCollections.observableList(serviceCertificat.afficher());
                    tf_tableview.setItems(certificats);

                    showAlert(Alert.AlertType.INFORMATION, "Certificat Modifié", "Le certificat a été modifié avec succès.");
                } catch (SQLException e) {
                    showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la modification du certificat : " + e.getMessage());
                }
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Aucun Certificat Sélectionné", "Veuillez sélectionner un certificat à modifier.");
        }
    }

    private boolean showModificationDialog(certificat selectedCertificat) {
        // Créer une fenêtre de dialogue pour la modification
        Dialog<Boolean> dialog = new Dialog<>();
        dialog.setTitle("Modifier le Certificat");
        dialog.setHeaderText(null);

        // Configuration des boutons
        ButtonType modifierButtonType = new ButtonType("Modifier", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(modifierButtonType, ButtonType.CANCEL);

        // Création des champs de saisie
        TextField tf_nom = new TextField(selectedCertificat.getNom());
        TextField tf_nomeleve = new TextField(selectedCertificat.getNomeleve());
        TextField tf_nomformateur = new TextField(selectedCertificat.getNomformateur());

        // Change the date parsing format here
        DatePicker dp_date = new DatePicker(LocalDate.parse(selectedCertificat.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(new Label("Nom:"), 0, 0);
        grid.add(tf_nom, 1, 0);
        grid.add(new Label("Nom de l'élève:"), 0, 1);
        grid.add(tf_nomeleve, 1, 1);
        grid.add(new Label("Nom du formateur:"), 0, 2);
        grid.add(tf_nomformateur, 1, 2);
        grid.add(new Label("Date:"), 0, 3);
        grid.add(dp_date, 1, 3);

        dialog.getDialogPane().setContent(grid);

        // Activation du bouton de modification lorsque les champs sont valides
        Node modifierButton = dialog.getDialogPane().lookupButton(modifierButtonType);
        updateModifierButtonState(modifierButton, tf_nom, tf_nomeleve, tf_nomformateur, dp_date);

        // Ajout d'un écouteur pour activer le bouton lors de la saisie
        tf_nom.textProperty().addListener((observable, oldValue, newValue) ->
                updateModifierButtonState(modifierButton, tf_nom, tf_nomeleve, tf_nomformateur, dp_date));
        tf_nomeleve.textProperty().addListener((observable, oldValue, newValue) ->
                updateModifierButtonState(modifierButton, tf_nom, tf_nomeleve, tf_nomformateur, dp_date));
        tf_nomformateur.textProperty().addListener((observable, oldValue, newValue) ->
                updateModifierButtonState(modifierButton, tf_nom, tf_nomeleve, tf_nomformateur, dp_date));
        dp_date.valueProperty().addListener((observable, oldValue, newValue) ->
                updateModifierButtonState(modifierButton, tf_nom, tf_nomeleve, tf_nomformateur, dp_date));

        // Set initial button state
        updateModifierButtonState(modifierButton, tf_nom, tf_nomeleve, tf_nomformateur, dp_date);

        // Résultat de la fenêtre de dialogue
        dialog.setResultConverter(dialogButton -> dialogButton == modifierButtonType);

        Optional<Boolean> result = dialog.showAndWait();
        if (result.isPresent() && result.get()) {
            // Mettre à jour les propriétés du certificat
            selectedCertificat.setNom(tf_nom.getText());
            selectedCertificat.setNomeleve(tf_nomeleve.getText());
            selectedCertificat.setNomformateur(tf_nomformateur.getText());

            // Update date parsing format here
            selectedCertificat.setDate(dp_date.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            return true;
        } else {
            return false;
        }
    }

    private void updateModifierButtonState(Node modifierButton, TextField tf_nom, TextField tf_nomeleve, TextField tf_nomformateur, DatePicker dp_date) {
        modifierButton.setDisable(
                tf_nom.getText().isEmpty() ||
                        tf_nomeleve.getText().isEmpty() ||
                        tf_nomformateur.getText().isEmpty() ||
                        dp_date.getEditor().getText().isEmpty()
        );
        System.out.println("Button state updated: " + !modifierButton.isDisable());
    }


    // Vérifier si les champs de saisie sont valides
    private boolean areFieldsValid(String nom, String nomeleve, String nomformateur, String date) {
        return !nom.trim().isEmpty() && !nomeleve.trim().isEmpty() && !nomformateur.trim().isEmpty() && isValidDate(date);
    }

    // Vérifier si la date a le format correct "yyyy-MM-dd"
    private boolean isValidDate(String date) {
        return !date.trim().isEmpty() && date.matches("\\d{4}-\\d{2}-\\d{2}");
    }
    @FXML
    private void generateQRCode() {
        certificat selectedCertificat = tf_tableview.getSelectionModel().getSelectedItem();

        if (selectedCertificat != null) {
            try {
                // Génération du contenu du QR code
                String qrCodeContent = "Nom: " + selectedCertificat.getNom() + "\n" +
                        "Nom de l'élève: " + selectedCertificat.getNomeleve() + "\n" +
                        "Nom du formateur: " + selectedCertificat.getNomformateur() + "\n" +
                        "Date: " + selectedCertificat.getDate();

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
