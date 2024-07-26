package com.purgatio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.purgatio.model.Room;
import com.purgatio.repository.RoomRepository;
import com.purgatio.utils.IdGenerator;

@CrossOrigin
@RestController
@RequestMapping("/room")
public class RoomController {

    @Autowired
    private RoomRepository roomRepository;
    
    	//GET
    
    @GetMapping("/{id}")			
    public Room getRoomById(@PathVariable("id") Integer id) {
    	return roomRepository.findById(id)
    			.orElseThrow(() -> new RuntimeException("Habitación no encontrada con id: " + id));
    }
    
    @GetMapping("/{id}/gameStatus")
    public ResponseEntity<Boolean> getGameStatus(@PathVariable int id) {
        return roomRepository.findById(id)
                .map(room -> ResponseEntity.ok(room.isGameStarted()))
                .orElse(ResponseEntity.notFound().build());
    }
    
    	//POST
    	//saijdhasdsgi
    
    @PostMapping("/")
    public Room createRoom(@RequestBody Room room) {
        int roomId = IdGenerator.generateUniqueRandomId(roomRepository);
        room.setId(roomId);
        roomRepository.save(room);
        return room;
    }
    
    
    @PostMapping("/{id}/gameStatus")
    public ResponseEntity<Object> updateGameStatus(@PathVariable int id) {
        return roomRepository.findById(id)
                .map(room -> {
                    room.setGameStarted(true); // Establece gameStarted en true
                    roomRepository.save(room);
                    return ResponseEntity.ok().build(); // Respuesta 200 OK sin cuerpo
                })
                .orElse(ResponseEntity.notFound().build()); // Respuesta 404 Not Found si la habitación no existe
    }
    
    	//DELETE
    
    @DeleteMapping("/{id}")
    public void deleteGame(@PathVariable("id") Integer id) {
    	Room room = new Room();
    	room.setId(id);
    	roomRepository.delete(room);
    }
        
}