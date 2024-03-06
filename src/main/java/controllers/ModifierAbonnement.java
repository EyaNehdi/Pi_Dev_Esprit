package controllers;

import entities.Abonnement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import services.AbonnementService;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ModifierAbonnement implements Initializable {

    @FXML
    private AnchorPane modifier;

    private int idCu;
    @FXML
    private TextField nomf;
    @FXML
    private TextField prenomf;

    @FXML
    private TextField mailf;

    @FXML
    private TextField numf;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        LoadData();
    }

    public void LoadData() {
        AbonnementService us = new AbonnementService();
        try {
            Abonnement u = us.findById(this.idCu);
            if (u != null) {
                if (u.getNom() != null) {
                    nomf.setText(u.getNom());
                }
                if (u.getPrenom() != null) {
                    prenomf.setText(u.getPrenom());
                }
                if (u.getMail() != null) {
                    mailf.setText(u.getMail());
                }
                if (u.getNum() != null) {
                    numf.setText(String.valueOf(u.getNum()));
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public int initData(int id_abonnement) {
        this.idCu = id_abonnement;
        System.out.println(id_abonnement);
        this.LoadData();
        System.err.println("ena aaaaaa" + this.idCu);
        return this.idCu;
    }

    @FXML
    private void ModifierAbonnement(ActionEvent event) {
        // Check if any field is empty
        if (nomf.getText().isEmpty() ||
                prenomf.getText().isEmpty() ||
        mailf.getText().isEmpty() ||
        numf.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all fields!");
            alert.show();
            return;
        }

        try {
            // Fetch user data from the database
            AbonnementService us = new AbonnementService();
            Abonnement abonnementToUpdate = us.findById(idCu);

            // Update user data
            abonnementToUpdate.setNom(nomf.getText());
            abonnementToUpdate.setPrenom(prenomf.getText());
            abonnementToUpdate.setMail(mailf.getText());
            abonnementToUpdate.setNum(Integer.parseInt(numf.getText()));

            // Call the modifier method to update the user
            us.update(abonnementToUpdate);

            // Show success message
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setHeaderText(null);
            successAlert.setContentText("Modification réussie !!");
            successAlert.show();
        } catch (SQLException ex) {
            ex.printStackTrace(); // Handle exception appropriately
            // Show error message
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("An error occurred while updating user data!");
            errorAlert.show();
        }
    }

}

