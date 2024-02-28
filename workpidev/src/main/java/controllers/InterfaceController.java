package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
public class InterfaceController {

    @FXML
    private Pane Pn_abonnements;

    @FXML
    private Pane Pn_cours;

    @FXML
    private Pane Pn_equipements;

    @FXML
    private Pane Pn_evenements;

    @FXML
    private Pane Pn_formations;

    @FXML
    private Label lb_Accueil;

    @FXML
    private TextField tf_abonnements;

    @FXML
    private TextField tf_cours;

    @FXML
    private TextField tf_equipements;

    @FXML
    private TextField tf_evenements;

    @FXML
    private TextField tf_formations;

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
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/gerer_evenement.fxml"));
            Pn_equipements.getScene().setRoot(root);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    @FXML
    void Voir_Formations(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ajoutformation.fxml"));
            Pn_equipements.getScene().setRoot(root);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void initialize() {
        assert Pn_abonnements != null : "fx:id=\"Pn_abonnements\" was not injected: check your FXML file 'interface.fxml'.";
        assert Pn_cours != null : "fx:id=\"Pn_cours\" was not injected: check your FXML file 'interface.fxml'.";
        assert Pn_equipements != null : "fx:id=\"Pn_equipements\" was not injected: check your FXML file 'interface.fxml'.";
        assert Pn_evenements != null : "fx:id=\"Pn_evenements\" was not injected: check your FXML file 'interface.fxml'.";
        assert Pn_formations != null : "fx:id=\"Pn_formations\" was not injected: check your FXML file 'interface.fxml'.";
        assert lb_Accueil != null : "fx:id=\"lb_Accueil\" was not injected: check your FXML file 'interface.fxml'.";
        assert tf_abonnements != null : "fx:id=\"tf_abonnements\" was not injected: check your FXML file 'interface.fxml'.";
        assert tf_cours != null : "fx:id=\"tf_cours\" was not injected: check your FXML file 'interface.fxml'.";
        assert tf_equipements != null : "fx:id=\"tf_equipements\" was not injected: check your FXML file 'interface.fxml'.";
        assert tf_evenements != null : "fx:id=\"tf_evenements\" was not injected: check your FXML file 'interface.fxml'.";
        assert tf_formations != null : "fx:id=\"tf_formations\" was not injected: check your FXML file 'interface.fxml'.";

    }

}