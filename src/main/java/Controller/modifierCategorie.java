package Controller;

import com.mysql.cj.xdevapi.Statement;
import entities.Categorie;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.CategorieService;
import utils.MyDatabase;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class modifierCategorie implements Initializable {

    private int idCu;
    @FXML
    private Button btnModifier;

    @FXML
    private TextField descriptionf;

    @FXML
    private TextField numSerief;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        LoadData();
    }


    public void LoadData() {

        CategorieService us = new CategorieService();
        try {
            Categorie u = us.findById(this.idCu);
            if (u != null) {
                if (
                        u.getNumSerie() != 0) {
                    numSerief.setText(Integer.toString(u.getNumSerie()));
                }

                if (u.getDescription() != null) {
                    descriptionf.setText(u.getDescription());
                }





            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public int initData(int id_categorie) {
        this.idCu = id_categorie;
        this.LoadData();
        System.err.println("ena aaaaaa" + this.idCu);
        return this.idCu;

    }

    @FXML
    private void updateData(ActionEvent event) {
        // Check if any field is empty
        if (numSerief.getText().isEmpty() ||
                descriptionf.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all fields!");
            alert.show();
            return;
        }

        try {
            // Fetch user data from the database
            CategorieService us = new CategorieService();
            Categorie categorieToUpdate = us.findById(idCu);

            // Update user data
            categorieToUpdate.setNumSerie(Integer.parseInt(numSerief.getText()));
            categorieToUpdate.setDescription(descriptionf.getText());

            // Call the modifier method to update the user
            us.update(categorieToUpdate);

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