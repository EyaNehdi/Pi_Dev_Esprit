package services;

import entities.Categorie;
import entities.Equipement;
import utils.MyDatabase;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategorieService implements IService<Categorie> {
    Connection connection;
    private Statement ste;
    private PreparedStatement pst;

    public CategorieService() {
        connection = MyDatabase.getInstance().getConnection();
    }

    @Override
    public void add(Categorie categorie) throws SQLException {
        String req = "INSERT INTO categorie (numSerie,description) VALUES(?,?)" ;
        PreparedStatement stmt = connection.prepareStatement(req);
        stmt.setInt(1, categorie.getNumSerie());
        stmt.setString(2, categorie.getDescription());
        int result=stmt.executeUpdate();
        System.out.println(result + " enregistrement ajouté.");

    }

    @Override
    public void delete (Categorie categorie) {
        try {
            String req = "delete from categorie where id_categorie=?";
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, categorie.getId_categorie());
            pst.executeUpdate();
            System.out.println("Suppression avec succes");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void update(Categorie categorie) {
        try {
            String req = "update categorie set numSerie=?,description=? where id_categorie=?";
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, categorie.getNumSerie());
            pst.setString(2, categorie.getDescription());
            pst.setInt(3,categorie.getId_categorie());
            pst.executeUpdate();
            System.out.println("Mise a jour avec succes");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<Categorie> readAll() throws SQLException{
        List<Categorie> categorieList = new ArrayList<>();
        String req = "SELECT * FROM categorie ";
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(req)) {
            while (rs.next()) {
                Categorie c= new Categorie();
                //c.setId_categorie(rs.getInt("id_categorie"));
                c.setNumSerie(rs.getInt("numSerie"));
                c.setDescription(rs.getString("description"));

                categorieList.add(c);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return categorieList;
    }

    @Override
    public Categorie readById(int id_categorie) {
        String requete = "select * from categorie where id_categorie "+ id_categorie;
          Categorie categorie = null;
        try {
            ste = connection.createStatement();
            ResultSet rs = ste.executeQuery(requete);
            while (rs.next()) {
                categorie = new Categorie(
                        rs.getInt("id_categorie"),
                        rs.getInt("numSerie"), // ajusté à la casse utilisée dans votre code
                        rs.getString("description") // ajusté à la casse utilisée dans votre code
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return categorie;
    }
    public Categorie findById(int id_categorie) throws SQLException {
        Categorie u = new Categorie();
        try {
            String sql = "SELECT * FROM categorie WHERE id_categorie = " + id_categorie;
            Statement ste1 = connection.createStatement();
            ResultSet rs = ste1.executeQuery(sql);
            while (rs.next()) {
                u.setId_categorie(rs.getInt("id_categorie"));
                u.setNumSerie(rs.getInt("numSerie"));
                u.setDescription(rs.getString("description"));

            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return u;
    }}