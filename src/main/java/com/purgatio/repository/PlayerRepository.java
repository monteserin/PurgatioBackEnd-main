package com.purgatio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.purgatio.model.Player;

public interface PlayerRepository extends CrudRepository<Player, Integer>{
	List<Player> findAll();
    List<Player> findByRoomId(int roomId);

    @Query("SELECT p FROM Player p WHERE p.sin IS NULL AND p.room.id = :roomId")
    List<Player> findPlayersWithoutSin(@Param("roomId") int roomId);
    
    @Query("SELECT p FROM Player p WHERE p.punish IS NULL AND p.room.id = :roomId")
    List<Player> findPlayersWithoutPunish(@Param("roomId") int roomId);
    
    @Query("SELECT p FROM Player p WHERE p.voted = false AND p.room.id = :roomId")
    List<Player> findPlayersWithoutVoting(@Param("roomId") int roomId);
   
    
    // Devuelve de momento todos los jugadores que hayan sido votados almenos 1 vez
    @Query("SELECT p FROM Player p WHERE p.votes > 0 AND p.room.id = :roomId")
    List<Player> findVotedPlayers(@Param("roomId") int roomId);
}