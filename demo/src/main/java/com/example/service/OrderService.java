package com.example.service;

import java.util.Iterator;
import java.util.List;

import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.entity.InventoryEntity;
import com.example.entity.OrderEntity;
import com.example.repository.InventoryRepository;
import com.example.repository.OrderRepository;
import com.example.utility.InsufficientStockException;
import com.example.utility.ResourceNotFoundException;

@Service
public class OrderService {

    @Autowired
    @Lazy
    private OrderRepository orderRepository;
    
    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    @Lazy
    private InventoryService inventoryService;

    @Transactional
    public OrderEntity saveOrder(OrderEntity order) {
        int stock = inventoryService.calculateStock(order.getItemId());

        System.out.println("TEST 1: " + stock);
        System.out.println("TEST 2: " + order.getQty());
        if (stock < order.getQty()) {
            throw new InsufficientStockException("Insufficient stock to fulfill order");
        }

        ///////////////////////////////////////////////////////
        int remaining = order.getQty();
        List<InventoryEntity> invList = inventoryRepository.findByItemId(order.getItemId());
        for (InventoryEntity inventoryEntity : invList) {
        	if (remaining > 0) {
        		if (inventoryEntity.getQty() == remaining) {
            		remaining = 0;
            		inventoryEntity.setQty(0);
            	} else if (inventoryEntity.getQty() > remaining) {
            		remaining = inventoryEntity.getQty() - remaining;
            		inventoryEntity.setQty(remaining);
            	}
        		System.out.println("TEST 3: " + remaining);
            	inventoryEntity.setType("W");
                inventoryService.saveInventory2(inventoryEntity);
        	}
		}
        ///////////////////////////////////////////////////////

        // Find the current largest orderNo and increment
        int largestOrderNo = orderRepository.findMaxOrderNo();
        int nextOrderNo = 1;

        if (largestOrderNo > 0) {
            nextOrderNo = largestOrderNo + 1;
            System.out.println(nextOrderNo);
        }

        // Set the new orderNo with prefix "O"
        order.setOrderNo("O" + nextOrderNo);
        
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderNo(order.getOrderNo());
        orderEntity.setItemId(order.getItemId());
        orderEntity.setQty(order.getQty());
        orderEntity.setPrice(order.getPrice());
        
        return orderRepository.save(orderEntity);
    }

    // Other CRUD methods for Order
    public OrderEntity getOrder(String orderNo) {
        return orderRepository.findByOrderNo(orderNo);//.orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }

    public Page<OrderEntity> listOrders(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    public OrderEntity updateOrder(String id, OrderEntity updatedOrder) {
        OrderEntity order = getOrder(id);
        order.setQty(updatedOrder.getQty());
        order.setPrice(updatedOrder.getPrice());
        order.setItemId(updatedOrder.getItemId());
        return orderRepository.save(order);
    }

    @Transactional
    public void deleteOrder(String id) {
        orderRepository.deleteByOrderNo(id);
    }
}
