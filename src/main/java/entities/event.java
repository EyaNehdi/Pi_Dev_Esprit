package entities;


import java.sql.Date;
import java.time.LocalDate;

public class event {

    private int id ;
    private String nom_evenement ;
    private Date date_debut ;
    private Date date_fin;

   public event (String nom, LocalDate date){

   }

    public event(int id, String nom_evenement, Date date_debut, Date date_fin) {
        this.id = id;
        this.nom_evenement = nom_evenement;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
    }

    public event(String nom_evenement, Date date_debut, Date date_fin) {
        this.nom_evenement = nom_evenement;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom_evenement() {
        return nom_evenement;
    }

    public void setNom_evenement(String nom_evenement) {
        this.nom_evenement = nom_evenement;
    }

    public Date getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(Date date_debut) {
        this.date_debut = date_debut;
    }

    public Date getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(Date date_fin) {
        this.date_fin = date_fin;
    }

    @Override
    public String toString() {
        return "event{" +
                "id=" + id +
                ", nom_evenement='" + nom_evenement + '\'' +
                ", date_debut=" + date_debut +
                ", date_fin=" + date_fin +
                '}';
    }
}
