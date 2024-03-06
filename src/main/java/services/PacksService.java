package services;

import entities.Packs;
import utils.MyDatabase;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PacksService implements IService<Packs> {
    Connection connection;
    private Statement ste;
    private PreparedStatement pst;

    public PacksService() {
        connection = MyDatabase.getInstance().getConnection();
    }

    @Override
    public void add(Packs packs) throws SQLException {
        String req = "INSERT INTO packs(nom,description,prix,type) VALUES(?,?,?,?)" ;
        PreparedStatement stmt = connection.prepareStatement(req);
        stmt.setString(1, packs.getNom());
        stmt.setString(2, packs.getDescription());
        stmt.setFloat(3, packs.getPrix());
        stmt.setString(4, packs.getType());
        int result=stmt.executeUpdate();
        System.out.println(result + " enregistrement ajouté.");

    }

    @Override
    public void delete (Packs packs) {
        try {
            String req = "delete from packs where id_packs=?";
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, packs.getId_Pack());
            pst.executeUpdate();
            System.out.println("Suppression avec succes");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void update(Packs packs) {
        try {
            String req = "update packs set nom=?,description=? ,prix=? ,type=?where id_packs=?";
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, packs.getId_Pack());
            pst.setString(2, packs.getNom());
            pst.setString(3, packs.getDescription());
            pst.setFloat(4, packs.getPrix());
            pst.setString(5, packs.getType());
            pst.executeUpdate();
            System.out.println("Mise a jour avec succes");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<Packs> readAll() throws SQLException {
        List<Packs> packsList = new ArrayList<>();
        String req = "SELECT * FROM packs";
        try (PreparedStatement pstmt = connection.prepareStatement(req);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Packs p = new Packs();
                p.setId_Pack(rs.getInt("id_packs"));
                p.setNom(rs.getString("nom"));
                p.setDescription(rs.getString("description"));
                p.setPrix(rs.getFloat("prix"));
                p.setType(rs.getString("type"));
                packsList.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des packs : " + e.getMessage());
        }
        return packsList;
    }


    @Override
    public Packs readById(int id_packs) {
        String requete = "select * from packs where id_packs "+ id_packs;
        Packs packs = null;
        try {
            ste = connection.createStatement();
            ResultSet rs = ste.executeQuery(requete);
            while (rs.next()) {
                packs= new Packs(
                        rs.getString("nom"),
                        rs.getString("description"),
                        rs.getFloat("prix"),
                        rs.getString("type")
                        );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return packs;
    }

    public Packs findById(int idCu) {
        // Implémentation de la logique pour rechercher et retourner le pack par son identifiant
        // Cette méthode doit retourner un objet packs
        return null; // Ou retournez le pack trouvé
    }

}

