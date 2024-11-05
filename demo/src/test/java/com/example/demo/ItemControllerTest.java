package com.example.demo;

import com.example.controller.ItemController;
import com.example.dto.ItemDTO;
import com.example.entity.ItemEntity;
import com.example.service.ItemService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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

@WebMvcTest(ItemController.class)
public class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemService itemService;

    @Test
    public void testGetItem() throws Exception {
        Long itemId = 1L;
        ItemEntity item = new ItemEntity();
        item.setId(itemId);
        when(itemService.getItem(itemId)).thenReturn(item);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/item/{id}", itemId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(itemId));

        verify(itemService, times(1)).getItem(itemId);
    }

    @Test
    public void testListItems() throws Exception {
        Pageable pageable = PageRequest.of(0, 10);
        Page<ItemEntity> page = new PageImpl<>(Collections.singletonList(new ItemEntity()));
        when(itemService.listItems(pageable)).thenReturn(page);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/item"))
                .andExpect(status().isOk());

        verify(itemService, times(1)).listItems(any(Pageable.class));
    }

    @Test
    public void testGetItemStock() throws Exception {
        Long itemId = 1L;
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setItemId(itemId);
        when(itemService.getRemainingStock(itemId)).thenReturn(itemDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/item/stock/{id}", itemId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.itemId").value(itemId));

        verify(itemService, times(1)).getRemainingStock(itemId);
    }

    @Test
    public void testSaveItem() throws Exception {
        ItemEntity item = new ItemEntity();
        item.setId(1L);
        when(itemService.saveItem(any(ItemEntity.class))).thenReturn(item);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/item")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1, \"name\":\"ItemName\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));

        verify(itemService, times(1)).saveItem(any(ItemEntity.class));
    }

    @Test
    public void testUpdateItem() throws Exception {
        Long itemId = 1L;
        ItemEntity item = new ItemEntity();
        item.setId(itemId);
        when(itemService.updateItem(eq(itemId), any(ItemEntity.class))).thenReturn(item);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/item/{id}", itemId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1, \"name\":\"UpdatedItem\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(itemId));

        verify(itemService, times(1)).updateItem(eq(itemId), any(ItemEntity.class));
    }

    @Test
    public void testDeleteItem() throws Exception {
        Long itemId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/item/{id}", itemId))
                .andExpect(status().isOk());

        verify(itemService, times(1)).deleteItem(itemId);
    }
}
