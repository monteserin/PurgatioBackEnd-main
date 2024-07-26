package com.purgatio.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.purgatio.dto.AssigmentDTO;
import com.purgatio.model.Player;

public class SinsAssignation {

    public static List<AssigmentDTO> players(List<Player> players, long seed) {
        // Mezcla aleatoriamente la lista de jugadores
        Collections.shuffle(players, new Random(seed));
        // Lista para almacenar las asignaciones
        List<AssigmentDTO> asignaciones = new ArrayList<>();
        // Lista auxiliar para manejar destinatarios disponibles
        List<Player> destinatariosAux = new ArrayList<>(players);
        
        Random random = new Random(seed);
        
        // Para cada jugador en la lista
        for (Player autor : players) {
            // Crear una nueva asignación
            AssigmentDTO asignacion = new AssigmentDTO();
            // Establecer el autor de la asignación
            asignacion.setAutor(autor);

            // Seleccionar un destinatario aleatorio que no sea el autor
            Player destinatario = null;
            do {
                destinatario = destinatariosAux.get(random.nextInt(destinatariosAux.size()));
            } while (destinatario.getId() == autor.getId());

            // Establecer el destinatario de la asignación
            asignacion.setDestinatario(destinatario);
            
            // Agregar la asignación a la lista
            asignaciones.add(asignacion);
            // Eliminar el destinatario de la lista auxiliar para que no se le pueda asignar nuevamente
            destinatariosAux.remove(destinatario);
        }

        // Devolver la lista de asignaciones
        return asignaciones;
    }
}