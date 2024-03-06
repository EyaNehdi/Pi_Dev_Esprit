package entities;

import java.util.Objects;

public class Packs {
    private int id_packs;
    private String nom;
    private String description;
    private float prix;
    private String type;


    public Packs(String nom, String description, float prix, String type) {

    }

    public Packs(int id_packs, String nom, String description, Float prix, String type) {
    }

    public Packs() {

    }

    public int getId_Pack() {
        return id_packs ;
    }

    public void setId_Pack(int id_packs) {
        this.id_packs  = id_packs;
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
        return Objects.hash(id_packs , nom, description, prix, type);
    }

    @Override
    public String toString() {
        return "packs{" +
                "id_packs='" + id_packs  + '\'' +
                "nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", prix='" + prix + '\'' +
                ", type='" + type + '\''+

                '}';
    }
}



