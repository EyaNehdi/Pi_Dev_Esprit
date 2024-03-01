package Test;

import services.servicesreservation;
import entities.reservation;
import java.sql.SQLException;




public class Mainreservation {
    public static void main(String[] args) {
        servicesreservation servicesreservation = new servicesreservation();
       reservation r1 = new reservation( 1,2,5);
        reservation r2 = new reservation( 2, 2,8);
       reservation r3 = new reservation( 3,5,5);
        try {


         /*  servicesreservation.ajouter(r1);
         servicesreservation.ajouter(r2);
           servicesreservation.ajouter(r3);
           // servicesreservation.modifier(r2);*/
              System.out.println(servicesreservation.afficher());
             //servicesreservation.supprimer(1);

        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }

    }
}

