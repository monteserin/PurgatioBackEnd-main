package com.purgatio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.purgatio.dto.PunishDTO;
import com.purgatio.dto.SinDTO;
import com.purgatio.model.Player;
import com.purgatio.repository.PlayerRepository;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    public void createSin(Integer playerId, SinDTO sinDTO) {
        Player player = playerRepository.findById(playerId).orElseThrow(() -> new RuntimeException(""));
        player.setSin(sinDTO.getSin());
        playerRepository.save(player);
    }
    
    public void createPunish(Integer playerId, PunishDTO punishDTO) {
        Player player = playerRepository.findById(playerId).orElseThrow(() -> new RuntimeException(""));
        player.setPunish(punishDTO.getPunish());
        playerRepository.save(player);
    }
}