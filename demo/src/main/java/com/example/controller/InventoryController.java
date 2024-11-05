package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import com.example.entity.InventoryEntity;
import com.example.service.InventoryService;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @GetMapping("/{id}")
    public InventoryEntity getInventory(@PathVariable Long id) {
        return inventoryService.getInventory(id);
    }

    @GetMapping
    public Page<InventoryEntity> listInventory(Pageable pageable) {
        return inventoryService.listInventory(pageable);
    }

    @PostMapping
    public InventoryEntity saveInventory(@RequestBody InventoryEntity inventory) {
        return inventoryService.saveInventory(inventory);
    }

    @PutMapping("/{id}")
    public InventoryEntity updateInventory(@PathVariable Long id, @RequestBody InventoryEntity inventory) {
        return inventoryService.updateInventory(id, inventory);
    }

    @DeleteMapping("/{id}")
    public void deleteInventory(@PathVariable Long id) {
        inventoryService.deleteInventory(id);
    }
}
