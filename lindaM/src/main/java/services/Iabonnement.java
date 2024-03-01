package services;

import entities.Abonnement;

import java.sql.SQLException;
import java.util.List;

public interface Iabonnement<T> {
    void ajouter(Abonnement abonnement ) throws SQLException;

    boolean ajouterabonnement(Abonnement abonnement);
    Abonnement trouverabonnementParId(int id);
    List<Abonnement> listerTousLesabonnement();
    boolean mettreAJourabonnement(Abonnement abonnement );
    boolean supprimerabonnement(int id);

    List<Abonnement> afficher() throws SQLException;

    void supprimer(Abonnement abonnement );
}
