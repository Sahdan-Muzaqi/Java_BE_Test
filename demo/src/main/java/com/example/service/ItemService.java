package com.example.service;

import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.dto.ItemDTO;
import com.example.entity.ItemEntity;
import com.example.repository.ItemRepository;
import com.example.utility.ResourceNotFoundException;

import jakarta.transaction.Transactional;
import java.util.Optional;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    //@Lazy
    private InventoryService inventoryService;

    public ItemEntity getItem(Long id) {
        return itemRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Item not found"));
    }

    public Page<ItemEntity> listItems(Pageable pageable) {
        return itemRepository.findAll(pageable);
    }

    public ItemEntity saveItem(ItemEntity item) {
        return itemRepository.save(item);
    }

    public ItemEntity updateItem(Long id, ItemEntity updatedItem) {
        ItemEntity item = getItem(id);
        item.setName(updatedItem.getName());
        item.setPrice(updatedItem.getPrice());
        return itemRepository.save(item);
    }

    @Transactional
    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }

    // Method to get item with remaining stock
    public ItemDTO getRemainingStock(Long itemId) {
    	ItemEntity item = getItem(itemId);
    	
    	ItemDTO itemDto = new ItemDTO();
    	
    	itemDto.setItemId(item.getId());
    	itemDto.setName(item.getName());
    	itemDto.setPrice(item.getPrice());
    	itemDto.setRemainingStock(inventoryService.calculateStock(itemId));
        return itemDto;
    }
}
