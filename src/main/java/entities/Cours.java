package entities;

public class Cours {
    private static int id_cours;
    private String titre;
    private String Contenu;

    public Cours() {
    }

    public Cours(int id_cours, String titre, String contenu) {
        this.id_cours = id_cours;
        this.titre = titre;
        Contenu = contenu;
    }

    public Cours(String titre, String contenu) {
        this.titre = titre;
        Contenu = contenu;
    }

    public static int getId_cours() {
        return id_cours;
    }

    public String getTitre() {
        return titre;
    }

    public String getContenu() {
        return Contenu;
    }

    public void setId_cours(int id_cours) {
        this.id_cours = id_cours;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setContenu(String contenu) {
        Contenu = contenu;
    }

    @Override
    public String toString() {
        return "Cours{" +
                "id_cours=" + id_cours +
                ", titre='" + titre + '\'' +
                ", Contenu='" + Contenu + '\'' +
                '}';
    }
}
