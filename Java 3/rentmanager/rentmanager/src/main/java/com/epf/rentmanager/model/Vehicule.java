package com.epf.rentmanager.model;

public class Vehicule {
    private int id;
    private String constructeur;
    private String modele;
    private int nb_places;

    private Vehicule(int id,String constructeur,String modele, int nb_places){
        this.id=id;
        this.constructeur=constructeur;
        this.modele=modele;
        this.nb_places=nb_places;
    }
    public int getId(){
        return id;
    }
    public String getConstructeur(){
        return constructeur;
    }
    public String getModele(){
        return modele;
    }
    public int getNb_places(){
        return nb_places;
    }
}

