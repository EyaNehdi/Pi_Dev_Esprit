package controllers;

import java.io.IOException;
import java.sql.SQLException;

import entities.User;
import services.UserService;
import javafx.fxml.FXMLLoader;
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

public class SignupController {

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
    @FXML
    void cancelonaction(ActionEvent event) {
        Stage stage=( Stage) cancel.getScene().getWindow();
        stage.close();
    }



    @FXML
    void initialize() {

        TextFormatter<String> phoneFormatter = new TextFormatter<>(change -> {
            String newText = change.getControlNewText();

            if (newText.matches("\\d*") && newText.length() <= 8) {
                return change;
            } else {
                fill.setText("Invalid phone number format!");  // Set the error message
                return null;  // Reject the change if it doesn't meet the criteria
            }
        });
       tel.setTextFormatter(phoneFormatter);
    }

    UserService ServUser= new UserService();

    @FXML
    private void add(ActionEvent event) throws SQLException {
        String Nom = nom.getText();
        String Prenom= prenom.getText();
        String Email = emaill.getText();
        String Mdp= mdpp.getText();
        String confirmPassword = mdp1.getText();
        String Tel = tel.getText();

        // Check if any field is empty
        if (Nom.isEmpty() ||Prenom.isEmpty() || Email.isEmpty() || Tel.isEmpty() || Mdp.isEmpty() || confirmPassword.isEmpty()) {
            fill.setText("Please fill in all the fields");
        } else if (!Email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {

            em.setText("Invalid email format!");


        }else if (ServUser.existemail(Email)) {

        em.setText("This email is already registered!");

     } else if (!Mdp.equals(confirmPassword)) {

        em1.setText("Passwords do not match!");
      em.setText("");
  /* }else if (!Tel.matches("\\d{8}")&& Tel.length() != 8) {
            fill.setText("Invalid phone number format!");
            em.setText("");
            em1.setText("");



      */ } else {
            try {
                User user = new User();
                user.setNom(Nom);
                user.setPrenom(Prenom);
                user.setEmail(Email);
                user.setMdp(Mdp);
                user.setTel(Tel);

                ServUser.ajouter(user);


                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));
                    Parent loginRoot = loader.load();
                    Scene loginScene = new Scene(loginRoot);
                    Stage loginStage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

                    // Switch to the Login scene
                    loginStage.setScene(loginScene);
                    loginStage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                StackPane welcomeRoot = new StackPane();
                welcomeRoot.setStyle("-fx-background-color: #f1ae31;");  // Set background color

                Label welcomeLabel = new Label("You have successfully signed up!");
                welcomeLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: white;");  // Customize label style

                welcomeRoot.getChildren().add(welcomeLabel);

                Scene welcomeScene = new Scene(welcomeRoot, 500, 200);

// Show the welcoming message scene in a new stage
                Stage welcomeStage = new Stage();
                welcomeStage.initStyle(StageStyle.UNDECORATED);  // Remove the border
                welcomeStage.setScene(welcomeScene);
                welcomeStage.show();

// Close the welcome stage after 3 seconds
                Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), events -> {
                    welcomeStage.close();

                    // Load the Login scene

                }));

                timeline.play();

            } catch (SQLException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
    }

}
