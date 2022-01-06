package com.alassaneniang.msscbeerservice.web.controller;

import com.alassaneniang.msscbeerservice.services.BeerService;
import com.alassaneniang.msscbeerservice.web.model.BeerDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping( "/api/v1/beer" )
public class BeerController {

    private final BeerService beerService;

    @GetMapping( { "/{beerId}" } )
    public ResponseEntity<BeerDTO> getBeerById ( @PathVariable( "beerId" ) UUID beerId ) {
        return new ResponseEntity<>( beerService.getBeerById( beerId ), HttpStatus.OK );
    }

    @PostMapping
    public ResponseEntity<BeerDTO> saveNewBeer ( @Valid @RequestBody BeerDTO beerDTO ) {
        return new ResponseEntity<>( beerService.saveNewBeer( beerDTO ), HttpStatus.CREATED );
    }

    @PutMapping( { "/{beerId}" } )
    public ResponseEntity<BeerDTO> updateBeerById ( @PathVariable( "beerId" ) UUID beerId, @Valid @RequestBody BeerDTO beerDTO ) {
        return new ResponseEntity<>( beerService.updateBeerById( beerId, beerDTO ), HttpStatus.NO_CONTENT );
    }

}
