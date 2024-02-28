package controllers;

import entities.Quizz;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import services.QuizzService;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class PlayQuizz {

    @FXML
    private Button opt1;

    @FXML
    private Button opt2;

    @FXML
    private Button opt3;

    @FXML
    private Button opt4;

    @FXML
    private Label question;

    private List<Quizz> quizzQuestions;
    private int counter = 0;
    private int correct = 0;
    private int wrong = 0;

    private QuizzService quizzService = new QuizzService();

    @FXML
    void opt1Clicked(ActionEvent event) {
        checkAnswer(opt1.getText().toString());
        handleNextQuestion(event);
    }

    @FXML
    void opt2Clicked(ActionEvent event) {
        checkAnswer(opt2.getText().toString());
        handleNextQuestion(event);
    }

    @FXML
    void opt3Clicked(ActionEvent event) {
        checkAnswer(opt3.getText().toString());
        handleNextQuestion(event);
    }

    @FXML
    void opt4Clicked(ActionEvent event) {
        checkAnswer(opt4.getText().toString());
        handleNextQuestion(event);
    }

    private void handleNextQuestion(ActionEvent event) {
        if (counter == quizzQuestions.size() - 1) {
            showResultStage(event);
        } else {
            counter++;
            Platform.runLater(this::loadQuestions);
        }
    }

    private void showResultStage(ActionEvent event) {
        try {
            System.out.println("Correct Answers: " + correct);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Result.fxml"));
            Parent root = loader.load();

            // Accédez au contrôleur de résultats et initialisez les données si nécessaire
            ResultController resultController = loader.getController();
            resultController.initData(correct, quizzQuestions.size());

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            // Fermez la fenêtre actuelle (le quizz)
            Stage thisStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            thisStage.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private void loadQuestions() {
        Platform.runLater(() -> {
            Quizz currentQuestion = quizzQuestions.get(counter);
            System.out.println("Loading question: " + currentQuestion.getQuestion());
            question.setTextFill(Color.BLUE);
            question.setText(currentQuestion.getQuestion());
            opt1.setText(currentQuestion.getOption1());
            opt2.setText(currentQuestion.getOption2());
            opt3.setText(currentQuestion.getOption3());
            opt4.setText(currentQuestion.getOption4());
        });
    }


    @FXML
    void initialize() {
        quizzQuestions = quizzService.getRandomQuizzQuestions(10);
        Collections.shuffle(quizzQuestions);

        for (Quizz q : quizzQuestions) {
            System.out.println("Question: " + q.getQuestion());
        }

        loadQuestions();
    }

    private boolean checkAnswer(String answer) {
        Quizz currentQuestion = quizzQuestions.get(counter);
        String correctAnswer = currentQuestion.getCorrectAnswer();

        System.out.println("Selected Answer: " + answer);
        System.out.println("Correct Answer: " + correctAnswer);

        boolean isCorrect = answer.equals(correctAnswer);
        System.out.println("Is Correct? " + isCorrect);
        if (isCorrect) {
            correct++; // Incrémenter le compteur de réponses correctes
        }
        return isCorrect;
    }
}
