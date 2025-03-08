package com.example.tp3;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class User {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String login;
    public String password;
    public String nom;
    public String prenom;
    public String dateNaissance;
    public String telephone;
    public String email;
    public String centresInteret;

    // Constructeur
    public User(String login, String password, String nom, String prenom, String dateNaissance, String telephone, String email, String centresInteret) {
        this.login = login;
        this.password = password;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.telephone = telephone;
        this.email = email;
        this.centresInteret = centresInteret;
    }
}