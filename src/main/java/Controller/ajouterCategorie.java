package Controller;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import entities.Categorie;
import entities.Equipement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import services.CategorieService;
import services.EquipementService;

public class ajouterCategorie {


    @FXML
    private Button btnAjouter ;

    @FXML
    private TextField descriptionf;

    @FXML
    private TextField numSerief;

    @FXML
    private void ajouterCategorie() {
        Categorie categorie = new Categorie();
        categorie.setNumSerie(Integer.parseInt(numSerief.getText()));
        categorie.setDescription(descriptionf.getText());

        CategorieService serviceCategorie= new CategorieService();

        try {
            serviceCategorie.add(categorie);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succes");
            alert.setContentText("Ajout de categorie avec succes");
            alert.showAndWait();
            // Changer de scène après l'ajout
            // Stage stage = (Stage) btnAjouter.getScene().getWindow();
            // SceneChanger.changerScene("/AfficherCours.fxml", stage);

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Echec");
            alert.setContentText("Echec d'ajout de categorie");
            alert.showAndWait();
            e.printStackTrace();
        }
    }

}