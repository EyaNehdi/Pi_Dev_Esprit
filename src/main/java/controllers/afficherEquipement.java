package controllers;
import java.util.ArrayList;
import java.util.List;
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
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import services.EquipementService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.stream.Collectors;

import utils.MyDatabase;
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
    @FXML
    private Button btnExport;

    private FilteredList<Equipement> filteredEquipement;
    ObservableList<Equipement> equipements = FXCollections.observableArrayList();

    private MyDatabase myDatabase = MyDatabase.getInstance();
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
    public List<Equipement> triEmail() throws SQLException {

        List<Equipement> list1 = new ArrayList<>();
        List<Equipement> list2 = serviceEquipement.readAll();

        list1 = list2.stream().sorted((o1, o2) -> o1.getNom().compareTo(o2.getNom())).collect(Collectors.toList());
        return list1;

    }
    @FXML
    private void trie() throws SQLException {
        EquipementService equipementService = new EquipementService();
        Equipement equipement = new Equipement();
        List<Equipement> equipements1 = triEmail();
        ObservableList<Equipement> observableCoursList = FXCollections.observableArrayList(equipements1);

        nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        statut.setCellValueFactory(new PropertyValueFactory<>("statut"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        tv_equipement.setItems(observableCoursList);

    }
    @FXML
    private void pdf_user(ActionEvent event) {
        System.out.println("hello");
        try{

            JasperDesign jDesign = JRXmlLoader.load("C:\\Users\\CBE\\Desktop\\Workshop3A20JDBC\\src\\main\\resources\\report.jrxml");

            JasperReport jReport = JasperCompileManager.compileReport(jDesign);
            Connection connection = myDatabase.getConnection();

            JasperPrint jPrint = JasperFillManager.fillReport(jReport, null, connection);

            JasperViewer viewer = new JasperViewer(jPrint, false);

            viewer.setTitle("Liste des equipement");
            viewer.show();
            System.out.println("hello");


        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    @FXML
    private void ExportExcel(ActionEvent event) {
        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Liste des equipement");

            // En-tête
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("nom");
            headerRow.createCell(1).setCellValue("statut");
            headerRow.createCell(2).setCellValue("type");



            // Données
            ObservableList<Equipement> equipementList = FXCollections.observableList(serviceEquipement.readAll());
            for (int i = 0; i < equipementList.size(); i++) {
                Row row = sheet.createRow(i + 1);
                row.createCell(0).setCellValue(equipementList.get(i).getNom());
                row.createCell(1).setCellValue(equipementList.get(i).getStatut());
                row.createCell(2).setCellValue(equipementList.get(i).getType());
            }

            // Sauvegarde du fichier
            String fileName = "liste_equipement.xlsx";
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
