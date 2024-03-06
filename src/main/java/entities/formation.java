package entities;


public class formation {

    private int id ;
    private String nom ;
    private String  date ;
    public formation (){

    }

    public formation(int id, String nom, String date) {
        this.id = id;
        this.nom = nom;
        this.date = date;
    }

    public formation(String nom, String date) {
        this.nom = nom;
        this.date = date;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "event{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", date=" + date +
                '}';
    }
}
