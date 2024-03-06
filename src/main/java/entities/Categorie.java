package entities;

public class Categorie {
     private  int id_categorie ;
     private  int numSerie ;
     private String description ;

     public Categorie() {
     }

     public Categorie(int id_categorie, int numSerie, String description) {
          this.id_categorie = id_categorie;
          this.numSerie = numSerie;
          this.description = description;
     }

     public Categorie(int numSerie, String description) {
          this.numSerie = numSerie;
          this.description = description;
     }




     public  int getId_categorie() {
          return id_categorie;
     }

     public int getNumSerie() {
          return numSerie;
     }

     public String getDescription() {
          return description;
     }

     public void setId_categorie(int id_categorie) {
          this.id_categorie = id_categorie;
     }

     public void setNumSerie(int numSerie) {
          this.numSerie = numSerie;
     }

     public void setDescription(String description) {
          this.description = description;
     }
}
