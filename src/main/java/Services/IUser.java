package Services;

import java.sql.SQLException;


public interface IUser<T> {
    public void ajouter(T t) throws  SQLException;

   // void modifier(String nom ,String prenom, String email,String mdp,String tel) throws SQLException;


    void supprimer(String nom) throws SQLException;


}
