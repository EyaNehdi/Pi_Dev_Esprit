package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;

public class ResultController {

    @FXML
    private Label remark, marks, markstext, correcttext, wrongtext;

    @FXML
    private ProgressIndicator correct_progress, wrong_progress;

    private int correct;
    private int wrong;

    public void initData(int correct, int size) {
        System.out.println("Debug: Correct Answers received: " + correct);
        System.out.println("Debug: Total Questions received: " + size);

        this.correct = correct;
        this.wrong = size - correct;

        correcttext.setText("Réponse Correcte: " + correct);
        wrongtext.setText("Réponse injuste: " + wrong);

        marks.setText(correct + "/" + size);

        float correctPercentage = (float) correct / size;
        correct_progress.setProgress(correctPercentage);

        float wrongPercentage = (float) wrong / size;
        wrong_progress.setProgress(wrongPercentage);

        markstext.setText(correct + " Marks Scored");

        setRemark();
    }

    private void setRemark() {
        if (correct < 2) {
            remark.setText("Vous pouvez faire mieux!");
        } else if (correct >= 2 && correct < 5) {
            remark.setText("Assez bien.");
        } else if (correct >= 5 && correct <= 7) {
            remark.setText("Bien");
        } else if (correct >= 8 && correct <= 9) {
            remark.setText("Très bien.");
        } else if (correct == 10) {
            remark.setText("Excellent!");
        }
    }
}
