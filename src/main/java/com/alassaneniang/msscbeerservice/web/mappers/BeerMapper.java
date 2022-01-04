package com.alassaneniang.msscbeerservice.web.mappers;

import com.alassaneniang.msscbeerservice.domain.Beer;
import com.alassaneniang.msscbeerservice.web.model.BeerDTO;
import org.mapstruct.Mapper;

@Mapper( uses = { DateMapper.class } )
public interface BeerMapper {

    BeerDTO beerToBeerDTO ( Beer beer );

    Beer beerDTOToBeer ( BeerDTO beerDTO );

}
