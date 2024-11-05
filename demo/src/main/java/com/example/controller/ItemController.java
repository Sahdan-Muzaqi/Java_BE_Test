package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.example.dto.ItemDTO;
import com.example.entity.ItemEntity;
import com.example.service.ItemService;

@RestController
@RequestMapping("/api/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping("/{id}")
    public ItemEntity getItem(@PathVariable Long id) {
        return itemService.getItem(id);
    }

    @GetMapping
    public Page<ItemEntity> listItems(Pageable pageable) {
        return itemService.listItems(pageable);
    }

    @GetMapping("/stock/{id}")
    public ItemDTO getItemStock(@PathVariable Long id) {
        return itemService.getRemainingStock(id);
    }

    @PostMapping
    public ItemEntity saveItem(@RequestBody ItemEntity item) {
        return itemService.saveItem(item);
    }

    @PutMapping("/{id}")
    public ItemEntity updateItem(@PathVariable Long id, @RequestBody ItemEntity item) {
        return itemService.updateItem(id, item);
    }

    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable Long id) {
        itemService.deleteItem(id);
    }
}
