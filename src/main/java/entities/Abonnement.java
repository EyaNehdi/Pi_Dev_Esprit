package entities;

public class Abonnement {
    private int id_abonnement;
    private String nom;
    private  String prenom;
    private  String mail;
    private int num;

    public Abonnement() {
    }

    public Abonnement(int idAbonnement, String nom, String prenom, String mail, int num) {
    }

    public Abonnement(String nom, String prenom, String mail, int  num) {
        setNom(nom);
        setPrenom(prenom);
        setMail(mail);
        setNum(num);
    }




    public  int getId_abonnement() {
        return id_abonnement;
    }


    public  void setId_abonnement(int id_abonnement) {
        this.id_abonnement = id_abonnement;
    }

    public  String getNom() {
        return nom;
    }

    public  void setNom(String nom) {
        this.nom = nom;
    }

    public  String getPrenom() {
        return prenom;
    }

    public  void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public  String getMail() {
        return mail;
    }

    public  void setMail(String mail) {
        this.mail = mail;
    }


    public  Integer getNum() {
        return Integer.valueOf(String.valueOf(num));
    }

    public  void setNum(int num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "abonnement{" +
                "id_abonnement=" + id_abonnement +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", mail='" + mail + '\'' +
                ", num='" + num + '\'' +
                '}';
    }


}

