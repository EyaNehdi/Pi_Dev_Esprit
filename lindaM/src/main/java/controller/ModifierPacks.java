package controller;

import entities.Packs;
import entities.Packs;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import services.PacksService;
import services.PacksService;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ModifierPacks implements Initializable {

    @FXML
    private AnchorPane modifier;

    private int idCu;
    @FXML
    private TextField nomf;
    @FXML
    private TextField descriptionf;

    @FXML
    private TextField prixf;

    @FXML
    private TextField typef;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        LoadData();
    }

    public void LoadData() {
        PacksService us = new PacksService();
        Packs u = us.findById(this.idCu);
        if (u != null) {
            if (u.getNom() != null) {
                nomf.setText(u.getNom());
            }
            if (u.getDescription() != null) {
                descriptionf.setText(u.getDescription());
            }
            if (u.getPrix() != 0) {
                prixf.setText(String.valueOf(u.getPrix())); // Conversion de float en String
            }
            if (u.getType() != null) {
                typef.setText(String.valueOf(u.getType()));
            }
        }
    }

    public int initData(int id_pack) {
        this.idCu = id_pack;
        System.out.println(id_pack);
        this.LoadData();
        System.err.println("ena aaaaaa" + this.idCu);
        return this.idCu;
    }

    @FXML
    private void ModifierPacks(ActionEvent event) {
        // Check if any field is empty
        if (nomf.getText().isEmpty() ||
                descriptionf.getText().isEmpty() ||
                prixf.getText().isEmpty() ||
                typef.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all fields!");
            alert.show();
            return;
        }

        // Fetch user data from the database
        PacksService us = new PacksService();
        Packs packsToUpdate = us.findById(idCu); // Correction : packsToUpdate

        // Update user data
        packsToUpdate.setNom(nomf.getText());
        packsToUpdate.setDescription(descriptionf.getText());
        packsToUpdate.setPrix(Float.parseFloat(prixf.getText())); // Conversion de String en float
        packsToUpdate.setType(typef.getText());

        // Call the modifier method to update the user
        us.update(packsToUpdate);

        // Show success message
        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
        successAlert.setHeaderText(null);
        successAlert.setContentText("Modification réussie !!");
        successAlert.show();
    }

}
