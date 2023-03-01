package guru.springframework.msscbrewery.services.impl;

import guru.springframework.msscbrewery.services.BeerServiceV2;
import guru.springframework.msscbrewery.model.BeerDtoV2;
import guru.springframework.msscbrewery.model.BeerStyleEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class BeerServiceV2Impl implements BeerServiceV2 {
    @Override
    public BeerDtoV2 saveNewBeer(BeerDtoV2 beerDto) {
        return BeerDtoV2.builder()
                .id(UUID.randomUUID())
                .build();
    }

    @Override
    public void updateBeer(UUID beerId, BeerDtoV2 beerDto) {
        log.debug("Updating a beer ...");
    }

    @Override
    public void deleteById(UUID beerId) {
        log.debug("Deleting a beer ...");
    }

    @Override
    public BeerDtoV2 getBeerById(UUID beerId) {
        return BeerDtoV2.builder()
                .id(UUID.randomUUID())
                .beerName("Galaxy Cat")
                .beerStyle(BeerStyleEnum.ALE)
                .build();
    }
}
