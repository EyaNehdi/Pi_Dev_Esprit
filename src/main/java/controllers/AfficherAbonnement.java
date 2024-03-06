package controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import entities.Abonnement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import services.AbonnementService;

public class AfficherAbonnement {
    AbonnementService serviceabonnement= new AbonnementService();
    private Abonnement selectedAbonnement;

    @FXML
    private Button btnModifier;

    @FXML
    private Button btnAjouter;

    @FXML
    private Button btnSupprimer;
    @FXML
    private TableColumn<Abonnement, Integer> id_abonnement;

    @FXML
    private TableColumn<Abonnement, String> mail;

    @FXML
    private TableColumn<Abonnement, String> nom;

    @FXML
    private TableColumn<Abonnement, Integer> num;

    @FXML
    private TextField tf_recherche;


    @FXML
    private TableColumn<Abonnement, String> prenom;

    @FXML
    private TableView<Abonnement> tv_abonnement;
    private FilteredList<Abonnement> filteredAbonnement;
    private ObservableList<Abonnement> AbonnementList;
    AbonnementService abonnementService = new AbonnementService();
    Abonnement abonnement = new Abonnement();
    public List<Abonnement> triEmail() throws SQLException {

        List<Abonnement> list1 = new ArrayList<>();
        List<Abonnement> list2 = serviceabonnement.readAll();

        list1 = list2.stream().sorted((o1, o2) -> o1.getNom().compareTo(o2.getNom())).collect(Collectors.toList());
        return list1;

    }
    @FXML
    private void Trie() throws SQLException {
        AbonnementService abonnementService1 = new AbonnementService();
        Abonnement abonnement1 = new Abonnement();
        List<Abonnement> abonnements = triEmail();
        ObservableList<Abonnement> observableCoursList = FXCollections.observableArrayList(abonnements);
       // id_abonnement.setCellValueFactory(new PropertyValueFactory<>("id_abonnement"));

        nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        mail.setCellValueFactory(new PropertyValueFactory<>("mail"));
        num.setCellValueFactory(new PropertyValueFactory<>("num"));
        tv_abonnement.setItems(observableCoursList);

    }
    @FXML
    void initialize() {
        try {
            btnModifier.setDisable(true);
            btnSupprimer.setDisable(true);

            nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
            prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
            mail.setCellValueFactory(new PropertyValueFactory<>("mail"));
            num.setCellValueFactory(new PropertyValueFactory<>("num"));

            ObservableList<Abonnement> AbonnementList = FXCollections.observableList(serviceabonnement.readAll());

            tv_abonnement.setItems(AbonnementList);

            // Créer un FilteredList pour filtrer les données affichées
            filteredAbonnement = new FilteredList<>(AbonnementList, p -> true);

            // Ajouter le ChangeListener pour mettre à jour le filtre lors de la saisie de l'utilisateur
            tf_recherche.textProperty().addListener((observable, oldValue, newValue) ->
                    filteredAbonnement.setPredicate(abonnement-> {
                        if (newValue == null || newValue.isEmpty()) {
                            return true; // Afficher tous les abo si la recherche est vide
                        }

                        String lowerCaseFilter = newValue.toLowerCase();
                      //  id_abonnement.setVisible(false);

                        // Filtrer par titre ou contenu (ajoutez d'autres propriétés si nécessaire)
                        return  (abonnement.getNom() != null && abonnement.getNom().toLowerCase().contains(lowerCaseFilter)) ||
                                (abonnement.getPrenom() != null && abonnement.getPrenom().toLowerCase().contains(lowerCaseFilter)) ||
                                (abonnement.getMail() != null && abonnement.getMail().toLowerCase().contains(lowerCaseFilter)) ||
                                (abonnement.getNum() != null && String.valueOf(abonnement.getNum()).toLowerCase().contains(lowerCaseFilter));

                    }));

            // Lier le FilteredList à la TableView
            tv_abonnement.setItems(filteredAbonnement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML

    public void Retour()
    {
        //Stage stage = (Stage) btnRetour.getScene().getWindow();
        //SceneChanger.changerScene("/Ajouterabonnement.fxml", stage);
    }
    @FXML
    public void update(MouseEvent event) {
        // Votre logique de mise à jour ici
    }


    public void mouseClicked(MouseEvent mouseEvent) {
        selectedAbonnement  = tv_abonnement.getSelectionModel().getSelectedItem();
        btnModifier.setDisable(false);
        btnSupprimer.setDisable(false);
    }
    @FXML
    public void Modifier() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierAbonnement.fxml"));
        Parent root = loader.load();
        // Obtenez la référence au contrôleur QuizzUpdate
        controllers.ModifierAbonnement controller = loader.getController();

        // Assurez-vous que l'objet Quizz n'est pas nul avant d'appeler initData
        if (selectedAbonnement != null) {
            // Appelez initData avec l'ID du Quizz sélectionné
            System.out.println(selectedAbonnement.getId_abonnement());
            controller.initData(selectedAbonnement.getId_abonnement());

            // Affichez la nouvelle scène
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } else {
            // Afficher une alerte si aucun Quizz n'est sélectionné
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune sélection");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un Quizz à modifier.");
            alert.showAndWait();
        }


    }

    public void Ajouter() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterAbonnement.fxml"));
        Parent root = loader.load();
        controllers.AjouterAbonnement controller = loader.getController();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }
    @FXML
    public void delete(ActionEvent event) {
        try {
            Abonnement abonnementSelectionne = tv_abonnement.getSelectionModel().getSelectedItem();

            if (abonnementSelectionne != null) {
                AbonnementService ps = new AbonnementService();
                ps.delete(abonnementSelectionne);
                initialize();
            } else {
                // Afficher une alerte si aucun cours n'est sélectionné
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Aucune sélecton");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez sélectionner un abonnement  à supprimer.");
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }





}
