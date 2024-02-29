package     Controller;

import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;

import entities.Categorie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import Controller.ajouterCategorie;

public class afficherCategorie {
        CategorieService serviceCategorie = new CategorieService();
        private int idCu;
        @FXML
        private TableColumn<Categorie, String> description;

        @FXML
        private TableColumn<Categorie, Integer> id_categorie;

        @FXML
        private TableColumn<Categorie, Integer> numSerie;

        @FXML
        private TableView<Categorie> tv_categorie;
        @FXML
        private Button btnModifier;

        @FXML
        private Button btnRetour;

        @FXML
        private Button btnSupprimer;
        @FXML
        void initialize() {
                try {
                       btnModifier.setDisable(true);
                       btnSupprimer.setDisable(true);
                        ObservableList<Categorie> categorie = FXCollections.observableList(serviceCategorie.readAll());
                        tv_categorie.setItems(categorie);
                       // id_categorie.setCellValueFactory(new PropertyValueFactory<>("id_categorie"));
                        numSerie.setCellValueFactory(new PropertyValueFactory<>("numSerie"));
                        description.setCellValueFactory(new PropertyValueFactory<>("description"));
                } catch (SQLException e) {
                        throw new RuntimeException(e);
                }

        }

        @FXML
        public void mouseClicked (MouseEvent mouseEvent) {
                Categorie categorie = tv_categorie.getSelectionModel().getSelectedItem();
                if (categorie != null) {

                        numSerie.setText(Integer.toString(categorie.getNumSerie())); // Conversion de int en String si nécessaire
                        description.setText(categorie.getDescription());
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
                        Categorie categorieSelectionne = tv_categorie.getSelectionModel().getSelectedItem();

                        if (categorieSelectionne != null) {
                                CategorieService ps = new CategorieService();
                                ps.delete(categorieSelectionne);
                                initialize();
                        } else {
                                // Afficher une alerte si aucun cours n'est sélectionné
                                Alert alert = new Alert(Alert.AlertType.WARNING);
                                alert.setTitle("Aucune sélection");
                                alert.setHeaderText(null);
                                alert.setContentText("Veuillez sélectionner une categorie à supprimer.");
                                alert.showAndWait();
                        }
                } catch (Exception e) {
                        e.printStackTrace();
                }
        }

        public void Retour()
        {
                Stage stage = (Stage) btnRetour.getScene().getWindow();
                SceneChanger.changerScene("/ajouterCategorie.fxml", stage);
        }



}