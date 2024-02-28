package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import entities.reservation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import services.servicesreservation;

public class AjouterreservationController {

    private services.servicesreservation servicesreservation = new servicesreservation();

    @FXML
    private TextField tf_nomeleve;

    @FXML
    private TextField tf_nomevent;

    @FXML
    void Afficher_reservation(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Afficherreservation.fxml"));
            tf_nomeleve.getScene().setRoot(root);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

  /*  @FXML
    void Ajouter_reservation(ActionEvent event)  {
        try {
            servicesreservation.ajouter(new reservation(tf_nomeleve.getText(),tf_nomevent.getText()));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("succes");
            alert.setContentText("reservation ajouter");
            alert.showAndWait();

        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }*/



}
