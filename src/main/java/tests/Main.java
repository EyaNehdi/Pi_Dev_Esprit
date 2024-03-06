package tests;
import utils.MyDatabase;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {


        public static void main(String[] args) throws SQLException {
            // Obtenir la connexion à la base de données
            MyDatabase db = MyDatabase.getInstance();
            Connection connection = db.getConnection();

            // Créer une instruction pour exécuter les requêtes SQL
            Statement statement = connection.createStatement();

            // Exécuter la requête de jointure
            String sql = "SELECT * FROM event INNER JOIN reservation ON event.nom_evenement = reservation.nom_evenement";
            ResultSet resultSet = statement.executeQuery(sql);


            // Traiter les résultats
            while (resultSet.next()) {
                String nomEvenement = resultSet.getString("nom_evenement");
                String date = resultSet.getString("date");
                String nomEleve = resultSet.getString("nom_eleve");

                // Afficher les données des tables jointes
                System.out.println("Evenement : " + nomEvenement + ", Date : " + date + ", Nom élève : " + nomEleve);
            }

            resultSet.close();
            statement.close();
            connection.close();
        }
    }

