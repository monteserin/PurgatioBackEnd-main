package com.purgatio.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Room {

    @Id
    private int id;
    
    private int gamemode;
    
    private boolean gameStarted = false;
    
    @JsonManagedReference
	@OneToMany (mappedBy = "room", cascade = {CascadeType.ALL}, orphanRemoval = true, fetch = FetchType.LAZY) 
    private List<Player> players;
    
}
