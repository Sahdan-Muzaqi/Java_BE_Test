package com.example.demo;

import com.example.controller.InventoryController;
import com.example.entity.InventoryEntity;
import com.example.service.InventoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(InventoryController.class)
public class InventoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InventoryService inventoryService;

    @Test
    public void testGetInventory() throws Exception {
        Long inventoryId = 1L;
        InventoryEntity inventory = new InventoryEntity();
        inventory.setId(inventoryId);
        when(inventoryService.getInventory(inventoryId)).thenReturn(inventory);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/inventory/{id}", inventoryId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(inventoryId));

        verify(inventoryService, times(1)).getInventory(inventoryId);
    }

    @Test
    public void testListInventory() throws Exception {
        Pageable pageable = PageRequest.of(0, 10);
        Page<InventoryEntity> page = new PageImpl<>(Collections.singletonList(new InventoryEntity()));
        when(inventoryService.listInventory(pageable)).thenReturn(page);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/inventory"))
                .andExpect(status().isOk());

        verify(inventoryService, times(1)).listInventory(any(Pageable.class));
    }

    @Test
    public void testSaveInventory() throws Exception {
        InventoryEntity inventory = new InventoryEntity();
        inventory.setId(1L);
        when(inventoryService.saveInventory(any(InventoryEntity.class))).thenReturn(inventory);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/inventory")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1, \"name\":\"InventoryItem\", \"quantity\":100}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));

        verify(inventoryService, times(1)).saveInventory(any(InventoryEntity.class));
    }

    @Test
    public void testUpdateInventory() throws Exception {
        Long inventoryId = 1L;
        InventoryEntity inventory = new InventoryEntity();
        inventory.setId(inventoryId);
        when(inventoryService.updateInventory(eq(inventoryId), any(InventoryEntity.class))).thenReturn(inventory);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/inventory/{id}", inventoryId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1, \"name\":\"UpdatedItem\", \"quantity\":150}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(inventoryId));

        verify(inventoryService, times(1)).updateInventory(eq(inventoryId), any(InventoryEntity.class));
    }

    @Test
    public void testDeleteInventory() throws Exception {
        Long inventoryId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/inventory/{id}", inventoryId))
                .andExpect(status().isOk());

        verify(inventoryService, times(1)).deleteInventory(inventoryId);
    }
}
