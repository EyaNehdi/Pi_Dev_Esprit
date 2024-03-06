package controllers;

import services.UserService;
import services.emailService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

import java.security.SecureRandom;
import java.util.Objects;

public class Motdepasseoublier {

    @FXML
    private TextField codetf;

    @FXML
    private TextField confirmepasswordtf;

    @FXML
    private TextField emailtf;

    @FXML
    private TextField passwordtf;

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public static String generateCode() {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(5);

        for (int i = 0; i < 5; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            sb.append(randomChar);
        }

        return sb.toString();
    }

    public String inputcode = generateCode();

    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType, message, ButtonType.OK);
        alert.showAndWait();
    }

    @FXML
    void confirmer(ActionEvent event) {
        if (passwordtf.getText().isEmpty() || confirmepasswordtf.getText().isEmpty() || codetf.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Champs vides !");
        } else if (!confirmepasswordtf.getText().equals(passwordtf.getText())) {
            showAlert(Alert.AlertType.ERROR, "Vérifiez votre mot de passe !");
        } else if (!Objects.equals(codetf.getText(), inputcode)) {
            showAlert(Alert.AlertType.ERROR, "Code incorrect !");
        } else {
            UserService userservice = new UserService();
            String emails = emailtf.getText();
            String passwd = passwordtf.getText();
            userservice.updateUserPasswordByEmail(emails, passwd);
        }
    }

    @FXML
    void sendmail(ActionEvent event) {
        String destinataire = emailtf.getText();
        String sujet = "Code de réinitialisation de mot de passe";
        String contenu = "Bonjour,Votre code de réinitialisation de mot de passe est : " + inputcode;
        System.out.println("Email try !");
        try {
            emailService.sendEmail(destinataire, sujet, contenu);
            System.out.println("Email envoyé avec succès !");
            showAlert(Alert.AlertType.INFORMATION, "Code envoyé par e-mail !");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erreur lors de l'envoi de l'e-mail !");
        }
    }
}