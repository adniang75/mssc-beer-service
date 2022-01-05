package com.alassaneniang.msscbeerservice.web.controller;

import com.alassaneniang.msscbeerservice.repositories.BeerRepository;
import com.alassaneniang.msscbeerservice.web.model.BeerDTO;
import com.alassaneniang.msscbeerservice.web.model.BeerStyleEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith( RestDocumentationExtension.class )
@AutoConfigureRestDocs
@WebMvcTest( BeerController.class )
@ComponentScan( basePackages = "com.alassaneniang.msscbeerservice.web.mappers" )
class BeerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    BeerRepository beerRepository;

    @Test
    void getBeerById () throws Exception {
        mockMvc
                .perform(
                        get( "/api/v1/beer/" + UUID.randomUUID() )
                                .accept( APPLICATION_JSON )
                )
                .andExpect( status().isOk() );
    }

    @Test
    void saveNewBeer () throws Exception {
        BeerDTO beerDTO = getValidBeerDTO();
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
        BeerDTO beerDTO = getValidBeerDTO();
        String beerDTOJSON = objectMapper.writeValueAsString( beerDTO );
        mockMvc
                .perform(
                        put( "/api/v1/beer/" + UUID.randomUUID() )
                                .contentType( APPLICATION_JSON )
                                .content( beerDTOJSON )
                )
                .andExpect( status().isNoContent() );

    }

    BeerDTO getValidBeerDTO () {
        return BeerDTO.builder()
                .beerName( "My Beer" )
                .beerStyle( BeerStyleEnum.ALE )
                .price( new BigDecimal( "2.99" ) )
                .upc( 123123123123L )
                .build();
    }

}