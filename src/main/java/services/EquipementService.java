package services;

import entities.Categorie;
import entities.Equipement;
import utils.MyDatabase;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EquipementService implements IService<Equipement> {
    Connection connection;
    private Statement ste;
    private PreparedStatement pst;

    public EquipementService() {
        connection = MyDatabase.getInstance().getConnection();
    }

    @Override
    public void add(Equipement equipement) throws SQLException {
        String req = "INSERT INTO equipement (nom,type,statut) VALUES(?,?,?)" ;
        PreparedStatement stmt = connection.prepareStatement(req);
        stmt.setString(1, equipement.getNom());
        stmt.setString(2, equipement.getType());
        stmt.setString(3, equipement.getStatut());
        int result=stmt.executeUpdate();
        System.out.println(result + " enregistrement ajouté.");

    }

    @Override
    public void delete (Equipement equipement) {
        try {
            String req = "delete from equipement where id_equipement=?";
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, equipement.getId_equipement());
            pst.executeUpdate();
            System.out.println("Suppression avec succes");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void update(Equipement equipement) {
        try {
            String req = "update equipement set nom=?,type=? ,statut=? where id_equipement=?";
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setString(1, equipement.getNom());
            pst.setString(2, equipement.getType());
            pst.setString(3, equipement.getStatut());
            pst.executeUpdate();
            System.out.println("Mise a jour avec succes");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<Equipement> readAll() throws SQLException{
        List<Equipement> equipementList = new ArrayList<>();
        String req = "SELECT * FROM equipement ";
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(req)) {
            while (rs.next()) {
                Equipement e= new Equipement();
               // e.setId_equipement(rs.getInt("id_equipement"));
                e.setNom(rs.getString("nom"));
                e.setStatut(rs.getString("statut"));
                e.setType(rs.getString("type"));
                 equipementList.add(e);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return equipementList;
    }

    @Override
    public Equipement readById(int id_equipement) {
        String requete = "select * from equipement where id_equipement=" + id_equipement;
        Equipement equipement = null;
        try {
            ste = connection.createStatement();
            ResultSet rs = ste.executeQuery(requete);
            while (rs.next()) {
                equipement = new Equipement(
                        rs.getInt("id_equipement"),
                        rs.getString("type"), // ajusté à la casse utilisée dans votre code
                        rs.getString("statut") // ajusté à la casse utilisée dans votre code
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return equipement;
    }
    public Equipement findById(int id_equipement) throws SQLException {
        Equipement u = new Equipement();
        try {
            String sql = "SELECT * FROM equipement WHERE id_equipement = " + id_equipement;
            Statement ste1 = connection.createStatement();
            ResultSet rs = ste1.executeQuery(sql);
            while (rs.next()) {
              //  u.setId_equipement(rs.getInt("id_equipement"));

                u.setNom(rs.getString("nom"));
                u.setStatut(rs.getString("statut"));

                u.setType(rs.getString("type"));

            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return u;
    }
    public boolean existeNom(String nom) throws SQLException {
        String query = "SELECT COUNT(*) FROM equipement WHERE nom = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query))
            {

            preparedStatement.setString(1, nom);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        }
        return false;
    }


}
