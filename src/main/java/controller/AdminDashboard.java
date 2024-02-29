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

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
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
                    table.getItems().setAll(ae);});}


    private final UserService us =new UserService();
    @FXML
   /* void supprission(ActionEvent event) {
       us.delete(Integer.parseInt(search.getText()));
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/AfficherUser.fxml"));
            search.getScene().setRoot(root);

        } catch (IOException e) {
            throw new RuntimeException(e);

        }
    }*/

    void supprimer(ActionEvent event) {
        try {
           UserService ls = new UserService();
            String nom = search.getText(); // Supposant que 'Nom' est le champ texte où le nom du livreur est saisi
            ls.supprimer(nom); // Supposant que ls est un service ou un objet d'accès aux données
            Alert alerteSucces = new Alert(Alert.AlertType.INFORMATION);
            alerteSucces.setTitle("Succès");
            alerteSucces.setContentText("Le livreur a été supprimé");
            alerteSucces.showAndWait();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

    }
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
public void modifier(ActionEvent event) {





}
       /* act.setCellFactory(column -> {


            return new TableCell<User, Void>() {
                private final Button deleteButton = new Button("Delete");
                private final Button getByIdButton = new Button("Get by ID");

                {
                   */

        }

           /* UserService UserS = new UserService();
            /*User u = new User();*/
            /*UserS.afficher().stream().forEach((p) -> {
                obslistus.add(p);
            });
            fullNameF.setCellValueFactory(new PropertyValueFactory<>("fullName"));
            emailF.setCellValueFactory(new PropertyValueFactory<>("email"));
            telF.setCellValueFactory(new PropertyValueFactory<>("tel"));

            act.setCellFactory(column -> {


                return new TableCell<User, Void>() {
                    private final Button deleteButton = new Button("Delete");
                    private final Button getByIdButton = new Button("Get by ID");

                    {
                        deleteButton.setOnAction(event -> {
                            User user = getTableView().getItems().get(getIndex());
                            obslistus.remove(user);
                            try {
                                UserS.supprimer(user);
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }

                        });

                    }*/

