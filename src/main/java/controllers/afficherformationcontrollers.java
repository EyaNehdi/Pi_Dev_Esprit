package controllers;

import entities.formation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import services.servicesformation;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Pagination;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

// ...


public class afficherformationcontrollers {

    servicesformation Serviceformation = new servicesformation();

    @FXML
    private Button tf_sup;

    @FXML
    private TableColumn<formation, String> col_nom;

    @FXML
    private TableColumn<formation, String> col_date;

    @FXML
    private TableView<formation> tv_formation;

    @FXML
    private Button tf_mod;
    @FXML
    private TextField fxrecherche;
    @FXML
    private Button pdf;
    @FXML
    private Button excel;

    @FXML
    void Excel(ActionEvent event) {
        try {

            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Formations");

            // Création de l'en-tête
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Nom");
            headerRow.createCell(1).setCellValue("Date");

            // Ajout des données de la TableView
            ObservableList<formation> formations = tv_formation.getItems();
            int rowNum = 1;
            for (formation formation : formations) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(formation.getNom());
                row.createCell(1).setCellValue(formation.getDate());
            }


            String desktopPath = System.getProperty("user.home") + "/Desktop/";
            File file = new File(desktopPath + "ListeFormations.xlsx");
            try (FileOutputStream outputStream = new FileOutputStream(file)) {
                workbook.write(outputStream);
            }

            showAlert(Alert.AlertType.INFORMATION, "Exportation Excel réussie", "La liste des formations a été exportée avec succès en format Excel sur votre bureau.");
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de l'exportation du fichier Excel : " + e.getMessage());
        }
    }

    @FXML
    void chercher(ActionEvent event) {
        String rechercheText = fxrecherche.getText().trim().toLowerCase();

        try {
            ObservableList<formation> formations = FXCollections.observableArrayList(Serviceformation.afficher());


            if (rechercheText.isEmpty()) {

                tv_formation.setItems(formations);
            } else {

                ObservableList<formation> filteredFormations = formations.filtered(f ->
                        f.getNom().toLowerCase().contains(rechercheText) || f.getDate().toLowerCase().contains(rechercheText)
                );


                tv_formation.setItems(filteredFormations);
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la recherche de formations : " + e.getMessage());
        }
    }



    @FXML
    void modifier(ActionEvent event) {
        // Récupérer la formation sélectionnée
        formation selectedFormation = tv_formation.getSelectionModel().getSelectedItem();

        if (selectedFormation != null) {
            // Afficher la fenêtre de dialogue pour la modification
            boolean modificationConfirme = showModificationDialog(selectedFormation);

            if (modificationConfirme) {
                try {
                    // Mettre à jour la formation dans la base de données
                    Serviceformation.modifier(selectedFormation);

                    // Rafraîchir la TableView
                    //ObservableList<formation> formations = DataHolder.getFormationsData();
                    ObservableList<formation> formations = FXCollections.observableList(Serviceformation.afficher());
                    tv_formation.setItems(formations);
                } catch (SQLException e) {
                    showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la modification de la formation : " + e.getMessage());
                }
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Aucune Formation Sélectionnée", "Veuillez sélectionner une formation à modifier.");
        }
    }

    private boolean showModificationDialog(formation selectedFormation) {
        // Afficher une fenêtre de dialogue pour la modification
        Dialog<Boolean> dialog = new Dialog<>();
        dialog.setTitle("Modifier la Formation");
        dialog.setHeaderText(null);

        // Configuration des boutons
        ButtonType modifierButtonType = new ButtonType("Modifier", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(modifierButtonType, ButtonType.CANCEL);

        // Création des champs de saisie
        TextField tf_nom = new TextField(selectedFormation.getNom());
        DatePicker dp_date = new DatePicker(LocalDate.parse(selectedFormation.getDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(new Label("Nom:"), 0, 0);
        grid.add(tf_nom, 1, 0);
        grid.add(new Label("Date:"), 0, 1);
        grid.add(dp_date, 1, 1);

        dialog.getDialogPane().setContent(grid);

        // Activation du bouton de modification lorsque le nom est non vide
        // et ne contient que des lettres alphabétiques, et la date a le format correct
        Node modifierButton = dialog.getDialogPane().lookupButton(modifierButtonType);
        modifierButton.setDisable(true);

        // Ajout d'un écouteur pour activer le bouton lors de la saisie
        tf_nom.textProperty().addListener((observable, oldValue, newValue) -> {
            boolean isNameValid = newValue.matches("[a-zA-Z]+"); // Vérification des lettres alphabétiques
            boolean isDateValid = isValidDate(dp_date.getEditor().getText());
            modifierButton.setDisable(newValue.trim().isEmpty() || !isNameValid || !isDateValid);
        });

        // Ajout d'un écouteur pour activer le bouton lors de la sélection d'une date
        dp_date.valueProperty().addListener((observable, oldValue, newValue) -> {
            boolean isNameValid = tf_nom.getText().matches("[a-zA-Z]+"); // Vérification des lettres alphabétiques
            boolean isDateValid = newValue != null && isValidDate(dp_date.getEditor().getText());
            modifierButton.setDisable(!isNameValid || !isDateValid);
        });

        // Résultat de la fenêtre de dialogue
        dialog.setResultConverter(dialogButton -> dialogButton == modifierButtonType);

        Optional<Boolean> result = dialog.showAndWait();
        if (result.isPresent() && result.get()) {
            // Mettre à jour les propriétés de la formation
            selectedFormation.setNom(tf_nom.getText());
            selectedFormation.setDate(dp_date.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            return true;
        } else {
            return false;
        }
    }

    // Vérifier si la date a le format correct "dd/MM/yyyy"
    private boolean isValidDate(String date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false);
            Date parsedDate = sdf.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    @FXML

    void initialize() {
        try {
            // Load formations from the service
            ObservableList<formation> formations = FXCollections.observableList(Serviceformation.afficher());

            // Set the items for TableView
            tv_formation.setItems(formations);

            // Define cell value factories for columns
            col_nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
            col_date.setCellValueFactory(new PropertyValueFactory<>("date"));

            // Enable sorting for TableView columns
            col_nom.setSortType(TableColumn.SortType.ASCENDING);
            col_date.setSortType(TableColumn.SortType.ASCENDING);
            tv_formation.getSortOrder().add(col_nom);
            tv_formation.getSortOrder().add(col_date);

            // Handle sorting order change
            tv_formation.setOnSort(event -> {
                TableColumn.SortType sortType = tv_formation.getSortOrder().get(0).getSortType();
                if (sortType.equals(TableColumn.SortType.ASCENDING)) {
                    // Handle ASCENDING sorting
                    formations.sort(Comparator.comparing(formation::getNom).thenComparing(formation::getDate));
                } else {
                    // Handle DESCENDING sorting
                    formations.sort(Comparator.comparing(formation::getNom).thenComparing(formation::getDate).reversed());
                }
            });

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void supprimer(ActionEvent event) {
        formation selectedFormation = tv_formation.getSelectionModel().getSelectedItem();

        if (selectedFormation != null) {
            try {
                Serviceformation.supprimer(selectedFormation.getId());
                tv_formation.getItems().remove(selectedFormation);
                showAlert(Alert.AlertType.INFORMATION, "Formation Supprimée", "La formation a été supprimée avec succès.");
            } catch (SQLException e) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la suppression de la formation : " + e.getMessage());
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Aucune Formation Sélectionnée", "Veuillez sélectionner une formation à supprimer.");
        }
    }

    // Helper method to show an alert
    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void retournerAjoutFormation(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ajoutformation.fxml"));
            tv_formation.getScene().setRoot(root);  // Assurez-vous d'utiliser le même élément de la scène que celui que vous avez utilisé pour charger la page "Ajouter Formation".
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void telecharger(ActionEvent event) {
        try {
            // Création d'un nouveau document PDF
            PDDocument document = new PDDocument();

            // Création d'une nouvelle page
            PDPage page = new PDPage();
            document.addPage(page);

            // Configuration du contenu du PDF
            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);

            // Titre du document
            contentStream.beginText();
            contentStream.newLineAtOffset(100, 750);
            contentStream.showText("Liste des Formations");
            contentStream.endText();

            // Ligne de séparation
            contentStream.moveTo(100, 745);
            contentStream.lineTo(page.getMediaBox().getWidth() - 100, 745);
            contentStream.stroke();

            // Configuration pour le contenu de la TableView
            contentStream.setFont(PDType1Font.HELVETICA, 12);
            int yPosition = 700; // Ajustez la position selon vos besoins

            // Ajout des données de la TableView
            ObservableList<formation> formations = tv_formation.getItems();
            for (formation formation : formations) {
                yPosition -= 20; // Ajustez l'espacement entre les lignes selon vos besoins
                contentStream.beginText();
                contentStream.newLineAtOffset(100, yPosition);
                contentStream.showText("Nom: " + formation.getNom());
                contentStream.newLineAtOffset(200, 0);
                contentStream.showText("Date: " + formation.getDate());
                contentStream.endText();
            }

            contentStream.close();

            // Spécifiez le chemin du bureau pour enregistrer le fichier PDF
            String desktopPath = System.getProperty("user.home") + "/Desktop/";
            File file = new File(desktopPath + "ListeFormations.pdf");

            // Sauvegarde du document PDF
            document.save(file);
            document.close();

            showAlert(Alert.AlertType.INFORMATION, "Téléchargement réussi", "La liste des formations a été téléchargée avec succès en format PDF sur votre bureau.");
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors du téléchargement du fichier PDF : " + e.getMessage());
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


    public List<formation> triEmail() throws SQLException {
        List<formation> list1 = new ArrayList<>();
        List<formation> list2 = Serviceformation.afficher();

        list1 = list2.stream().sorted(Comparator.comparing(formation::getNom)).collect(Collectors.toList());
        return list1;
    }

    @FXML
    private void trie() throws SQLException {
        // Assuming "nom" and "date" are TableColumn instances in your FXML file
        col_nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        col_date.setCellValueFactory(new PropertyValueFactory<>("date"));

        List<formation> formations = triEmail();
        ObservableList<formation> observableFormationsList = FXCollections.observableArrayList(formations);

        tv_formation.setItems(observableFormationsList);
    }
}
