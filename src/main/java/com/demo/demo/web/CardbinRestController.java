package com.demo.demo.web;

import com.demo.demo.service.cardbin.CardbinService;
import com.demo.demo.servicedto.request.AddCardbinRequest;
import com.demo.demo.webdto.response.BaseWebResponse;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cardbins")
public class CardbinRestController {

    private final CardbinService cardbinService;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Single<ResponseEntity<BaseWebResponse>> addCardbin(
            @RequestBody AddCardbinRequest addCardbinRequest){
        return cardbinService.addCardbin(addCardbinRequest)
                .subscribeOn(Schedulers.io())
                .map(s -> ResponseEntity
                            .created(URI.create("/api/cardbins/" + s))
                            .body(BaseWebResponse.successNoData())
                );
    }
}
