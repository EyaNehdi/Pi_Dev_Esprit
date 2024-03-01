package services;

import entities.Packs;


import java.util.List;

public interface Ipacks {
    boolean add (Packs packs);
    Packs trouverpacksParId(int id_packs);
    List<Packs> listerTousLespacks();
    boolean mettreAJourpacks(Packs packs);
    boolean delete (int id_packs);
}
