package Controller;

import com.mysql.cj.xdevapi.Statement;
import entities.Equipement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.EquipementService;
import utils.MyDatabase;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class modifierEquipement implements Initializable {

    private int idCu;
    @FXML
    private Button btnModifier;

    @FXML
    private TextField nomf;

    @FXML
    private TextField statutf;

    @FXML
    private TextField typef;


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        LoadData();
    }


    public void LoadData() {

        EquipementService us = new EquipementService();
        try {
            Equipement u = us.findById(this.idCu);
            if (u != null) {
                if (u.getNom() != null) {
                    nomf.setText(u.getNom());
                }
                if (u.getStatut() != null) {
                    statutf.setText(u.getStatut());
                }

                if (u.getType() != null) {
                    typef.setText(u.getType());
                }





            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public int initData(int id_equipement) {
        this.idCu = id_equipement;
        this.LoadData();
        System.err.println("ena aaaaaa" + this.idCu);
        return this.idCu;

    }

    @FXML
    private void updateData(ActionEvent event) {
        // Check if any field is empty
        if (nomf.getText().isEmpty() ||
                statutf.getText().isEmpty() ||
                typef.getText().isEmpty()) {


            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all fields!");
            alert.show();
            return;
        }

        try {
            // Fetch user data from the database
            EquipementService us = new EquipementService();
            Equipement equipementToUpdate = us.findById(idCu);

            // Update user data
            equipementToUpdate.setNom(nomf.getText());
          equipementToUpdate.setStatut(statutf.getText());
            equipementToUpdate.setStatut(typef.getText());

            // Call the modifier method to update the user
            us.update(equipementToUpdate);

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