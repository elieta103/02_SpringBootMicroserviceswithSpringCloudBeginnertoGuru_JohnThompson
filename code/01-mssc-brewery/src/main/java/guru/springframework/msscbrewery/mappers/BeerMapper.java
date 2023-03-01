package guru.springframework.msscbrewery.mappers;

import guru.springframework.msscbrewery.domain.Beer;
import guru.springframework.msscbrewery.model.BeerDtoV2;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
public interface BeerMapper {
    BeerDtoV2 beerToBeerDto(Beer beer);
    Beer beerDtoToBeer(BeerDtoV2 beerDtoV2);
}
