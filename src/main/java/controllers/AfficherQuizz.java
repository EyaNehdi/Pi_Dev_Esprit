package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import entities.Cours;
import entities.Quizz;
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
import services.CoursService;
import services.QuizzService;

public class AfficherQuizz {
    QuizzService serviceQuizz = new QuizzService();
    @FXML
    private Button btnModifier;

    @FXML
    private Button btnRetour;

    @FXML
    private Button btnSupprimer;

    @FXML
    private TableColumn<Quizz, String> correctAnswer;

    @FXML
    private TableColumn<Quizz, String> option1;

    @FXML
    private TableColumn<Quizz, String> option2;

    @FXML
    private TableColumn<Quizz, String> option3;

    @FXML
    private TableColumn<Quizz, String> option4;

    @FXML
    private TableColumn<Quizz, String> question;
    @FXML
    private TableView<Quizz> tv_quizz;
private FilteredList<Quizz> filteredQuizz;
    @FXML
    private TextField tf_recherche;
    @FXML
    void initialize() {
        try {
            btnModifier.setDisable(true);
            btnSupprimer.setDisable(true);
            ObservableList<Quizz> quizzList = FXCollections.observableList(serviceQuizz.readAll());
            tv_quizz.setItems(quizzList);
            question.setCellValueFactory(new PropertyValueFactory<>("question"));
            option1.setCellValueFactory(new PropertyValueFactory<>("option1"));
            option2.setCellValueFactory(new PropertyValueFactory<>("option2"));
            option3.setCellValueFactory(new PropertyValueFactory<>("option3"));
            option4.setCellValueFactory(new PropertyValueFactory<>("option4"));
            correctAnswer.setCellValueFactory(new PropertyValueFactory<>("correctAnswer"));
            // Créer un FilteredList pour filtrer les données affichées
            filteredQuizz = new FilteredList<>(quizzList, p -> true);

            // Ajouter le ChangeListener pour mettre à jour le filtre lors de la saisie de l'utilisateur
            tf_recherche.textProperty().addListener((observable, oldValue, newValue) ->
                    filteredQuizz.setPredicate(quizz -> {
                        if (newValue == null || newValue.isEmpty()) {
                            return true; // Afficher tous les cours si la recherche est vide
                        }

                        String lowerCaseFilter = newValue.toLowerCase();

                        // Filtrer par titre ou contenu (ajoutez d'autres propriétés si nécessaire)
                        return quizz.getQuestion().toLowerCase().contains(lowerCaseFilter) ||
                                quizz.getOption1().toLowerCase().contains(lowerCaseFilter)||
                                quizz.getOption2().toLowerCase().contains(lowerCaseFilter) ||
                                quizz.getOption3().toLowerCase().contains(lowerCaseFilter) ||
                        quizz.getOption4().toLowerCase().contains(lowerCaseFilter) ||
                        quizz.getCorrectAnswer().toLowerCase().contains(lowerCaseFilter);
                    }));

            // Lier le FilteredList à la TableView
            tv_quizz.setItems(filteredQuizz);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public void mouseClicked(MouseEvent mouseEvent) {
        Quizz quizz = tv_quizz.getSelectionModel().getSelectedItem();
        quizz = new Quizz(quizz.getId_quizz(),quizz.getQuestion(), quizz.getOption1(), quizz.getOption2(), quizz.getOption3(), quizz.getOption4(), quizz.getCorrectAnswer());
        question.setText(quizz.getQuestion());
        option1.setText(quizz.getOption1());
        option2.setText(quizz.getOption2());
        option3.setText(quizz.getOption3());
        option4.setText(quizz.getOption4());
        correctAnswer.setText(quizz.getCorrectAnswer());
        btnModifier.setDisable(false);
        btnSupprimer.setDisable(false);
    }
    @FXML
    public void Modifier() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/QuizzCard.fxml"));
        Parent root = loader.load();

        // Obtenez la référence au contrôleur QuizzUpdate
        QuizzUpdate controller = loader.getController();

        // Récupérez l'objet Quizz sélectionné dans le TableView
        Quizz quizzSelectionne = tv_quizz.getSelectionModel().getSelectedItem();

        // Assurez-vous que l'objet Quizz n'est pas nul avant d'appeler initData
        if (quizzSelectionne != null) {
            // Appelez initData avec l'ID du Quizz sélectionné
            controller.initData(quizzSelectionne.getId_quizz());

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

    @FXML
    public void delete(ActionEvent event) {
        try {
            Quizz quizzSelectionne = tv_quizz.getSelectionModel().getSelectedItem();

            if (quizzSelectionne != null) {
                QuizzService ps = new QuizzService();
                ps.delete(quizzSelectionne);
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
    public void Retour()
    {
        Stage stage = (Stage) btnRetour.getScene().getWindow();
        SceneChanger.changerScene("/AjouterQuizz.fxml", stage);
    }
}
