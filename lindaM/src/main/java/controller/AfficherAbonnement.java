package controller;

import java.io.IOException;
import java.sql.SQLException;

import entities.Abonnement;
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
import services.AbonnementService;

public class AfficherAbonnement {
    AbonnementService serviceabonnement= new AbonnementService();
    private Abonnement selectedAbonnement;

    @FXML
    private Button btnModifier;

    @FXML
    private Button btnSupprimer;
    @FXML
    private TableColumn<Abonnement, Integer> id_abonnement;

    @FXML
    private TableColumn<Abonnement, String> mail;

    @FXML
    private TableColumn<Abonnement, String> nom;

    @FXML
    private TableColumn<Abonnement, Integer> num;


    @FXML
    private TableColumn<Abonnement, String> prenom;

    @FXML
    private TableView<Abonnement> tv_abonnement;
    @FXML
    void initialize() {
        try {

            btnModifier.setDisable(true);
            btnSupprimer.setDisable(true);
            ObservableList<Abonnement>abonnement = FXCollections.observableList(serviceabonnement.readAll());

            tv_abonnement.setItems(abonnement);
            id_abonnement.setCellValueFactory(new PropertyValueFactory<>("id_abonnement"));
            nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
            prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
            mail.setCellValueFactory(new PropertyValueFactory<>("mail"));
            num.setCellValueFactory(new PropertyValueFactory<>("num"));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public void Retour()
    {
        //Stage stage = (Stage) btnRetour.getScene().getWindow();
        //SceneChanger.changerScene("/Ajouterabonnement.fxml", stage);
    }
    @FXML
    public void update(MouseEvent event) {
        // Votre logique de mise à jour ici
    }


    public void mouseClicked(MouseEvent mouseEvent) {
        selectedAbonnement  = tv_abonnement.getSelectionModel().getSelectedItem();
        btnModifier.setDisable(false);
        btnSupprimer.setDisable(false);
    }
    @FXML
    public void Modifier() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierAbonnement.fxml"));
        Parent root = loader.load();
        // Obtenez la référence au contrôleur QuizzUpdate
        ModifierAbonnement controller = loader.getController();



        // Assurez-vous que l'objet Quizz n'est pas nul avant d'appeler initData
        if (selectedAbonnement != null) {
            // Appelez initData avec l'ID du Quizz sélectionné
            System.out.println(selectedAbonnement.getId_abonnement());
            controller.initData(selectedAbonnement.getId_abonnement());

            // Affichez la nouvelle scène
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
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
            Abonnement abonnementSelectionne = tv_abonnement.getSelectionModel().getSelectedItem();

            if (abonnementSelectionne != null) {
                AbonnementService ps = new AbonnementService();
                ps.delete(abonnementSelectionne);
                initialize();
            } else {
                // Afficher une alerte si aucun cours n'est sélectionné
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Aucune sélecton");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez sélectionner un abonnement  à supprimer.");
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }





}
