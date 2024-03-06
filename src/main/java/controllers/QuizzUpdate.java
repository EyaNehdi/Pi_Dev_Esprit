package controllers;


import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import entities.Cours;
import entities.Quizz;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.CoursService;
import services.QuizzService;

public class QuizzUpdate implements Initializable {
@FXML
private Button btnRetour;
    @FXML
    private TextField tf_correctAnswer;

    @FXML
    private TextField tf_option1;

    @FXML
    private TextField tf_option2;

    @FXML
    private TextField tf_option3;

    @FXML
    private TextField tf_option4;

    @FXML
    private TextField tf_question;
    private int idCu;
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        LoadData();
    }
    public void LoadData() {

        QuizzService us = new QuizzService();
        try {
            Quizz u = us.findById(this.idCu);
            if (u != null) {
                if (u.getQuestion() != null) {
                    tf_question.setText(u.getQuestion());
                }
                if (u.getOption1() != null) {
                    tf_option1.setText(u.getOption1());
                }
                if (u.getOption2() != null) {
                    tf_option2.setText(u.getOption2());
                }
                if (u.getOption3() != null) {
                    tf_option3.setText(u.getOption3());
                }
                if (u.getOption4() != null) {
                    tf_option4.setText(u.getOption4());
                }
                if (u.getCorrectAnswer() != null) {
                    tf_correctAnswer.setText(u.getCorrectAnswer());
                }


            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    public int initData(int id_quizz) {
        this.idCu = id_quizz;
        this.LoadData();
        System.err.println("ena aaaaaa" + this.idCu);
        return this.idCu;

    }
    @FXML
    private void updateData(ActionEvent event) {
        // Check if any field is empty
        if (tf_question.getText().isEmpty() ||
                tf_option1.getText().isEmpty() ||
                tf_option2.getText().isEmpty() ||
                tf_option3.getText().isEmpty() ||
                tf_option4.getText().isEmpty() ||
                tf_correctAnswer.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all fields!");
            alert.show();
            return;
        }

        try {
            // Fetch user data from the database
            QuizzService quizzService = new QuizzService();

            // Check uniqueness
            if (quizzService.quizzExists(tf_question.getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Un quizz avec cette question existe déjà!");
                alert.show();
                return;
            }

            Quizz quizzToUpdate = quizzService.findById(idCu);

            // Update quizz data
            quizzToUpdate.setQuestion(tf_question.getText());
            quizzToUpdate.setOption1(tf_option1.getText());
            quizzToUpdate.setOption2(tf_option2.getText());
            quizzToUpdate.setOption3(tf_option3.getText());
            quizzToUpdate.setOption4(tf_option4.getText());
            quizzToUpdate.setCorrectAnswer(tf_correctAnswer.getText());

            // Call the modifier method to update the quizz
            quizzService.update(quizzToUpdate);

            // Show success message
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setHeaderText(null);
            successAlert.setContentText("Modification réussie !!");
            successAlert.show();

        } catch (SQLException ex) {
            ex.printStackTrace(); // Handle exception appropriately
            // Show error message
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("An error occurred while updating quizz data!");
            errorAlert.show();
        }
    }

    public void Retour()
    {
        Stage stage = (Stage) btnRetour.getScene().getWindow();
        SceneChanger.changerScene("/AfficherQuizz.fxml", stage);
    }
}
