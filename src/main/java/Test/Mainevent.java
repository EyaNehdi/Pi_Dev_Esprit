package Test;

import entities.event;
import javafx.application.Application;
import javafx.stage.Stage;
import services.servicesevent;
import java.sql.Date;
import java.sql.SQLException;

public class Mainevent  {
    public static void main (String [] args ){
        servicesevent servicesevent= new servicesevent();
        //event e1= new event(1,"atelier",Date.valueOf("2024-03-20"),Date.valueOf("2024-03-23"));
        event e2= new event(2,"session revision",Date.valueOf("2024-06-10"),Date.valueOf("2024-06-11"));
        event e3= new event(3,"pi dev ",Date.valueOf("2024-05-20"),Date.valueOf("2024-05-22"));

        try {
           // servicesevent.ajouter(e1);
            servicesevent.ajouter(e2);
            servicesevent.ajouter(e3);
            /*   servicesevent.modifier(e1);
              servicesevent.modifier(e2);
               servicesevent.modifier(e3);
             System.out.println(servicesevent.afficher());
            servicesevent.supprimer(3); */
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }

}
