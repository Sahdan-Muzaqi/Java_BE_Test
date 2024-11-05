package com.example.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.entity.InventoryEntity;
import com.example.entity.OrderEntity;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
	OrderEntity findByOrderNo(String orderNo); // Query to find by orderId
	void deleteByOrderNo(String orderNo); // Query to delete by orderId
	
	@Query("SELECT COALESCE(MAX(CAST(SUBSTRING(o.orderNo, 2) AS int)), 0) FROM OrderEntity o WHERE o.orderNo LIKE 'O%'")
	Integer findMaxOrderNo();
	
}