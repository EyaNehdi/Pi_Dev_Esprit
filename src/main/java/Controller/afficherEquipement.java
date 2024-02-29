package Controller;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;

import entities.Equipement;
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

import Controller.ajouterEquipement;
import services.EquipementService;

public class afficherEquipement {
 EquipementService serviceEquipement = new EquipementService();
    private int idCu;
    @FXML
    private TableColumn<Equipement, String> nom;

    @FXML
    private TableColumn<Equipement, String> statut;

    @FXML
    private TableColumn<Equipement, String> type;

    @FXML
    private TableView<Equipement> tv_equipement;
    @FXML
    private Button btnModifier;

    @FXML
    private Button btnRetour;

    @FXML
    private Button btnSupprimer;
    @FXML
    void initialize() {
        try {
           // btnModifier.setDisable(true);
            //btnSupprimer.setDisable(true);
            ObservableList<Equipement> equipement = FXCollections.observableList(serviceEquipement.readAll());
            tv_equipement.setItems(equipement);
            //id_equipement.setCellValueFactory(new PropertyValueFactory<>("idEquipement")); // Correction ici
            nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
            statut.setCellValueFactory(new PropertyValueFactory<>("statut"));
            type.setCellValueFactory(new PropertyValueFactory<>("type"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    public void mouseClicked (MouseEvent mouseEvent) {
        Equipement equipement = tv_equipement.getSelectionModel().getSelectedItem();
        if (equipement != null) {
            nom.setText(equipement.getNom());
            statut.setText(equipement.getStatut());
            type.setText(equipement.getType());
            btnModifier.setDisable(false);
            btnSupprimer.setDisable(false);
        }
    }
    @FXML
    public void Modifier() throws IOException {
        Equipement equipementSelectionne = tv_equipement.getSelectionModel().getSelectedItem();
        if (equipementSelectionne != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/modifierEquipement.fxml"));
            Parent root = loader.load();
            modifierCategorie controller = loader.getController();
            controller.initData(equipementSelectionne.getId_equipement()); // Utilisation de l'instance spécifique de Categorie
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        }
    }
    @FXML
    public void delete(ActionEvent event) {
        try {
            Equipement equipementSelectionne = tv_equipement.getSelectionModel().getSelectedItem();

            if (equipementSelectionne != null) {
                EquipementService ps = new EquipementService();
                ps.delete(equipementSelectionne);
                initialize();
            } else {
                // Afficher une alerte si aucun cours n'est sélectionné
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Aucune sélection");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez sélectionner un equipement à supprimer.");
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }





}

