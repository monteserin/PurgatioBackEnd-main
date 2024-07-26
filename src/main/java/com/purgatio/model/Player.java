package com.purgatio.model;


import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(length = 20)
    private String playerName;
    
    @Column(length = 300)
    private String sin;
    
    private int avatarId;
    
    private int judgeSin;
    
    @Column(length = 300)
    private String punish;
    
    // Cuanta gente ha acertado tu pecado
    private int votes;
    
    // Guarda si el jugador ya ha votado 
    private boolean voted = false;
    
    @JsonBackReference
    @ManyToOne
    private Room room;
    
    

}