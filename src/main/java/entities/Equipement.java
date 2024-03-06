package entities;

public class Equipement {
    private  int id_equipement ;
    private String nom , statut ;
    private  String type ;

    public Equipement(int idEquipement, String type, String statut) {
    }

    public Equipement(int id_equipement, String nom, String statut, String type) {
        this.id_equipement = id_equipement;
        this.nom = nom;
        this.statut = statut;
        this.type = type;
    }

    public Equipement(String nom, String statut, String type) {
        this.nom = nom;
        this.statut = statut;
        this.type = type;
    }

    public Equipement() {

    }


    public int getId_equipement() {
        return id_equipement;
    }

    public String getNom() {
        return nom;
    }

    public String getStatut() {
        return statut;
    }

    public String getType() {
        return type;
    }

    public void setId_equipement(int id_equipement) {
        this.id_equipement = id_equipement;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Equipement{" +
                "id_equipement=" + id_equipement +
                ", nom='" + nom + '\'' +
                ", statut='" + statut + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
