package Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class dashb {

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
        private Button btnEquipement;
    @FXML
    void Voir_Equipement(ActionEvent event) {
        Stage stage = (Stage) btnEquipement.getScene().getWindow();
        SceneChanger.changerScene("/AjouterEquipement.fxml", stage);

    }
    }



