package com.alassaneniang.msscbeerservice.web.controller;

import com.alassaneniang.msscbeerservice.web.model.BeerDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest( BeerController.class )
class BeerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getBeerById () throws Exception {
        mockMvc
                .perform(
                        get( "/api/v1/beer/" + UUID.randomUUID().toString() )
                                .accept( APPLICATION_JSON )
                )
                .andExpect( status().isOk() );
    }

    @Test
    void saveNewBeer () throws Exception {
        BeerDTO beerDTO = BeerDTO.builder().build();
        String beerDTOJSON = objectMapper.writeValueAsString( beerDTO );
        mockMvc
                .perform(
                        post( "/api/v1/beer/" )
                                .contentType( APPLICATION_JSON )
                                .content( beerDTOJSON )
                )
                .andExpect( status().isCreated() );
    }

    @Test
    void updateBeerById () throws Exception {
        BeerDTO beerDTO = BeerDTO.builder().build();
        String beerDTOJSON = objectMapper.writeValueAsString( beerDTO );
        mockMvc
                .perform(
                        put( "/api/v1/beer/" + UUID.randomUUID().toString() )
                                .contentType( APPLICATION_JSON )
                                .content( beerDTOJSON )
                )
                .andExpect( status().isNoContent() );

    }

}