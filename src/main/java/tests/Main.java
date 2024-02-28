package tests;

import entities.Quizz;
import services.CoursService;
import utils.MyDatabase;
import entities.Cours;
import services.QuizzService;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        MyDatabase.getInstance().getConnection();

        // Test de Cours
       // CoursService serviceCours = new CoursService();
        //Cours c1 = new Cours(3,"Cours1", "Voici le contenu du cours1");
       // Cours c2 = new Cours("Cours2", "Voici le contenu du cours2");

        // Test de Quizz
        QuizzService serviceQuizz = new QuizzService();
        Quizz q1= new Quizz("Quelle est la capitale de la Tunisie?","Djerba","Tunis","Ariana", "Touzeur","Tunis");

        try {
            // Ajout d'un cours
         // serviceCours.add(c1);
           // System.out.println("Cours ajouté : " + c1);
            // Ajout d'un quizz
            serviceQuizz.add(q1);
            System.out.println("Quizz ajouté : " + q1);

            // Affichage de tous les cours
            //List<Cours> coursList = serviceCours.readAll();
            //System.out.println("Liste des cours : " + coursList);

            // Affichage de tous les quizz
           // List<Quizz> quizzList = serviceQuizz.readAll();
            //System.out.println("Liste des quizz : " + quizzList);

            // Modification d'un cours
           // c1.setContenu("Eya Nehdi");
          // serviceCours.update(c1);
           // System.out.println("Cours mis à jour : " + c1);

            // Modification d'un quizz (mettez à jour l'ID en fonction de votre base de données)
           // q1.setReponse("Nouvelle réponse pour Quizz1");
           // serviceQuizz.update(q1);
           // System.out.println("Quizz mis à jour : " + q1);

            // Suppression d'un cours
           // serviceCours.delete(c1);
           //System.out.println("Cours supprimé : " + c1);

            // Suppression d'un quizz (mettez à jour l'ID en fonction de votre base de données)
           // serviceQuizz.delete(q1);
            //System.out.println("Quizz supprimé : " + q1);


        } catch (SQLException e) {
            System.out.println("Erreur : " + e.getMessage());
        }

    }
}
