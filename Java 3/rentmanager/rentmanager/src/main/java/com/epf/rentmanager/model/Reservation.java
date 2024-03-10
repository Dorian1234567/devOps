package com.epf.rentmanager.model;

import java.time.LocalDate;

public class Reservation {
    private int id;
    private int client_id;
    private int vehicule_id;
    static LocalDate debut;
    static LocalDate fin;

    private Reservation(int id,int client_id,int vehicule_id,LocalDate debut,LocalDate fin){
        this.id=id;
        this.client_id=client_id;
        this.vehicule_id=vehicule_id;
        this.debut=debut;
        this.fin=fin;
    }
    public int getId(){return id;}
    public int getClient_id(){return client_id;}
    public int getVehicule_id(){return vehicule_id;}
    public LocalDate getDebut(){return debut;}
    public LocalDate getFin(){return fin;}
}
