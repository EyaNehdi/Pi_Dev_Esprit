package controllers;

import entities.Quizz;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.QuizzService;

import java.sql.SQLException;

public class AjouterQuizz {

    @FXML
    private Button btnLister;

    @FXML
    private Button btnAjouter;

    @FXML
    private TextField tf_correctAnswer;

    @FXML
    private TextField tf_opt1;

    @FXML
    private TextField tf_opt2;

    @FXML
    private TextField tf_opt3;

    @FXML
    private TextField tf_opt4;

    @FXML
    private TextField tf_question;
    @FXML
    private Button btnRetour;

    @FXML
    void initialize() {
    }

    @FXML
    private void AjouterQuizz() {
        // Validation de saisie
        if (champsVides()) {
            afficherAlerte("Veuillez remplir tous les champs.");
            return;
        }

        // Vérification d'unicité
        if (quizzExisteDeja(tf_question.getText())) {
            afficherAlerte("Un quizz avec cette question existe déjà.");
            return;
        }

        // Ajout du quizz
        Quizz quizz = new Quizz(tf_question.getText(), tf_opt1.getText(), tf_opt2.getText(), tf_opt3.getText(), tf_opt4.getText(), tf_correctAnswer.getText());
        QuizzService serviceQuizz = new QuizzService();

        try {
            serviceQuizz.add(quizz);
            afficherAlerte("Ajout de quizz avec succès");

            // Changer de scène après l'ajout
            Stage stage = (Stage) btnAjouter.getScene().getWindow();
            SceneChanger.changerScene("/AfficherQuizz.fxml", stage);

        } catch (SQLException e) {
            afficherAlerte("Échec d'ajout de quizz. " + e.getMessage());
            System.out.println(e.getMessage());
        }
    }

    public void Lister() {
        Stage stage = (Stage) btnLister.getScene().getWindow();
        SceneChanger.changerScene("/AfficherQuizz.fxml", stage);
    }
    public void Retour() {
        Stage stage = (Stage) btnLister.getScene().getWindow();
        SceneChanger.changerScene("/AjouterCours.fxml", stage);
    }

    private boolean champsVides() {
        return tf_question.getText().isEmpty() || tf_opt1.getText().isEmpty() || tf_opt2.getText().isEmpty() ||
                tf_opt3.getText().isEmpty() || tf_opt4.getText().isEmpty() || tf_correctAnswer.getText().isEmpty();
    }

    private boolean quizzExisteDeja(String question) {
        // Vérifie si un quizz avec la même question existe déjà
        QuizzService serviceQuizz = new QuizzService();

        return serviceQuizz.quizzExists(question);
    }

    private void afficherAlerte(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
