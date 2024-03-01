package entities;

import java.util.Objects;

public class Packs {
    private int id_pack;
    private String nom;
    private String description;
    private float prix;
    private String type;


    public Packs(String nom, String description, float prix, String type) {

    }

    public Packs(int id_pack, String nom, String description, Float prix, String type) {
    }

    public Packs() {

    }

    public int getId_Pack() {
        return id_pack;
    }

    public void setId_Pack(int id_pack) {
        this.id_pack = id_pack;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_pack, nom, description, prix, type);
    }

    @Override
    public String toString() {
        return "packs{" +
                "id_pack='" + id_pack + '\'' +
                "nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", prix='" + prix + '\'' +
                ", type='" + type + '\''+

                '}';
    }
}



