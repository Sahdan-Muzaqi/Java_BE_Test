package com.example.service;

import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.entity.InventoryEntity;
import com.example.entity.ItemEntity;
import com.example.repository.InventoryRepository;
import com.example.utility.InsufficientStockException;
import com.example.utility.ResourceNotFoundException;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    // Calculate stock based on inventory entries
    public int calculateStock(Long itemId) {
        return inventoryRepository.findByItemId(itemId)
                                  .stream()
                                  //.mapToInt(inv -> "T".equals(inv.getType()) ? inv.getQty() : -inv.getQty())
                                  .mapToInt(inv -> inv.getQty())
                                  .sum();
    }
    
    public int calculateStock2(Long id) {
        return inventoryRepository.findById(id)
                                  .get()
                                  .getQty();
    }

    // CRUD methods for Inventory
    public InventoryEntity getInventory(Long id) {
        return inventoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Inventory not found"));
    }

    public Page<InventoryEntity> listInventory(Pageable pageable) {
        return inventoryRepository.findAll(pageable);
    }

    @Transactional
    public InventoryEntity saveInventory(InventoryEntity inventory) {
    	InventoryEntity temp = inventoryRepository.getById(inventory.getId());
    	if (temp != null) {
    		int currentStock = calculateStock2(inventory.getItemId());
    		System.out.println("TEST 4: " + currentStock);
    		if ("W".equals(inventory.getType())) {
                if (currentStock < inventory.getQty()) {
                    throw new InsufficientStockException("Insufficient stock for withdrawal");
                } else {
                	inventory.setQty(currentStock - inventory.getQty());
                }
            } else if ("T".equals(inventory.getType())){
            	inventory.setQty(currentStock + inventory.getQty());
            }
    		return updateInventory(inventory.getId(), inventory);
    	} else {
    		return inventoryRepository.save(inventory);
    	}
    }
    
    @Transactional
    public InventoryEntity saveInventory2(InventoryEntity inventory) {
		return inventoryRepository.save(inventory);
    }

    @Transactional
    public InventoryEntity updateInventory(Long id, InventoryEntity updatedInventory) {
        InventoryEntity inventory = getInventory(id);
        inventory.setQty(updatedInventory.getQty());
        inventory.setType(updatedInventory.getType());
        inventory.setItemId(updatedInventory.getItemId());
        return inventoryRepository.save(inventory);
    }

    @Transactional
    public void deleteInventory(Long id) {
        inventoryRepository.deleteById(id);
    }
}
