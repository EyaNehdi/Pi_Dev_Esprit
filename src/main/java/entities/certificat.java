package entities;


public class certificat {

    private int id;
    private String nomeleve;
    private String nomformateur;
    private String nom;
    private String date;

    public certificat() {

    }

    public certificat(int id, String nomeleve, String nomformateur, String nom, String date) {
        this.id = id;
        this.nomeleve = nomeleve;
        this.nomformateur = nomformateur;
        this.nom = nom;
        this.date = date;

    }

    public certificat(String nomeleve, String nomformateur, String nom, String date) {

        this.nomeleve = nomeleve;
        this.nomformateur = nomformateur;
        this.nom = nom;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeleve() {
        return nomeleve;
    }

    public void setNomeleve(String nomeleve) {
        this.nomeleve = nomeleve;
    }

    public String getNomformateur() {
        return nomformateur;
    }

    public void setNomformateur(String nomformateur) {
        this.nomformateur = nomformateur;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "certificat{" +
                "id=" + id +
                ", nomeleve='" + nomeleve + '\'' +
                ", nomformateur='" + nomformateur + '\'' +
                ", nom='" + nom + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}