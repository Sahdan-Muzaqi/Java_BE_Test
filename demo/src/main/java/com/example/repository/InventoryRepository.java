package com.example.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.InventoryEntity;
import com.example.entity.ItemEntity;

@Repository
public interface InventoryRepository extends JpaRepository<InventoryEntity, Long> {
	List<InventoryEntity> findByItemId(Long itemId); // Query to find by itemId
}