package controller;


import Entity.User;
import Services.UserService;
import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
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

import java.io.IOException;

public class dashboard  {
    @FXML
    private Button user;
    public void admin(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminDashboard.fxml"));
            Parent adminRoot = loader.load();
            Scene adminScene = new Scene(adminRoot);

// Create a new stage for the Admin Dashboard
            Stage adminStage = new Stage();
            adminStage.setScene(adminScene);
            adminStage.initStyle(StageStyle.UNDECORATED); // Set style before showing the stage

// Close the current (login) stage
            Stage loginStage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            loginStage.close();

// Show the Admin Dashboard stage
            adminStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
