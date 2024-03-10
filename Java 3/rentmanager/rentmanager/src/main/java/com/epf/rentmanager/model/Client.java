package com.epf.rentmanager.model;

import java.time.LocalDate;
public class Client {
    private String nom;
    private String prenom;
    private String email;
    static LocalDate DATETIME;

    private Client(String nom,String prenom,String email,LocalDate DATETIME){
        this.nom=nom;
        this.prenom=prenom;
        this.email=email;
        this.DATETIME=DATETIME;
    }
    private String  getNom(){
        return nom;
    }
    private String getPrenom(){
        return prenom;
    }
    private String getEmail(){
        return email;
    }
    private LocalDate getDATETIME(){
        return DATETIME;
    }

}
