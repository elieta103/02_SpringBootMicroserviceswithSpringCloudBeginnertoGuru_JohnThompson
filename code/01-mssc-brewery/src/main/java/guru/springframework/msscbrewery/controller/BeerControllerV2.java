package guru.springframework.msscbrewery.controller;

import guru.springframework.msscbrewery.services.BeerServiceV2;
import guru.springframework.msscbrewery.model.BeerDtoV2;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@Validated
@RestController
@RequestMapping("/api/v2/beer")
@RequiredArgsConstructor
public class BeerControllerV2 {

    private final BeerServiceV2 beerServiceV2;

    @GetMapping("/{beerId}")
    public ResponseEntity<BeerDtoV2> getBeer(@NotNull  @PathVariable("beerId") UUID beerId){
        return  new ResponseEntity<>(beerServiceV2.getBeerById(beerId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity handleSave (@Valid @NotNull @RequestBody BeerDtoV2 beerDto){
        log.debug("In the handle post ...");
        val savedDto = beerServiceV2.saveNewBeer(beerDto);
        val headers = new HttpHeaders();
        headers.add("Location", "/api/v2/beer/"+savedDto.getId().toString());

        return  new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @PutMapping("/{beerId}")
    public ResponseEntity handleUpdate (@PathVariable("beerId") UUID beerId,
                                        @Valid @RequestBody BeerDtoV2 beerDto){
        beerServiceV2.updateBeer(beerId, beerDto);

        return  new ResponseEntity(HttpStatus.NO_CONTENT);
    }


    @DeleteMapping("/{beerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void handleUpdate (@PathVariable("beerId") UUID beerId){
        beerServiceV2.deleteById(beerId);
    }


}
