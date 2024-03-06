package     Controller;

import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;

import entities.Categorie;
import entities.Equipement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import services.CategorieService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import Controller.afficherCategorie;

public class afficherCategorie {
        CategorieService serviceCategorie = new CategorieService();
        @FXML
        private TableColumn<Categorie, String> description;

        @FXML
        private TableColumn<Categorie, Integer> numSerie;
        @FXML
        private TableColumn<Categorie, Integer> numSerie2;
        @FXML
        private TableView<Categorie> tv_categorie;
        @FXML
        private Button btnModifier;

        @FXML
        private Button btnRetour;

        @FXML
        private Button btnSupprimer;
        @FXML
        private Button btnExport;
        private FilteredList<Categorie> filteredCategorie;
        @FXML
        void initialize() {
                try {
                        btnModifier.setDisable(true);
                        btnSupprimer.setDisable(true);
                        ObservableList<Categorie> categorie = FXCollections.observableList(serviceCategorie.readAll());
                        tv_categorie.setItems(categorie);
                        numSerie2.setCellValueFactory(new PropertyValueFactory<>("id_categorie"));
                        numSerie.setCellValueFactory(new PropertyValueFactory<>("numSerie"));
                        description.setCellValueFactory(new PropertyValueFactory<>("description"));
                } catch (SQLException e) {
                        System.out.println(e.getMessage());
                }
        }




        @FXML
        public void mouseClicked (MouseEvent mouseEvent) {
                Categorie categorie = tv_categorie.getSelectionModel().getSelectedItem();
                if (categorie != null) {
                        btnModifier.setDisable(false);
                        btnSupprimer.setDisable(false);
                }
        }
        @FXML
        public void Modifier() throws IOException {
                Categorie categorieSelectionne = tv_categorie.getSelectionModel().getSelectedItem();
                if (categorieSelectionne != null) {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/modifierCategorie.fxml"));
                        Parent root = loader.load();
                        modifierCategorie controller = loader.getController();
                        controller.initData(categorieSelectionne.getId_categorie()); // Utilisation de l'instance spécifique de Categorie
                        Stage stage = new Stage();
                        stage.setScene(new Scene(root));
                        stage.show();
                }
        }
        @FXML
        public void delete(ActionEvent event) {
                try {
                        System.out.println("Début de la méthode delete");

                        Categorie categorieSelectionne = tv_categorie.getSelectionModel().getSelectedItem();

                        if (categorieSelectionne != null) {
                                System.out.println("Catégorie sélectionnée : " + categorieSelectionne.toString());

                                serviceCategorie.delete(categorieSelectionne);
                                System.out.println("Catégorie supprimée de la base de données");
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("DONE");
                                alert.setContentText("Delete done");
                                alert.showAndWait();

                                // Mettre à jour la liste observable
                                ObservableList<Categorie> categories = FXCollections.observableList(serviceCategorie.readAll());
                                tv_categorie.setItems(categories);
                                System.out.println("Liste des catégories mise à jour");

                                // Rafraîchir la TableView
                                tv_categorie.refresh();
                                System.out.println("TableView rafraîchie");

                        } else {
                                // Afficher une alerte si aucun équipement n'est sélectionné
                                Alert alert = new Alert(Alert.AlertType.WARNING);
                                alert.setTitle("Aucune sélection");
                                alert.setHeaderText(null);
                                alert.setContentText("Veuillez sélectionner une catégorie à supprimer.");
                                alert.showAndWait();
                                System.out.println("Aucune catégorie sélectionnée");

                        }

                        System.out.println("Fin de la méthode delete");

                } catch (Exception e) {
                        e.printStackTrace();
                }
        }



        public void Retour()
        {
                Stage stage = (Stage) btnRetour.getScene().getWindow();
                SceneChanger.changerScene("/ajouterCategorie.fxml", stage);
        }

        @FXML
        private void ExportExcel(ActionEvent event) {
                try {
                        Workbook workbook = new XSSFWorkbook();
                        Sheet sheet = workbook.createSheet("Liste des categories");

                        // En-tête
                        Row headerRow = sheet.createRow(0);
                        headerRow.createCell(0).setCellValue("numSerie");
                        headerRow.createCell(1).setCellValue("description");


                        // Données
                        ObservableList<Categorie> categorieList = FXCollections.observableList(serviceCategorie.readAll());
                        for (int i = 0; i < categorieList.size(); i++) {
                                Row row = sheet.createRow(i + 1);
                                row.createCell(0).setCellValue(categorieList.get(i).getNumSerie());
                                row.createCell(1).setCellValue(categorieList.get(i).getDescription());

                        }

                        // Sauvegarde du fichier
                        String fileName = "liste_categorie.xlsx";
                        try (FileOutputStream fileOut = new FileOutputStream(fileName)) {
                                workbook.write(fileOut);
                                fileOut.flush();
                        }

                        System.out.println("Export Excel réussi.");
                } catch (IOException | SQLException e) {
                        e.printStackTrace();
                }
        }

}