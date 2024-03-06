package controllers;

import entities.Abonnement;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.AbonnementService;


import java.sql.SQLException;

public class AjouterAbonnement {
    @FXML
    private Button btn_Ajouter;

    @FXML
    private TextField mailf;

    @FXML
    private TextField nomf;

    @FXML
    private TextField numf;

    @FXML
    private TextField prenomf;

    public void Ajouterabonnement() {
        if (isValidInput()) {
            try {
                // Convertir la chaîne de caractères en entier pour le numéro
                int numero = Integer.parseInt(numf.getText());

                // Créer un nouvel abonnement avec les données saisies
                Abonnement abonnement = new Abonnement(nomf.getText(), prenomf.getText(), mailf.getText(), numero);

                // Instancier le service d'abonnement
                AbonnementService serviceabonnement = new AbonnementService();

                // Ajouter l'abonnement en utilisant le service d'abonnement
                serviceabonnement.add(abonnement);

                // Afficher une alerte de succès
                showAlert("Ajout avec succès");




                // Récupérer le numéro de téléphone de TelField
                String tel1 = numf.getText();
                String nom = nomf.getText();


                // Appeler la méthode SMSSender de SmsSender avec le numéro de téléphone récupéré
              //  SMSSENDER.sendSMS(tel1, "Bienvenue  "+nom+ "! Merci pour votre paiement en ligne !\nVotre renouvellement est maintenant actif.\nProfitez_enpleinement!\nCORDIALEMENT,\nEDUACT");


               Stage stage = (Stage) btn_Ajouter.getScene().getWindow();
                stage.close();

            } catch (NumberFormatException e) {
                // Gérer une exception si la conversion échoue (si le texte n'est pas un entier valide)
                showAlert("Veuillez entrer un numéro d'abonnement valide.");
            } catch (SQLException e) {
                // Gérer les exceptions SQL
                showAlert("Une erreur est survenue lors de l'ajout de l'abonnement.");
                System.out.println(e.getMessage());
            }
        }
    }

    private boolean isValidInput() {
        String nom = nomf.getText();
        String prenom = prenomf.getText();
        String mail = mailf.getText();
        String num = numf.getText();

        if (nom.isEmpty() || prenom.isEmpty() || mail.isEmpty() || num.isEmpty()) {
            showAlert("Veuillez remplir tous les champs.");
            return false;
        }

        if (!nom.matches("[a-zA-Z]+")) {
            showAlert("Le nom doit contenir uniquement des lettres.");
            return false;
        }

        if (!prenom.matches("[a-zA-Z]+")) {
            showAlert("Le prénom doit contenir uniquement des lettres.");
            return false;
        }

        try {
            int numero = Integer.parseInt(num);
        } catch (NumberFormatException e) {
            showAlert("Veuillez entrer un numéro d'abonnement valide.");
            return false;
        }

        return true;
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
