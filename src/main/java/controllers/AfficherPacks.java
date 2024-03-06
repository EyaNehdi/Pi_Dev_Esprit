package controllers;

import java.io.IOException;
import java.sql.SQLException;

import entities.Abonnement;
import entities.Packs;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import services.PacksService;


public class AfficherPacks {
    PacksService servicepacks = new PacksService(); // Correction ici
    private Packs selectedPacks ;

    @FXML
    private Button btnModifier;

    @FXML
    private Button btnSupprimer;
    @FXML
    private TableColumn<Packs,Integer> id_packs;

    @FXML
    private TableColumn<Packs, String> nom;

    @FXML
    private TableColumn<Packs, String> description;

    @FXML
    private TableColumn<Packs, Float> prix;

    @FXML
    private TableColumn<Packs, String> type;

    @FXML
    private TableView<Packs> tv_packs;

    @FXML
    void initialize() throws SQLException {
        try {

            btnModifier.setDisable(true);
            btnSupprimer.setDisable(true);
            ObservableList<Packs> packs = FXCollections.observableList(servicepacks.readAll());

            tv_packs.setItems(packs);
            id_packs.setCellValueFactory(new PropertyValueFactory<>("id_packs"));
            nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
            description.setCellValueFactory(new PropertyValueFactory<>("description"));
            type.setCellValueFactory(new PropertyValueFactory<>("type"));
            prix.setCellValueFactory(new PropertyValueFactory<>("prix"));

            id_packs.setVisible(false);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void showRec() throws SQLException {

        ObservableList<Packs> packs = FXCollections.observableList(servicepacks.readAll());
        tv_packs.setItems(packs);
        id_packs.setCellValueFactory(new PropertyValueFactory<>("id_packs"));
        nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        prix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));

    }
    private void refresh() throws IOException{
        ObservableList<Packs> packs = null;
        try {
            packs = FXCollections.observableList(servicepacks.readAll());
            nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
            id_packs.setCellValueFactory(new PropertyValueFactory<>("id_packs"));
            description.setCellValueFactory(new PropertyValueFactory<>("description"));
            prix.setCellValueFactory(new PropertyValueFactory<>("prix"));
            type.setCellValueFactory(new PropertyValueFactory<>("type"));
            tv_packs.setItems(packs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



    }
    public void Retour() {
        // Stage stage = (Stage) btnRetour.getScene().getWindow();
        // SceneChanger.changerScene("/Ajouterabonnement.fxml", stage);
    }

    public void mouseClicked(MouseEvent mouseEvent) {
        selectedPacks  = tv_packs.getSelectionModel().getSelectedItem();
        btnModifier.setDisable(false);
        btnSupprimer.setDisable(false);
    }
    @FXML
    public void Ajouter() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterPacks.fxml"));
        Parent root = loader.load();
        AjouterPacks controller = loader.getController();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    public void Modifier() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierPacks.fxml"));
        Parent root = loader.load();
        controllers.ModifierPacks controller = loader.getController();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();

     if (selectedPacks!= null) {
        // Appelez initData avec l'ID du Quizz sélectionné
        System.out.println(selectedPacks.getId_Pack());
        controller.initData(selectedPacks.getId_Pack());


    } else {
        // Afficher une alerte si aucun Quizz n'est sélectionné
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Aucune sélection");
        alert.setHeaderText(null);
        alert.setContentText("Veuillez sélectionner un Quizz à modifier.");
        alert.showAndWait();
    }



}

    @FXML
    public void delete(ActionEvent event) {
        try {

            if (selectedPacks != null) {
                PacksService ps = new PacksService();
                ps.delete(selectedPacks);
                initialize();
            } else {
                // Afficher une alerte si aucun cours n'est sélectionné
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Aucune sélection");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez sélectionner un pack à supprimer.");
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


