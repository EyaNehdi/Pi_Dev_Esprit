package controllers;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import  java.util.List;
import java.util.stream.Collectors;

import entities.Cours;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import services.CoursService;
import utils.MyDatabase;




public class AfficherCours extends Component {
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
    private Button btnPDF;
    Connection connection;
    @FXML
    private Button btnExcel;
    Cours cours = new Cours();
    public List<Cours> triEmail() throws SQLException {

        List<Cours> list1 = new ArrayList<>();
        List<Cours> list2 = serviceCours.readAll();

        list1 = list2.stream().sorted((o1, o2) -> o1.getTitre().compareTo(o2.getTitre())).collect(Collectors.toList());
        return list1;

    }
    @FXML
    private void Trie() throws SQLException {
        CoursService coursService = new CoursService();
        Cours cours = new Cours();
        List<Cours> cours1 = triEmail();
        ObservableList<Cours> observableCoursList = FXCollections.observableArrayList(cours1);

        titre.setCellValueFactory(new PropertyValueFactory<>("titre"));
        contenu.setCellValueFactory(new PropertyValueFactory<>("contenu"));

        tv_cours.setItems(observableCoursList);

    }
    @FXML
    void initialize() {
        try {
            btnModifier.setDisable(true);
            btnSupprimer.setDisable(true);

            titre.setCellValueFactory(new PropertyValueFactory<>("titre"));
            contenu.setCellValueFactory(new PropertyValueFactory<>("contenu"));

            ObservableList<Cours> coursList = FXCollections.observableList(serviceCours.readAll());

            tv_cours.setItems(coursList);

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
       Cours selectedCours = tv_cours.getSelectionModel().getSelectedItem();
       Stage currentStage = (Stage) btnModifier.getScene().getWindow();
       currentStage.close();
       if (selectedCours != null) {
           FXMLLoader loader = new FXMLLoader(getClass().getResource("/CoursCard.fxml"));
           Parent root = loader.load();
           CoursUpdate controller = loader.getController();
           controller.initData(selectedCours.getId_cours());
           Stage stage = new Stage();
           stage.setScene(new Scene(root));
           stage.showAndWait();
           // Après avoir fermé l'interface de modification, rafraîchissez la TableView
           refreshTableView();

       } else {
           // Afficher une alerte si aucun cours n'est sélectionné
           Alert alert = new Alert(Alert.AlertType.WARNING);
           alert.setTitle("Aucune sélection");
           alert.setHeaderText(null);
           alert.setContentText("Veuillez sélectionner un cours à modifier.");
           alert.showAndWait();
       }
   }

    private void refreshTableView() {
        tv_cours.getColumns().get(0).setVisible(false);
        tv_cours.getColumns().get(0).setVisible(true);
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

    @FXML
    private void exporterPDF(ActionEvent event) {
        System.out.println("hello");
        try {
            ensureConnectionInitialized();  // Assurez-vous que la connexion est initialisée

            if (connection != null) {
                JasperDesign jDesign = JRXmlLoader.load("C:\\Users\\Eyane\\WorkshopProjet\\src\\main\\resources\\report.jrxml");
                JasperReport jReport = JasperCompileManager.compileReport(jDesign);
                JasperPrint jPrint = JasperFillManager.fillReport(jReport, null, connection);

                JasperViewer viewer = new JasperViewer(jPrint, false);
                viewer.setTitle("Liste des Cours");
                viewer.show();
                System.out.println("hello");
            } else {
                System.out.println("La connexion à la base de données est null.");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void ensureConnectionInitialized() {
        if (connection == null) {
            initializeConnection();
        }
    }
    private void initializeConnection() {
        try {
            MyDatabase myDatabase = MyDatabase.getInstance();
            connection = myDatabase.getConnection();

            if (connection != null) {
                System.out.println("Connection établie");
            } else {
                System.err.println("La connexion à la base de données a échoué.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erreur lors de l'établissement de la connexion à la base de données.");
        }
    }
    @FXML
    private void ExportExcel(ActionEvent event) {
        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Liste des Cours");

            // En-tête
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Titre");
            headerRow.createCell(1).setCellValue("Contenu");

            // Données
            ObservableList<Cours> coursList = FXCollections.observableList(serviceCours.readAll());
            for (int i = 0; i < coursList.size(); i++) {
                Row row = sheet.createRow(i + 1);
                row.createCell(0).setCellValue(coursList.get(i).getTitre());
                row.createCell(1).setCellValue(coursList.get(i).getContenu());
            }

            // Sauvegarde du fichier
            String fileName = "liste_cours.xlsx";
            try (FileOutputStream fileOut = new FileOutputStream(fileName)) {
                workbook.write(fileOut);
                fileOut.flush();
            }

            System.out.println("Export Excel réussi.");
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
}
