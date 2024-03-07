package controllers;

import entities.event;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.Node;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import services.servicesevent;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFCell;

public class AffichereventController {
    services.servicesevent servicesevent = new services.servicesevent();

    @FXML
    private Button btn_modifier;

    @FXML
    private Button btn_supprimer;
    @FXML
    private Button pdf;
    @FXML
    private Button Excel;

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
    void telecharge_e(ActionEvent event) {
        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Evénements");

            // Création de l'en-tête
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Nom");
            headerRow.createCell(1).setCellValue("Date de début");
            headerRow.createCell(2).setCellValue("Date de fin");

            // Ajout des données de la TableView
            ObservableList<event> events = tv_event.getItems();
            int rowNum = 1;
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            for (event event1 : events) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(event1.getNom_evenement());
                row.createCell(1).setCellValue(dateFormatter.format(event1.getDate_debut().toLocalDate()));
                row.createCell(2).setCellValue(dateFormatter.format(event1.getDate_fin().toLocalDate()));
            }

            String desktopPath = System.getProperty("user.home") + "/Desktop/";
            File file = new File(desktopPath + "ListeEvénements.xlsx");
            try (FileOutputStream outputStream = new FileOutputStream(file)) {
                workbook.write(outputStream);
            }

            showAlert(Alert.AlertType.INFORMATION, "Exportation Excel réussie", "La liste des Evénements a été exportée avec succès en format Excel sur votre bureau.");
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de l'exportation du fichier Excel : " + e.getMessage());
        }
    }


    @FXML
    void telecharge(ActionEvent event) {
        try {
            // Création d'un nouveau document PDF
            PDDocument document = new PDDocument();

            // Création d'une nouvelle page
            PDPage page = new PDPage();
            document.addPage(page);

            // Configuration du contenu du PDF
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                // Titre du document
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 20);
                contentStream.setNonStrokingColor(0, 0, 255); // Couleur du texte (bleu)
                contentStream.beginText();
                contentStream.setLeading(25);
                contentStream.newLineAtOffset(100, 700);
                contentStream.showText("Liste des Événements");
                contentStream.endText();

                // Ligne de séparation
                contentStream.moveTo(100, 690);
                contentStream.lineTo(page.getMediaBox().getWidth() - 100, 690);
                contentStream.stroke();

                // Configuration pour le contenu de la TableView
                contentStream.setFont(PDType1Font.HELVETICA, 14);
                int yPosition = 650; // Ajustez la position selon vos besoins

                // Ajout des données de la TableView
                ObservableList<event> events = tv_event.getItems();
                for (event event1 : events) {
                    yPosition -= 80; // Ajustez l'espacement entre les lignes selon vos besoins
                    contentStream.beginText();
                    contentStream.newLineAtOffset(100, yPosition);
                    contentStream.setNonStrokingColor(0, 0, 0); // Couleur du texte (noir)
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16); // Police en gras
                    contentStream.showText("Nom: ");
                    contentStream.setFont(PDType1Font.HELVETICA, 14); // Retour à la police normale
                    contentStream.showText(event1.getNom_evenement());
                    contentStream.newLine();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16); // Police en gras
                    contentStream.showText("Date début: ");
                    contentStream.setFont(PDType1Font.HELVETICA, 14); // Retour à la police normale
                    contentStream.showText(event1.getDate_debut().toString());
                    contentStream.newLine();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16); // Police en gras
                    contentStream.showText("Date fin: ");
                    contentStream.setFont(PDType1Font.HELVETICA, 14); // Retour à la police normale
                    contentStream.showText(event1.getDate_fin().toString());
                    contentStream.newLine();
                    contentStream.newLine();
                    contentStream.endText();
                }
            }

            // Spécifiez le chemin du bureau pour enregistrer le fichier PDF
            String desktopPath = System.getProperty("user.home") + "/Desktop/";
            File file = new File(desktopPath + "ListeEvénements.pdf");

            // Sauvegarde du document PDF
            document.save(file);
            document.close();

            showAlert(Alert.AlertType.INFORMATION, "Téléchargement réussi", "La liste des événements a été téléchargée avec succès en format PDF sur votre bureau.");
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors du téléchargement du fichier PDF : " + e.getMessage());
        }
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
    public List<event> triEmail() throws SQLException {

        List<event> list1 = new ArrayList<>();
        List<event> list2 = servicesevent.afficher();

        list1 = list2.stream().sorted((o1, o2) -> o1.getNom_evenement().compareTo(o2.getNom_evenement())).collect(Collectors.toList());
        return list1;

    }
    @FXML
    private void Trie() throws SQLException {
        servicesevent serviceevent =new servicesevent();
        event event = new event();
        List<event> events = triEmail();
        ObservableList<event> observableCoursList = FXCollections.observableArrayList(events);

        col_nom.setCellValueFactory(new PropertyValueFactory<>("nom_evenement"));
        col_date.setCellValueFactory(new PropertyValueFactory<>("date_debut"));
        col_datefin.setCellValueFactory(new PropertyValueFactory<>("date_fin"));

        tv_event.setItems(observableCoursList);

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
