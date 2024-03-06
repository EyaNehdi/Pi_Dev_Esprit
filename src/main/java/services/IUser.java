package services;

import java.sql.SQLException;


public interface IUser<T> {
    public void ajouter(T t) throws  SQLException;

   void modifier(String Nom ,String Prenom, String Email,String Mdp,String Tel,int id) throws SQLException;


    void supprimer(String nom) throws SQLException;


}
