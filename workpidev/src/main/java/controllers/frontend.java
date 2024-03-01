package controllers;

import entities.formation;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.awt.event.ActionEvent;
import java.util.List;

public class frontend {

    @FXML
    private Label lb_Accueil;

    @FXML
    private ListView<formation> formationListView;

    public void setFormations(List<formation> formations) {
        formationListView.getItems().addAll(formations);
    }

    @FXML
    void Voir_Abonnements(ActionEvent event) {

    }

    @FXML
    void Voir_Cours(ActionEvent event) {

    }

    @FXML
    void Voir_Equipements(ActionEvent event) {

    }

    @FXML
    void Voir_Evenements(ActionEvent event) {

    }

    @FXML
    void Voir_Formations(ActionEvent event) {

    }

    public void Voir_Abonnements(javafx.event.ActionEvent actionEvent) {
    }

    public void Voir_Equipements(javafx.event.ActionEvent actionEvent) {
    }

    public void Voir_Formations(javafx.event.ActionEvent actionEvent) {
    }

    public void Voir_Evenements(javafx.event.ActionEvent actionEvent) {
    }

    public void Voir_Cours(javafx.event.ActionEvent actionEvent) {
    }
}
