package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class DashboardController {
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
    private Button btnCours;

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
    private Button btnEvent;
    @FXML
    private Button btnEquipement;

    @FXML
    private Button btnAbonnement;
    @FXML
    private Button btnFormation;
    public void Voir_Cours() {
        Stage stage = (Stage) btnCours.getScene().getWindow();
        SceneChanger.changerScene("/AjouterCours.fxml", stage);
    }
    public void Voir_Event() {
        Stage stage = (Stage) btnEvent.getScene().getWindow();
        SceneChanger.changerScene("/gerer_evenement.fxml", stage);
    }
    public void Voir_Equipement() {
        Stage stage = (Stage) btnEquipement.getScene().getWindow();
        SceneChanger.changerScene("/ajouterEquipement.fxml", stage);
    }
    public void Voir_Abonnement() {
        Stage stage = (Stage) btnAbonnement.getScene().getWindow();
        SceneChanger.changerScene("/ajouterAbonnement.fxml", stage);
    }
    public void Voir_Formation() {
        Stage stage = (Stage) btnFormation.getScene().getWindow();
        SceneChanger.changerScene("/ajoutformation.fxml", stage);
    }
}
