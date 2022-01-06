package com.alassaneniang.msscbeerservice.services;

import com.alassaneniang.msscbeerservice.web.model.BeerDTO;

import java.util.UUID;

public interface BeerService {

    BeerDTO getBeerById ( UUID beerId );

    BeerDTO saveNewBeer ( BeerDTO beerDTO );

    BeerDTO updateBeerById ( UUID beerId, BeerDTO beerDTO );

}
