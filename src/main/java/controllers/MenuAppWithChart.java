package controllers;

import services.UserService;
import javafx.beans.binding.Bindings;
import javafx.scene.control.*;
import org.jfree.chart.JFreeChart;

import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.*;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;


import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author spangsberg
 */
public class MenuAppWithChart implements Initializable {

    @FXML
    private BorderPane borderPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        borderPane.setCenter(buildPieChart());
    }

    @FXML
    private void handleShowBarChart() {

        borderPane.setCenter(buildBarChart());
    }

    @FXML
    private void handleShowPieChart() {
        borderPane.setCenter(buildPieChart());
    }


    private BarChart<String, Number> buildBarChart() {
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Devise");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Nombre de comptes");

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Solde des comptes par devise");

        XYChart.Series<String, Number> dataSeries = new XYChart.Series<>();
        dataSeries.setName("Solde des comptes");

        try (Connection conn = getConnection()) {
            // Exécuter les requêtes SQL pour récupérer le nombre de comptes par devise
            int x = userService.getNombreDeUser(conn, "admin");
            int y = userService.getNombreDeUser(conn, "eleve");
            int z = userService.getNombreDeUser(conn, "enseignant");

            // Ajouter les données au graphique
            dataSeries.getData().add(new XYChart.Data<>("admin", x));
            dataSeries.getData().add(new XYChart.Data<>("eleve", y));
            dataSeries.getData().add(new XYChart.Data<>("enseignant", z));

        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer l'erreur de connexion ou d'exécution de la requête SQL
        }

        barChart.getData().add(dataSeries);

        return barChart;
    }

    private Connection getConnection() throws SQLException {
        // Remplacez les valeurs suivantes par les détails de connexion à votre base de données
        String url = "jdbc:mysql://localhost:3306/esprit3a20";
        String username = "root";
        String password = "";
        return DriverManager.getConnection(url, username, password);
    }

    // Méthode pour récupérer le nombre de comptes pour une valeur de solde donnée

    UserService userService = new UserService();

    // Méthode pour construire le graphique en secteurs
    public PieChart buildPieChart() {
        try (Connection conn = getConnection()) {
            int h = userService.getNombreDeUser(conn, "admin");
            int x = userService.getNombreDeUser(conn, "eleve");
            int y = userService.getNombreDeUser(conn, "enseignant");


            // Préparation des données pour le graphique en secteurs
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                    new PieChart.Data("admin", x),
                    new PieChart.Data("eleve", y),
                    new PieChart.Data("enseignant", h)
            );

            PieChart pieChart = new PieChart(pieChartData); // Création du graphique en secteurs

            // Configurations du graphique en secteurs
            pieChart.setTitle("utilisateur");
            pieChart.setClockwise(true);
            pieChart.setLabelLineLength(50);
            pieChart.setLabelsVisible(true);
            pieChart.setLegendVisible(false);
            pieChart.setStartAngle(180);

            // Liaison des valeurs et des libellés sur chaque tranche de secteur pour refléter les changements
            pieChartData.forEach(data ->
                    data.nameProperty().bind(
                            Bindings.concat(data.getName(), " ", data.pieValueProperty(), " ")
                    )
            );

            return pieChart;
        } catch (SQLException e) {
            e.printStackTrace();
            // Gestion des erreurs de connexion ou d'exécution de la requête SQL
            return null;
        }
    }

    /**
     *
     */
    @FXML
    private void handleClose() {
        System.exit(0);
    }


    /**
     *
     */
    @FXML
    private void handleUpdatePieData() {
        Node node = borderPane.getCenter();

        if (node instanceof PieChart) {
            PieChart pc = (PieChart) node;
            double value = pc.getData().get(2).getPieValue();
            pc.getData().get(2).setPieValue(value * 1.10);
            createToolTips(pc);
        }
    }


    /**
     * Creates tooltips for all data entries
     *
     * @param pc
     */
    private void createToolTips(PieChart pc) {

        for (PieChart.Data data : pc.getData()) {
            String msg = Double.toString(data.getPieValue());

            Tooltip tp = new Tooltip(msg);
            tp.setShowDelay(Duration.seconds(0));

            Tooltip.install(data.getNode(), tp);

            //update tooltip data when changed
            data.pieValueProperty().addListener((observable, oldValue, newValue) ->
            {
                tp.setText(newValue.toString());
            });
        }
    }


    //save chart



    // Méthode utilitaire pour afficher une boîte de dialogue d'alerte
    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType, message, ButtonType.OK);
        alert.showAndWait();
    }
}