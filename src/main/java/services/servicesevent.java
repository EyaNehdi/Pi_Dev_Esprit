package services;

import entities.event;
import Utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class servicesevent implements Iservice<event> {
    Connection connection;

    public servicesevent() {
        connection = MyDatabase.getInstance().getConnection();
    }

    @Override
    public void ajouter(event event) throws SQLException {
        if (event.getNom_evenement() == null || event.getNom_evenement().isEmpty()) {
            System.out.println("Le nom de l'événement ne peut pas être vide");
            return;
        }

        String req = "INSERT INTO event (nom_evenement, date_debut , date_fin ) VALUES (?, ?,?)";
        try (PreparedStatement ps = connection.prepareStatement(req)) {
            ps.setString(1, event.getNom_evenement());
            ps.setDate(2, event.getDate_debut());
            ps.setDate(3, event.getDate_fin());
            ps.executeUpdate();
            System.out.println("Événement ajouté avec succès");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(event event) throws SQLException {
        String req = "UPDATE event SET nom_evenement=?, date_debut=?  , date_fin =? WHERE id=?";
        try (PreparedStatement ps = connection.prepareStatement(req)) {
            ps.setString(1, event.getNom_evenement());
            ps.setDate(2, event.getDate_debut());
            ps.setDate(3, event.getDate_fin());
            ps.setInt(4, event.getId());
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Événement modifié avec succès");
            } else {
                System.out.println("Aucun événement n'a été modifié. Vérifiez l'ID de l'événement.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String requete = "DELETE FROM event WHERE ID = ?";
        try (PreparedStatement ps = connection.prepareStatement(requete)) {
            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Événement supprimé avec succès");
            } else {
                System.out.println("Aucun événement trouvé avec cet ID.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<event> afficher() throws SQLException {
        List<event> events = new ArrayList<>();
        String req = "SELECT * FROM event";
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(req)) {
            while (rs.next()) {
                int id = rs.getInt("ID");
                String nom_evenement = rs.getString("nom_evenement");
                Date date_debut = rs.getDate("date_debut");
                Date date_fin = rs.getDate("date_fin");

                event e = new event(nom_evenement, date_debut, date_fin);
                e.setId(id);

                events.add(e);
            }
        }
        return events;
    }
}
