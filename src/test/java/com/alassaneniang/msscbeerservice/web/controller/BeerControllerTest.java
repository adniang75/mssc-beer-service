package com.alassaneniang.msscbeerservice.web.controller;

import com.alassaneniang.msscbeerservice.domain.Beer;
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
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/* change static import from springframework.test.web.servlet.request.MockMvcRequestBuilders.*
 to org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.* */

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
        given( beerRepository.findById( any() ) )
                .willReturn( Optional.of( Beer.builder().build() ) );

        mockMvc
                .perform(
                        get( "/api/v1/beer/{beerId}", UUID.randomUUID() )
                                .param( "isCold", "yes" )
                                .accept( APPLICATION_JSON )
                )
                .andExpect( status().isOk() )
                .andDo(
                        document(
                                "v1/beer",
                                pathParameters( // documenting path parameters
                                        parameterWithName( "beerId" ).description( "UUID of desired beer to get" )
                                ),
                                requestParameters(
                                        parameterWithName( "isCold" ).description( "Is beer cold query parameter" )
                                ),
                                responseFields(
                                        fieldWithPath( "id" ).description( "Id of the beer" ),
                                        fieldWithPath( "version" ).description( "Version number" ),
                                        fieldWithPath( "createdDate" ).description( "Date created" ),
                                        fieldWithPath( "lastModifiedDate" ).description( "Date updated" ),
                                        fieldWithPath( "beerName" ).description( "Beer name" ),
                                        fieldWithPath( "beerStyle" ).description( "Beer style" ),
                                        fieldWithPath( "upc" ).description( "UPC of beer" ),
                                        fieldWithPath( "price" ).description( "Beer price" ),
                                        fieldWithPath( "quantityOnHand" ).description( "Quantity on hand" )
                                )
                        )
                );
    }

    @Test
    void saveNewBeer () throws Exception {
        BeerDTO beerDTO = getValidBeerDTO();
        String beerDTOJSON = objectMapper.writeValueAsString( beerDTO );
        ConstrainedFields fields = new ConstrainedFields( BeerDTO.class );
        mockMvc
                .perform(
                        post( "/api/v1/beer/" )
                                .contentType( APPLICATION_JSON )
                                .content( beerDTOJSON )
                )
                .andExpect( status().isCreated() )
                .andDo(
                        document(
                                "v1/beer",
                                requestFields(
                                        fields.withPath( "id" ).ignored(),
                                        fields.withPath( "version" ).ignored(),
                                        fields.withPath( "createdDate" ).ignored(),
                                        fields.withPath( "lastModifiedDate" ).ignored(),
                                        fields.withPath( "beerName" ).description( "Beer name" ),
                                        fields.withPath( "beerStyle" ).description( "Beer style" ),
                                        fields.withPath( "upc" ).description( "UPC of beer" ).attributes(),
                                        fields.withPath( "price" ).description( "Beer price" ),
                                        fields.withPath( "quantityOnHand" ).ignored()
                                )
                        )
                );
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

    @SuppressWarnings( "unused" )
    private static class ConstrainedFields {

        private final ConstraintDescriptions constraintDescriptions;

        ConstrainedFields ( Class<?> input ) {
            this.constraintDescriptions = new ConstraintDescriptions( input );
        }

        private FieldDescriptor withPath ( String path ) {
            return fieldWithPath( path )
                    .attributes(
                            key( "constraints" )
                                    .value( StringUtils
                                            .collectionToDelimitedString( this.constraintDescriptions
                                                    .descriptionsForProperty( path ), ". " )
                                    )
                    );
        }
    }

}