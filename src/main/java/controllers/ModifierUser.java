package controllers;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import entities.User;
import services.UserService;
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
    private User userupdate;
    UserService us=new UserService();
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
    public void initData(User userupdate) {
        this.userupdate = userupdate;
        // Mettre à jour les champs de texte avec les informations du livreur
        nom.setText(userupdate.getNom());
        prenom.setText(userupdate.getPrenom());
        emaill.setText(userupdate.getEmail());
        mdpp.setText(userupdate.getMdp());
        tel.setText(userupdate.getTel());
    }
    @FXML
    void modifier(ActionEvent event) throws SQLException {
        if (userupdate == null) {
            userupdate = new User(); // assuming you have a no-argument constructor in your User class
        }
        userupdate.setNom(nom.getText());
        userupdate.setPrenom(prenom.getText());
        userupdate.setEmail(emaill.getText());
        userupdate.setMdp(mdpp.getText());

        userupdate.setTel(tel.getText());


        if (userupdate.getNom().isEmpty() ||userupdate.getPrenom().isEmpty() || userupdate.getEmail().isEmpty() || userupdate.getTel().isEmpty() || userupdate.getMdp().isEmpty()) {
            fill.setText("Please fill in all the fields");
        } else if (!userupdate.getEmail().matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {

            em.setText("Invalid email format!");


        }else if (us.existemail(userupdate.getEmail())) {

            em.setText("This email is already registered!");

        } else if (! userupdate.getMdp().equals(mdp1)) {

            em1.setText("Passwords do not match!");
            em.setText("");
   }

        try {

            us.modifier(userupdate.getNom(), userupdate.getPrenom(), userupdate.getEmail(),userupdate.getMdp(),userupdate.getTel(),userupdate.getId());
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminDashboard.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

            stage.setScene(scene);
            stage.show();
            // Fermez la fenêtre de modification ici si nécessaire
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}






