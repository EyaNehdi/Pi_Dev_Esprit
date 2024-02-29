package Controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import entities.Categorie;
import entities.Equipement;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import services.EquipementService;
import services.EquipementService;

public class ajouterEquipement {


    @FXML
    private Button btnAjouter;

    @FXML
    private TextField nomf;

    @FXML
    private TextField typef;

    @FXML
    private TextField statutf;

    @FXML
    private void ajouterEquipement() {
        Equipement equipement = new Equipement();
        equipement.setNom(nomf.getText());
        equipement.setType(typef.getText());
        equipement.setStatut(statutf.getText());

        EquipementService serviceCategorie= new EquipementService();

        try {
            serviceCategorie.add(equipement);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succes");
            alert.setContentText("Ajout d'equipement avec succes");
            alert.showAndWait();
            // Changer de scène après l'ajout
            // Stage stage = (Stage) btnAjouter.getScene().getWindow();
            // SceneChanger.changerScene("/AfficherCours.fxml", stage);

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Echec");
            alert.setContentText("Echec d'ajout d'equipement");
            alert.showAndWait();
            e.printStackTrace();
        }
    }

}