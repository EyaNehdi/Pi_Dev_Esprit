package services;

import entities.certificat;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class servicescertificat implements IService<certificat> {
    Connection connection;

    public servicescertificat() {
        connection = MyDatabase.getInstance().getConnection();
    }

    @Override
    public void add(certificat certificat) throws SQLException {
        // Check if the certificate name is unique before adding
        if (isNomCertificatUnique(certificat.getNom(), certificat.getId())) {
            String req = "INSERT INTO certificat (nomeleve, nomformateur, nom, date) VALUES (?, ?, ?, ?)";
            try (PreparedStatement ps = connection.prepareStatement(req)) {
                ps.setString(1, certificat.getNomeleve());
                ps.setString(2, certificat.getNomformateur());
                ps.setString(3, certificat.getNom());
                ps.setString(4, certificat.getDate());
                ps.executeUpdate();
                System.out.println("Certificat ajouté");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new SQLIntegrityConstraintViolationException("Le nom du certificat doit être unique.");
        }
    }

    @Override
    public void delete(certificat certificat) throws SQLException {
        try {
            String req = "DELETE FROM certificat WHERE id=?";
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, certificat.getId());
            pst.executeUpdate();
            System.out.println("Suppression avec succès");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private boolean isNomCertificatUnique(String nom, int id) throws SQLException {
        String req = "SELECT COUNT(*) FROM certificat WHERE nom=?";
        try (PreparedStatement ps = connection.prepareStatement(req)) {
            ps.setString(1, nom);
            ResultSet rs = ps.executeQuery();
            return rs.next() && rs.getInt(1) == 0;
        }
    }

    @Override
    public void update(certificat certificat) throws SQLException {
        // Check if the updated certificate name is unique, excluding the current certificate
        if (isNomCertificatUnique(certificat.getNom(), certificat.getId())) {
            String req = "UPDATE certificat SET nomeleve=?, nomformateur=?, nom=?, date=? WHERE id=?";
            try (PreparedStatement ps = connection.prepareStatement(req)) {
                ps.setString(1, certificat.getNomeleve());
                ps.setString(2, certificat.getNomformateur());
                ps.setString(3, certificat.getNom());
                ps.setString(4, certificat.getDate());
                ps.setInt(5, certificat.getId());
                ps.executeUpdate();
                System.out.println("Certificat modifié");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new SQLIntegrityConstraintViolationException("Le nom du certificat doit être unique.");
        }
    }



    @Override
    public List<certificat> readAll() throws SQLException {
        List<certificat> certificats = new ArrayList<>();
        String req = "SELECT * FROM certificat";
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(req)) {
            while (rs.next()) {
                certificat c = new certificat();
                c.setId(rs.getInt("id"));
                c.setNomeleve(rs.getString("nomeleve"));
                c.setNomformateur(rs.getString("nomformateur"));
                c.setNom(rs.getString("nom"));
                c.setDate(rs.getString("date"));
                certificats.add(c);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return certificats;
    }

    @Override
    public certificat readById(int id) throws SQLException {
        return null;
    }
}
