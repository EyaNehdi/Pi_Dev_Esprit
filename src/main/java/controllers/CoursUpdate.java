package controllers;

import entities.Cours;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.CoursService;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CoursUpdate implements Initializable {

    private int idCu;
    @FXML
    private TextArea contenuf;

    @FXML
    private TextField titref;
    @FXML
    private Button btnRetour;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        LoadData();
    }


    public void LoadData() {

        CoursService us = new CoursService();
        try {
            Cours u = us.findById(this.idCu);
            if (u != null) {
                if (u.getTitre() != null) {
                    titref.setText(u.getTitre());
                }
                if (u.getContenu() != null) {
                    contenuf.setText(u.getContenu());
                }





            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public int initData(int id_cours) {
        this.idCu = id_cours;
        this.LoadData();
        System.err.println("ena aaaaaa" + this.idCu);
        return this.idCu;

    }
    @FXML
    private void updateData(ActionEvent event) {
        // Check if any field is empty
        if (titref.getText().isEmpty() ||
                contenuf.getText().isEmpty() ) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all fields!");
            alert.show();
            return;
        }

        try {
            // Fetch user data from the database
            CoursService us = new CoursService();
            Cours coursToUpdate = us.findById(idCu);

            // Update user data
            coursToUpdate.setTitre(titref.getText());
            coursToUpdate.setContenu(contenuf.getText());

            // Call the modifier method to update the user
            us.update(coursToUpdate);

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

    public void Retour()
    {
        Stage stage = (Stage) btnRetour.getScene().getWindow();
        SceneChanger.changerScene("/AfficherCours.fxml", stage);
    }


}
