package controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.awt.event.ActionEvent;

public class frontend {

    @FXML
    private Label lb_Accueil;

    @FXML
    private TableView<String> t; // Assuming String as the type

    @FXML
    private TableColumn<String, String> n;

    @FXML
    private TableColumn<String, String> d;

    @FXML
    void Voir_Abonnements(ActionEvent event) {
        // Your code for this action
    }

    @FXML
    void Voir_Cours(ActionEvent event) {
        // Your code for this action
    }

    @FXML
    void Voir_Equipements(ActionEvent event) {
        // Your code for this action
    }

    @FXML
    void Voir_Evenements(ActionEvent event) {
        // Your code for this action
    }

    @FXML
    void Voir_Formations(ActionEvent event) {
        // Your code for this action
    }

    @FXML
    void initialize() {
        // Fill the columns with sample data
        n.setCellValueFactory(cellData -> new SimpleStringProperty( cellData.getValue()));
        d.setCellValueFactory(cellData -> new SimpleStringProperty( cellData.getValue()));

        t.getItems().addAll(
                "formationbi,01-01-2022",
                "sport,02-01-2022",
                "science,03-01-2022"
        );
    }

    public void Voir_Abonnements(javafx.event.ActionEvent actionEvent) {
    }

    public void Voir_Evenements(javafx.event.ActionEvent actionEvent) {
    }

    public void Voir_Formations(javafx.event.ActionEvent actionEvent) {
    }

    public void Voir_Equipements(javafx.event.ActionEvent actionEvent) {
    }

    public void Voir_Cours(javafx.event.ActionEvent actionEvent) {
    }
}
