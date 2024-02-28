package services;

import entities.Cours;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CoursService implements IService<Cours> {
    Connection connection;
    private Statement ste;
    private PreparedStatement pst;

    public CoursService() {
        connection = MyDatabase.getInstance().getConnection();
    }

    @Override
    public void add(Cours cours) throws SQLException {
        String req = "INSERT INTO cours (titre, contenu) VALUES (?, ?)";
        PreparedStatement stmt = connection.prepareStatement(req);
        stmt.setString(1, cours.getTitre());
        stmt.setString(2, cours.getContenu());
        int result = stmt.executeUpdate();
        System.out.println(result + " enregistrement ajouté.");
    }

    @Override
    public void delete(Cours cours) throws SQLException {
        try {
            String req = "DELETE FROM cours WHERE id_cours=?";
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, cours.getId_cours());
            pst.executeUpdate();
            System.out.println("Suppression avec succès");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void update(Cours cours) throws SQLException {
        try {
            String req = "UPDATE cours SET titre=?, contenu=? WHERE id_cours=?";
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setString(1, cours.getTitre());
            pst.setString(2, cours.getContenu());
            pst.setInt(3, cours.getId_cours());
            pst.executeUpdate();
            System.out.println("Mise à jour avec succès");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<Cours> readAll() throws SQLException {
        List<Cours> coursList = new ArrayList<>();
        String req = "SELECT * FROM cours";
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(req)) {
            while (rs.next()) {
                Cours c = new Cours();
                c.setId_cours(rs.getInt("id_cours"));
                c.setTitre(rs.getString("titre"));
                c.setContenu(rs.getString("contenu"));
                coursList.add(c);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return coursList;
    }

    @Override
    public Cours readById(int id_cours) throws SQLException {
        String requete = "SELECT * FROM cours WHERE id_cours=" + id_cours;
        Cours cours = null;
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(requete)) {
            while (rs.next()) {
                cours = new Cours(
                        rs.getInt("id_cours"),
                        rs.getString("titre"),
                        rs.getString("contenu")
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return cours;
    }

    public Cours findById(int id_cours) throws SQLException {
        Cours u = new Cours();
        try {
            String sql = "SELECT * FROM cours WHERE id_cours = " + id_cours;
            Statement ste1 = connection.createStatement();
            ResultSet rs = ste1.executeQuery(sql);
            while (rs.next()) {
                u.setId_cours(rs.getInt("id_cours"));
                u.setTitre(rs.getString("titre"));
                u.setContenu(rs.getString("contenu"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return u;
    }

    public List<Cours> getAllCours() throws SQLException {
        return readAll();
    }
}
