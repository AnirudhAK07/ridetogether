package com.ridetogether;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import org.springframework.http.MediaType;

@SpringBootTest
@AutoConfigureMockMvc
class TripControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void returnsFormattedDemoSettlements() throws Exception {
        mockMvc.perform(get("/api/trips/demo/settlements"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].from").value("Rahul"))
                .andExpect(jsonPath("$[0].amount").value("Rs. 7333.33"))
                .andExpect(jsonPath("$[1].to").value("Sanjay"));
    }

    @Test
    void createsATrip() throws Exception {
        mockMvc.perform(post("/api/trips")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Coorg Weekend Ride\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Coorg Weekend Ride"));
    }
}