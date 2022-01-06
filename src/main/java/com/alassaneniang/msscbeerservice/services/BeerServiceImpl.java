package com.alassaneniang.msscbeerservice.services;

import com.alassaneniang.msscbeerservice.domain.Beer;
import com.alassaneniang.msscbeerservice.repositories.BeerRepository;
import com.alassaneniang.msscbeerservice.web.controller.NotFoundException;
import com.alassaneniang.msscbeerservice.web.mappers.BeerMapper;
import com.alassaneniang.msscbeerservice.web.model.BeerDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class BeerServiceImpl implements BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @Override
    public BeerDTO getBeerById ( UUID beerId ) {
        return beerMapper.beerToBeerDTO(
                beerRepository
                        .findById( beerId )
                        .orElseThrow( NotFoundException::new )
        );
    }

    @Override
    public BeerDTO saveNewBeer ( BeerDTO beerDTO ) {
        return beerMapper.beerToBeerDTO(
                beerRepository.save(
                        beerMapper.beerDTOToBeer( beerDTO )
                )
        );
    }

    @Override
    public BeerDTO updateBeerById ( UUID beerId, BeerDTO beerDTO ) {
        Beer beer = beerRepository
                .findById( beerId )
                .orElseThrow( NotFoundException::new );

        beer.setBeerName( beerDTO.getBeerName() );
        beer.setBeerStyle( beerDTO.getBeerStyle().name() );
        beer.setPrice( beerDTO.getPrice() );
        beer.setUpc( beerDTO.getUpc() );

        return beerMapper.beerToBeerDTO( beerRepository.save( beer ) );
    }
}
