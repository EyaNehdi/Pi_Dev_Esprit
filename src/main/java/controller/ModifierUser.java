package controller;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import Entity.User;
import Services.UserService;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.control.Label;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.stage.StageStyle;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.StringConverter;
public class ModifierUser implements Initializable {
    @FXML
    private Button cancel;

    @FXML
    private TextField emaill;

    @FXML
    private PasswordField mdp1;

    @FXML
    private PasswordField mdpp;

    @FXML
    private TextField nom;

    @FXML
    private TextField prenom;

    @FXML
    private Button sign;

    @FXML
    private TextField tel;
    @FXML
    private Label fill;
    @FXML
    private Label em;
    @FXML
    private Label em1;
    private User userup;
    @FXML
    void cancelonaction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminDashboard.fxml"));
            Parent loginRoot = loader.load();
            Scene loginScene = new Scene(loginRoot);
            Stage loginStage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

            // Switch to the Login scene
            loginStage.setScene(loginScene);
            loginStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //loadData("");
    }
public void modifier(ActionEvent event){
    String updatedNom = nom.getText();
    String updatedPrenom = prenom.getText();
    String updatedEmail = emaill.getText();
    String updatedMdp = mdp1.getText();
    String updatedTel =tel.getText();
    // Validate if tel is a valid integer (you may want to improve validation)


    // Create a new User object with the updated information
    User updatedUser = new User(updatedNom, updatedPrenom, updatedEmail, updatedMdp, updatedTel);

    // Set the ID of the updated user (assuming you have a method to set the ID in your User class)


    // Call the modifier method to update the user in the database
    UserService.modifier(updatedUser);

    // Optionally, you can display a success message or navigate to another scene
    fill.setText("User Updated Successfully!");
}
}
  /*  public void loadData(String searchTerm) {
        UserService us = new UserService();
        try {
            List<User> userList = us.Search(searchTerm);

            if (!userList.isEmpty()) {
                // Assuming you want to work with the first user in the list
                User u = userList.get(0);

                // Populate your UI components with the user's information
                nom.setText(u.getNom());
               prenom.setText(u.getPrenom());
                emaill.setText(u.getEmail());
                mdpp.setText(u.getMdp());
                tel.setText(u.getTel());
                // Handle image-related code if needed
            } else {
                // Handle the case where no matching user is found
                fill.setText("no user found");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }*/




