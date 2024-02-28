package controllers;

import java.io.IOException;
import java.sql.SQLException;

import entities.Cours;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import services.CoursService;
import controllers.AjouterCours;

public class AfficherCours {
CoursService serviceCours = new CoursService();
    private int idCu;
    @FXML
    private TableColumn<Cours, String> contenu;

    @FXML
    private TableColumn<Cours, String> titre;

    @FXML
    private TableView<Cours> tv_cours;
    @FXML
    private Button btnModifier;

    @FXML
    private Button btnRetour;

    @FXML
    private Button btnSupprimer;
    private FilteredList<Cours> filteredCours;
    @FXML
    void initialize() {
        try {
            btnModifier.setDisable(true);
            btnSupprimer.setDisable(true);
            ObservableList<Cours> coursList = FXCollections.observableList(serviceCours.readAll());
            tv_cours.setItems(coursList);
            titre.setCellValueFactory(new PropertyValueFactory<>("titre"));
            contenu.setCellValueFactory(new PropertyValueFactory<>("contenu"));

            // Créer un FilteredList pour filtrer les données affichées
            filteredCours = new FilteredList<>(coursList, p -> true);

            // Ajouter le ChangeListener pour mettre à jour le filtre lors de la saisie de l'utilisateur
            tf_recherche.textProperty().addListener((observable, oldValue, newValue) ->
                    filteredCours.setPredicate(cours -> {
                        if (newValue == null || newValue.isEmpty()) {
                            return true; // Afficher tous les cours si la recherche est vide
                        }

                        String lowerCaseFilter = newValue.toLowerCase();

                        // Filtrer par titre ou contenu (ajoutez d'autres propriétés si nécessaire)
                        return cours.getTitre().toLowerCase().contains(lowerCaseFilter) ||
                                cours.getContenu().toLowerCase().contains(lowerCaseFilter);
                    }));

            // Lier le FilteredList à la TableView
            tv_cours.setItems(filteredCours);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void Retour()
    {
        Stage stage = (Stage) btnRetour.getScene().getWindow();
        SceneChanger.changerScene("/AjouterCours.fxml", stage);
    }

    public void mouseClicked(MouseEvent mouseEvent) {
        Cours selectedcours = tv_cours.getSelectionModel().getSelectedItem();
        selectedcours = new Cours(selectedcours.getId_cours(),selectedcours.getTitre(), selectedcours.getContenu());
        titre.setText(selectedcours.getTitre());
        contenu.setText(selectedcours.getContenu());
        btnModifier.setDisable(false);
        btnSupprimer.setDisable(false);
    }
    @FXML
    public void Modifier() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/CoursCard.fxml"));
        Parent root = loader.load();
        CoursUpdate controller = loader.getController();
        controller.initData(Cours.getId_cours());
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();


    }
    @FXML
    public void delete(ActionEvent event) {
        try {
            Cours coursSelectionne = tv_cours.getSelectionModel().getSelectedItem();

            if (coursSelectionne != null) {
                CoursService ps = new CoursService();
                ps.delete(coursSelectionne);
                initialize();
            } else {
                // Afficher une alerte si aucun cours n'est sélectionné
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Aucune sélection");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez sélectionner un cours à supprimer.");
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private TextField tf_recherche;



}
