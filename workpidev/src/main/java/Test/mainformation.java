package Test;

import entities.formation;
import javafx.application.Application;
import javafx.stage.Stage;
import services.servicesformation;

import java.sql.SQLException;

public class mainformation  {

    public static void main (String [] args ) {
        servicesformation servicesformation = new servicesformation();
        formation f1 = new formation(1, "formationai", "23-04-2024");
        formation f2 = new formation(2, " python", "22-05-2024");
        formation f3 = new formation(3, "formation BI", "12-11-2023");
        try {
            servicesformation.ajouter(f3);
              //servicesformation.ajouter(f2);
            //   servicesformation.ajouter(f3);
              //servicesformation.modifier(f1);
            //System.out.println(servicesformation.afficher());
            servicesformation.supprimer(3);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


    }

    //@Override
    public void start(Stage stage) throws Exception {

    }
}
