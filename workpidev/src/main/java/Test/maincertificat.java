package Test;

import entities.certificat;
import services.servicescertificat;

import java.sql.SQLException;

public class maincertificat {
    public static void main (String [] args ) {
        servicescertificat servicescertificat = new servicescertificat();
        certificat c1 = new certificat(1, "nour", "ahmed", "formation_ai", "23-05-2024");
        certificat c2 = new certificat(2, "sami", "omar", "formation_ai", "23-04-2024");

        try {
            //servicescertificat.ajouter(c1);
            servicescertificat.ajouter(c1);
            //   servicescertificat.ajouter(c3);
            //  servicescertificat.modifier(c3);
            //System.out.println(servicescertificat.afficher());
            //servicescertificat.supprimer(1);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


    }}

