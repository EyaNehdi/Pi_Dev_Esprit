package services;

import entities.Abonnement;
import javafx.scene.control.TextField;
import utils.MyDatabase;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AbonnementService implements IService<Abonnement> {
    Connection connection;
    private Statement ste;
    private PreparedStatement pst;

    public AbonnementService() {
        connection = MyDatabase.getInstance().getConnection();
    }

    @Override
    public void add(Abonnement abonnement) throws SQLException {
        String req = "INSERT INTO abonnement(nom,prenom,mail,num) VALUES(?,?,?,?)" ;
        PreparedStatement stmt = connection.prepareStatement(req);
        stmt.setString(1, abonnement.getNom());
        stmt.setString(2, abonnement.getPrenom());



        stmt.setString(3, abonnement.getMail());
        stmt.setInt(4, abonnement.getNum());




        int result=stmt.executeUpdate();
        System.out.println(result + " enregistrement ajouté.");

    }

    @Override
    public void delete (Abonnement abonnement) {
        try {
            String req = "delete from abonnement where id_abonnement=?";
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, abonnement.getId_abonnement());
            pst.executeUpdate();
            System.out.println(abonnement.getId_abonnement());
            System.out.println("Suppression avec succes");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void update(Abonnement abonnement) {
        try {
            String req = "update abonnement set nom=?,prenom=? ,mail=? ,num=? where id_abonnement=?";
            PreparedStatement pst = connection.prepareStatement(req);

            pst.setString(1, abonnement.getNom());
            pst.setString(2, abonnement.getPrenom());
            pst.setString(3, abonnement.getMail());
            pst.setInt(4, abonnement.getNum());
            pst.setInt(5, abonnement.getId_abonnement());

            pst.executeUpdate();
            System.out.println("Mise a jour avec succes");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<Abonnement> readAll() throws SQLException{
        List<Abonnement> abonnementList = new ArrayList<>();
        String req = "SELECT * FROM abonnement ";

        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(req)) {
            while (rs.next()) {
                Abonnement a= new Abonnement( rs.getString("nom"), rs.getString("prenom"), rs.getString("mail"), rs.getInt("num"));
                a.setId_abonnement(rs.getInt("id_abonnement"));
                a.setNom(rs.getString("nom"));
                a.setPrenom(rs.getString("prenom"));
                a.setMail(rs.getString("mail"));
                a.setNum(rs.getInt("num"));

              abonnementList.add(a);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return abonnementList;
    }

    @Override
    public Abonnement readById(int id_abonnement) {
        String requete = "select * from abonnement where id_abonnement "+ id_abonnement;
        Abonnement abonnement = null;
        try {
            ste = connection.createStatement();
            ResultSet rs = ste.executeQuery(requete);
            while (rs.next()) {
                abonnement= new Abonnement(
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("mail"),
                        rs.getInt("num")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return abonnement;
    }
    public Abonnement findById(int id_abonnement) throws SQLException {
       Abonnement u = new Abonnement();
        try {
            String sql = "SELECT * FROM abonnement WHERE id_abonnement = " + id_abonnement;
            Statement ste1 = connection.createStatement();
            ResultSet rs = ste1.executeQuery(sql);
            while (rs.next()) {
                u.setId_abonnement(rs.getInt("id_abonnement"));
                u.setNom(rs.getString("nom"));
                u.setPrenom(rs.getString("prenom"));
                u.setMail(rs.getString("mail"));
                u.setNum(rs.getInt("num"));

            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return u;
    }


}