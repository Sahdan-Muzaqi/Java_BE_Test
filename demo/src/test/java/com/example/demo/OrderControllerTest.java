package com.example.demo;

import com.example.controller.OrderController;
import com.example.entity.OrderEntity;
import com.example.service.OrderService;
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

@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Test
    public void testGetOrder() throws Exception {
        String orderId = "O1";
        OrderEntity order = new OrderEntity();
        order.setOrderNo(orderId);
        when(orderService.getOrder(orderId)).thenReturn(order);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/order/{id}", orderId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderNo").value(orderId));

        verify(orderService, times(1)).getOrder(orderId);
    }

    @Test
    public void testListOrders() throws Exception {
        Pageable pageable = PageRequest.of(0, 10);
        Page<OrderEntity> page = new PageImpl<>(Collections.singletonList(new OrderEntity()));
        when(orderService.listOrders(pageable)).thenReturn(page);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/order"))
                .andExpect(status().isOk());

        verify(orderService, times(1)).listOrders(any(Pageable.class));
    }

    @Test
    public void testSaveOrder() throws Exception {
        OrderEntity order = new OrderEntity();
        order.setOrderNo("O1");
        when(orderService.saveOrder(any(OrderEntity.class))).thenReturn(order);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"orderNo\":\"1\", \"itemId\":1, \"quantity\":5}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderNo").value("O1"));

        verify(orderService, times(1)).saveOrder(any(OrderEntity.class));
    }

    @Test
    public void testUpdateOrder() throws Exception {
        String orderId = "O1";
        OrderEntity order = new OrderEntity();
        order.setOrderNo(orderId);
        when(orderService.updateOrder(eq(orderId), any(OrderEntity.class))).thenReturn(order);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/order/{id}", orderId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"orderNo\":\"1\", \"itemId\":1, \"quantity\":10}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderNo").value(orderId));

        verify(orderService, times(1)).updateOrder(eq(orderId), any(OrderEntity.class));
    }

    @Test
    public void testDeleteOrder() throws Exception {
        String orderId = "O1";

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/order/{id}", orderId))
                .andExpect(status().isOk());

        verify(orderService, times(1)).deleteOrder(orderId);
    }
}
