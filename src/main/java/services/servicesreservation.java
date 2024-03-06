package services;

import entities.Cours;
import entities.reservation;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class servicesreservation implements IService<reservation> {

    private Connection connection;

    public servicesreservation() { connection = MyDatabase.getInstance().getConnection();}

    @Override
    public void add(reservation reservation) throws SQLException {
        String req = "INSERT INTO reservation (id_user, event_id  ) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(req)) {
            ps.setInt(1, reservation.getId_user());
            ps.setInt(2, reservation.getEvent_id());
            ps.executeUpdate();
            System.out.println("reservation ajouté avec succès");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public void update(reservation reservation) throws SQLException {
        String req = "UPDATE reservation SET id_user=?, event_id=? WHERE id=?";
        try (PreparedStatement ps = connection.prepareStatement(req)) {
            ps.setInt(1, reservation.getId_user());
            ps.setInt(2, reservation.getEvent_id());
            ps.setInt(3, reservation.getId());
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Réservation modifiée avec succès");
            } else {
                System.out.println("Aucune réservation n'a été modifiée. Vérifiez l'ID de la réservation.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void delete(reservation res) throws SQLException {
        try {
            String req = "DELETE FROM reservation WHERE id=?";
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, res.getId());
            pst.executeUpdate();
            System.out.println("Suppression avec succès");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }


    @Override
    public List<reservation> readAll() throws SQLException {
        List<reservation> reservations = new ArrayList<>();
        String req = "SELECT * FROM reservation";
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(req)) {
            while (rs.next()) {
                reservation r = new reservation();
                r.setId(rs.getInt("id"));
                r.setId_user(rs.getInt("id_user"));
                r.setEvent_id(rs.getInt("event_id")); // Changer "event" en "event_id"
                reservations.add(r);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return reservations;
    }

    @Override
    public reservation readById(int id) throws SQLException {
        return null;
    }
}
