package Controller;

import entities.Equipement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import services.EquipementService;

import java.io.IOException;
import java.sql.SQLException;

public class afficherEquipement {
    EquipementService serviceEquipement = new EquipementService();

    @FXML
    private TextField tf_recherche;

    @FXML
    private TableColumn<Equipement, String> nom;

    @FXML
    private TableColumn<Equipement, Integer> id_C;

    @FXML
    private TableColumn<Equipement, String> statut;

    @FXML
    private TableColumn<Equipement, String> type;

    @FXML
    private TableView<Equipement> tv_equipement;

    @FXML
    private Button btnModifier;

    @FXML
    private Button btnSupprimer;

    private FilteredList<Equipement> filteredEquipement;
    ObservableList<Equipement> equipements = FXCollections.observableArrayList();


    @FXML
    void initialize() {
        try {
            btnModifier.setDisable(true);
            btnSupprimer.setDisable(true);
            id_C.setCellValueFactory(new PropertyValueFactory<>("id_equipement"));
            nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
            statut.setCellValueFactory(new PropertyValueFactory<>("statut"));
            type.setCellValueFactory(new PropertyValueFactory<>("type"));

            ObservableList<Equipement> equipementList = FXCollections.observableList(serviceEquipement.readAll());

            // Créer un FilteredList pour filtrer les données affichées
            filteredEquipement = new FilteredList<>(equipementList, p -> true);

            // Ajouter le ChangeListener pour mettre à jour le filtre lors de la saisie de l'utilisateur
            tf_recherche.textProperty().addListener((observable, oldValue, newValue) ->
                    filteredEquipement.setPredicate(equipement -> {
                        if (newValue == null || newValue.isEmpty()) {
                            return true; // Afficher tous les équipements si la recherche est vide
                        }

                        String lowerCaseFilter = newValue.toLowerCase();

                        // Filtrer par nom, statut ou type
                        return equipement.getNom().toLowerCase().contains(lowerCaseFilter) ||
                                equipement.getStatut().toLowerCase().contains(lowerCaseFilter) ||
                                equipement.getType().toLowerCase().contains(lowerCaseFilter);
                    }));

            // Lier le FilteredList à la TableView
            tv_equipement.setItems(filteredEquipement);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void mouseClicked(MouseEvent mouseEvent) {
        Equipement equipement = tv_equipement.getSelectionModel().getSelectedItem();
        if (equipement != null) {
            btnModifier.setDisable(false);
            btnSupprimer.setDisable(false);
        }
    }

    @FXML
    public void Modifier() throws IOException {
        Equipement equipementSelectionne = tv_equipement.getSelectionModel().getSelectedItem();
        if (equipementSelectionne != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/modifierEquipement.fxml"));
            Parent root = loader.load();
            modifierEquipement controller = loader.getController();
            controller.initData(equipementSelectionne.getId_equipement()); // Utilisation de l'instance spécifique de Categorie
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        }
    }

    @FXML
    public void delete(ActionEvent event) {
        try {
            Equipement equipementSelectionne = tv_equipement.getSelectionModel().getSelectedItem();

            if (equipementSelectionne != null) {
                EquipementService eq=new EquipementService();
                eq.delete(equipementSelectionne);
                equipements.remove(equipementSelectionne);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("DONE");
                alert.setContentText("Delete done");
                alert.showAndWait();
                // Mettre à jour la liste observable
                //ObservableList<Equipement> equipements = FXCollections.observableList(serviceEquipement.readAll());
                //tv_equipement.setItems(equipements);
            } else {
                // Afficher une alerte si aucun équipement n'est sélectionné
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Aucune sélection");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez sélectionner un équipement à supprimer.");
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
