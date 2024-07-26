
package com.purgatio.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.purgatio.dto.AssigmentDTO;
import com.purgatio.dto.PunishDTO;
import com.purgatio.dto.SinDTO;
import com.purgatio.model.Player;
import com.purgatio.model.Room;
import com.purgatio.repository.PlayerRepository;
import com.purgatio.repository.RoomRepository;
import com.purgatio.service.PlayerService;
import com.purgatio.utils.SinsAssignation;
import com.purgatio.utils.WordsFilter;

@CrossOrigin
@RestController
@RequestMapping("/player")
public class PlayerController {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private PlayerService playerService;

    //POST
    
    @PostMapping("/")
    public Player createPlayer(@RequestBody Player player) {
        player.setRoom(roomRepository.findById(player.getRoom().getId()).orElseThrow(() -> new RuntimeException("Room not found")));
        return playerRepository.save(player);
    }

    @PostMapping("/{playerId}/sin")
    public boolean createSin(@PathVariable("playerId") Integer playerId, @RequestBody SinDTO sin) {
        if (WordsFilter.containsBannedWords(sin.getSin())) {
            return false; 
        }

        playerService.createSin(playerId, sin);
        return true;
    }
    
    @PostMapping("/{playerId}/punish")
    public boolean createPunish(@PathVariable("playerId") Integer playerId, @RequestBody PunishDTO punish) {
        if (WordsFilter.containsBannedWords(punish.getPunish())) {
            return false;
        }
        playerService.createPunish(playerId, punish);
        return true;
    }
    
    @PostMapping("/join")
    public Player joinRoom(@RequestBody Player player) {
    	Room room = roomRepository.findById(player.getRoom().getId()).orElseThrow(() -> new RuntimeException("Room not found"));
    	player.setRoom(room);
    	return playerRepository.save(player);
    }
    
    //GET
    
    @GetMapping("/room/{roomId}")
    public List<Player> getPlayersByRoomId(@PathVariable Integer roomId) {
        return playerRepository.findByRoomId(roomId);
    }
    
    @GetMapping("/nosin/{roomId}")
    public List<Player> findPlayersWithoutSin(@PathVariable Integer roomId) {
        return playerRepository.findPlayersWithoutSin(roomId);
    }
    
    @GetMapping("/nopunish/{roomId}")
    public List<Player> findPlayersWithoutPunish(@PathVariable Integer roomId) {
        return playerRepository.findPlayersWithoutPunish(roomId);
    }

    @GetMapping("/assign/{roomId}")
    public List<Player> getAssignations(@PathVariable Integer roomId){
    	return playerRepository.findByRoomId(roomId);
    }
    
    @GetMapping("/novoting/{roomId}")
    public List<Player> findPlayersWithoutVoting(@PathVariable Integer roomId){
    	return playerRepository.findPlayersWithoutVoting(roomId);    
    }
    
    // Devuelve los jugadores que deben realizar el castigo segun el reglamento
    @GetMapping("/gameover/{roomId}")
    public List<Player> findVotedPlayers(@PathVariable Integer roomId){
    	return playerRepository.findVotedPlayers(roomId);    
    }
    
    
    //DELETE
    
    @DeleteMapping("/{playerId}")
    public void deletePlayer(@PathVariable("playerId") Integer id) {
        Player player = playerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Player not found"));
        playerRepository.delete(player);
    }
    
    @DeleteMapping("/{playerId}/sin")
    public void deleteSin(@PathVariable("playerId") Integer playerId) {
        Player player = playerRepository.findById(playerId).orElse(null);

        if (player != null) {
            player.setSin(null); 
            playerRepository.save(player);
        }
    }
    
    @DeleteMapping("/{playerId}/punish")
    public void deletePunish(@PathVariable("playerId") Integer playerId) {
        Player player = playerRepository.findById(playerId).orElse(null);

        if (player != null) {
            player.setPunish(null); 
            playerRepository.save(player);
        }
    }

    //UPDATE
    
    //    Asigna que pecado ha de juzgar a cada jugador y guarda en la BD el id de del jugador a juzgar, el admin de la sala ya recibira las asignaciones con este metodo, los demas jugadores para obtener las asignaciones es en el metodo getAssignations
    @PutMapping("/assign/{roomId}")
    public List<Player> assignSins(@PathVariable Integer roomId) {
    	List<Player> players = playerRepository.findByRoomId(roomId);
    	long seed = System.currentTimeMillis();
        List<AssigmentDTO> assignations = SinsAssignation.players(players, seed);
        System.out.println("oooooooooooooooooooooooooooooooooooooooooooooooo");
        for(AssigmentDTO ass : assignations) {
        	Player aux = new Player();
        	aux = ass.getDestinatario();
        	aux.setJudgeSin(ass.getAutor().getId());
//        	Comprobar que tiene aux en los demas campos porque al updatear se borran los demas campos en la BD
        	  System.out.println("Contenido de aux antes de guardar:");
              System.out.println("ID: " + aux.getId());
              System.out.println("IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIID a JUZGAR: " + aux.getJudgeSin());

        	playerRepository.save(aux);
        }
        List<Player> players2 = playerRepository.findByRoomId(roomId);
        return players2;
    }
    
  //No se usa pero estaria bien que funcionara en vez de updatear con un post (createSin)
    @PutMapping("/{playerId}/sin")
    public void editSin(@PathVariable("playerId") Integer playerId, @RequestBody String sin) {
    	Player player = playerRepository.findById(playerId).orElseThrow(() -> new RuntimeException(""));
    	player.setSin(sin);
    	playerRepository.save(player);
    }
    
    @PutMapping("/{playerId}/votes")
    public void incrementVotes(@PathVariable("playerId") Integer playerId) {
    	Player player = playerRepository.findById(playerId).orElseThrow(() -> new RuntimeException(""));
    	player.setVotes(player.getVotes()+1);
    	playerRepository.save(player);
    }
  
    @PutMapping("/{playerId}/hasvoted")
    public void setIVoted(@PathVariable("playerId") Integer playerId) {
    	Player player = playerRepository.findById(playerId).orElseThrow(() -> new RuntimeException(""));
    	player.setVoted(true);
    	playerRepository.save(player);
    }


}