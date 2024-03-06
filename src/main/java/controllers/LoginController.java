package controllers;
import entities.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.stage.StageStyle;
import utils.MyDatabase;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.net.URL;
public class LoginController implements Initializable {
    @FXML
    private Hyperlink create;

    @FXML
    private TextField email;

    @FXML
    private Button login;

    @FXML
    private Label loginmess;

    @FXML
    private PasswordField pass;

    @FXML
    private Hyperlink reset;
    @FXML
    private Button cancel;
    Connection connection;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        File file=new File("Assets/logo.png");
    }
    public void loginonaction(ActionEvent event){
        if (email.getText().equals("houssemzalila@esprit.tn") && pass.getText().equals("houssem")) {
            // Admin login
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Travel Me :: Success Message");
            alert.setHeaderText(null);
            alert.setContentText("Bienvenue Admin");
            alert.showAndWait();

            // Close the current stage (login stage)
            Stage loginStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            loginStage.close();
try{
            //Load AdminDashboard.fxml on a new stage
            Parent root = FXMLLoader.load(getClass().getResource("/Admindashboard.fxml"));
            Scene scene = new Scene(root);
            Stage adminStage = new Stage();
            adminStage.setScene(scene);
            adminStage.initStyle(StageStyle.UNDECORATED);
            adminStage.show();
} catch (IOException e) {
    e.printStackTrace();
}
        /*} else {
            // Regular user login
            String query2 = "SELECT * FROM user WHERE email=? AND mdp=?";
            connection = DBConnection.getInstance().getConnection();
            try {
                PreparedStatement smt = connection.prepareStatement(query2);
                smt.setString(1, email.getText());
                smt.setString(2, pass.getText());
                ResultSet rs = smt.executeQuery();
                if (rs.next()) {
                    // User found, set current user and open UserCard.fxml
                    User currentUser = new User(
                            rs.getInt("id"),
                            rs.getString("fullName"),
                            rs.getString("email"),
                            rs.getString("tel"),
                            rs.getString("image")
                    );
                    User.setCurrent_User(currentUser);
                    SessionManager.getInstace(
                            rs.getInt("id"),
                            rs.getString("fullName"),
                            rs.getString("email"),
                            rs.getString("tel"),
                            rs.getString("image")
                    );
                    System.out.println(User.Current_User.getEmail());
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Travel Me :: Success Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Vous êtes connecté");
                    alert.showAndWait();

                    // Close the current stage (login stage)
                    Stage loginStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    loginStage.close();

                    // Load UserCard.fxml on a new stage
                    Parent root = FXMLLoader.load(getClass().getResource("/UserCard.fxml"));
                    Scene scene = new Scene(root);
                    Stage userStage = new Stage();
                    userStage.setScene(scene);
                    userStage.show();
                } else {
                    // No user found with provided credentials
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Travel Me :: Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Wrong Email/Password!");
                    alert.showAndWait();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
                // Handle any SQL exceptions here
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
                // Handle any IO exceptions related to FXML loading
            }
        }

       /* if(email.getText().isBlank()==false && pass.getText().isBlank()==false){ validatelogin();}
        */}else {
            loginmess.setText("invalid login.Please try again");
        }
    }
    public void cancelonaction(ActionEvent event){
        Stage stage=( Stage) cancel.getScene().getWindow();
        stage.close();
    }
    public void validatelogin(){

    }
    @FXML
    void changerpasse(ActionEvent event)throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mail.fxml"));
        Parent root = loader.load();

        // Create a new stage for the mail scene
        Stage mailStage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        mailStage.setScene(new Scene(root));
        mailStage.show();
    }
   @FXML
   private void create(ActionEvent event) throws IOException {
       FXMLLoader loader = new FXMLLoader(getClass().getResource("/signup.fxml"));
       Parent root = loader.load();

       Scene scene = new Scene(root);
       Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

       stage.setScene(scene);
       stage.show();
   }


}
