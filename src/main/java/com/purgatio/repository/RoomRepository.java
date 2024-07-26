package com.purgatio.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.purgatio.model.Room;

public interface RoomRepository extends CrudRepository<Room, Integer>{
	List<Room> findAll();
}
