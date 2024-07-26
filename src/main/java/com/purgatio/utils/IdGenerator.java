package com.purgatio.utils;

import java.util.Random;

import com.purgatio.repository.RoomRepository;

public class IdGenerator {

    public static int generateUniqueRandomId(RoomRepository roomRepository) {
        Random r = new Random(System.currentTimeMillis());
        int roomId;
        do {
            roomId = 1 + r.nextInt(1000);
        } while (roomRepository.existsById(roomId));
        return roomId;
    }
}