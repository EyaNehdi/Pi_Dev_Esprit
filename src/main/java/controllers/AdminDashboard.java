package controllers;

import entities.User;
import services.UserService;
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

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.stage.StageStyle;

public class AdminDashboard implements Initializable {


    @FXML
    private TableView<User> table;
    ObservableList<User> obslistus = FXCollections.observableArrayList();

    @FXML
    private TableColumn<User, String> e;

    @FXML
    private TableColumn<User, String> m;

    @FXML
    private TableColumn<User, String> n;

    @FXML
    private TableColumn<User, String> num;

    @FXML
    private TableColumn<User, String> p;
    @FXML
    private TextField search;
    @FXML
    private Button delete;
    @FXML
    private Button modifier;


    @FXML
    private TableColumn<User, String> r;

    public void initialize(URL url, ResourceBundle rb) {
        try {
            listUsers();
            // Associate the observable list with the TableView

        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log the exception
        }
    }

    public void listUsers() throws SQLException {
        UserService UserS = new UserService();
        UserS.afficher().forEach(p -> obslistus.add(p));

        // Set cell value factories for each TableColumn
        n.setCellValueFactory(new PropertyValueFactory<>("Nom"));
        p.setCellValueFactory(new PropertyValueFactory<>("Prenom"));
        e.setCellValueFactory(new PropertyValueFactory<>("Email"));
        m.setCellValueFactory(new PropertyValueFactory<>("Mdp"));
        num.setCellValueFactory(new PropertyValueFactory<>("Tel"));
        table.setItems(obslistus);
        search.textProperty().addListener((obs, oldText, newText) -> {
            List<User> ae = null;
            try {
                ae = UserS.Search(newText);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            table.getItems().setAll(ae);
        });
    }


    private final UserService us = new UserService();





    @FXML
    public void delete(ActionEvent event) {
        try {
            User userSelectionne = table.getSelectionModel().getSelectedItem();

            if (userSelectionne != null) {
                UserService ps = new UserService();
                ps.supprimer(userSelectionne);

                // Update the ObservableList directly to refresh the TableView
                obslistus.remove(userSelectionne);
            } else {
                // Afficher une alerte si aucun utilisateur n'est sélectionné
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Aucune sélection");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez sélectionner un utilisateur à supprimer.");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            // Handle SQLException specifically
            e.printStackTrace(); // Log the exception or handle it based on your application's needs
        } catch (NullPointerException e) {
            // Handle NullPointerException if getSelectedItem() returns null
            e.printStackTrace(); // Log the exception or handle it based on your application's needs
        } catch (Exception e) {
            // Catch any other unexpected exceptions
            e.printStackTrace(); // Log the exception or handle it based on your application's needs
        }

    }

    public void modifieruser(ActionEvent event) {
        UserService userService = new UserService();
        String searchTerm = search.getText();

        List<User> usersToUpdate = null;
        try {
            usersToUpdate = userService.Search(searchTerm);
        } catch (SQLException e) {
            // Handle the exception appropriately, e.g., log it or show an error message
            e.printStackTrace();
            return; // or throw a specific exception if needed
        }

        if (!usersToUpdate.isEmpty()) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierUser.fxml"));
            Parent root = null;
            try {
               root = loader.load();
                ModifierUser modifierlivreurController = loader.getController();

                // Assuming you want to pass the first user in the list (modify accordingly)
                User userToUpdate = usersToUpdate.get(0);
                modifierlivreurController.initData(userToUpdate);





                Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                // Handle the exception appropriately, e.g., log it or show an error message
                e.printStackTrace();
            }
        } else {
            System.out.println("Le livreur avec le nom spécifié n'existe pas.");
        }
    }


    public void acceuil(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dash.fxml"));
            Parent adminRoot = loader.load();
            Scene adminScene = new Scene(adminRoot);

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
    }}




