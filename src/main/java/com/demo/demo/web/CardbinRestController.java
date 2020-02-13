package com.demo.demo.web;

import com.demo.demo.entity.Attribute;
import com.demo.demo.entity.Cardbin;
import com.demo.demo.service.cardbin.CardbinService;
import com.demo.demo.servicedto.request.AddCardbinRequest;
import com.demo.demo.webdto.request.UpdateCardbinWebRequest;
import com.demo.demo.webdto.response.BaseWebResponse;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cardbins")
public class CardbinRestController {

    private final CardbinService cardbinService;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Single<BaseWebResponse> addCardbin(
            @RequestBody AddCardbinRequest addCardbinRequest){
        return cardbinService.addCardbin(addCardbinRequest)
                .subscribeOn(Schedulers.io())
                .map(s -> BaseWebResponse.successNoData());
    }

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Observable<BaseWebResponse<List<Cardbin>>> getAllCardbins(){
        return cardbinService.getAllCardbins()
                .subscribeOn(Schedulers.io())
                .map(cardbins -> BaseWebResponse.successWithData(cardbins));
    }

    @GetMapping(
            value = "/{cardbinId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Single<BaseWebResponse<Cardbin>> getCardbinDetail(@PathVariable(name = "cardbinId") Integer cardbinId){
        return cardbinService.getCardbinDetail(cardbinId)
                .subscribeOn(Schedulers.io())
                .map(cardbin -> BaseWebResponse.successWithData(cardbin));
    }

    @GetMapping(
            value = "/{cardbinId}/attributes",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Observable<BaseWebResponse<List<Attribute>>> getCardinAttributes(
            @PathVariable(name = "cardbinId") Integer cardbinId){
        return cardbinService.getCardbinAttributes(cardbinId)
                .subscribeOn(Schedulers.io())
                .map(attributes -> BaseWebResponse.successWithData(attributes));
    }

    @PutMapping(
            value = "/{cardbinId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Single<BaseWebResponse<Cardbin>> updateCardbin(
            @PathVariable(value = "cardbinId") Integer cardbinId,
            @RequestBody UpdateCardbinWebRequest updateCardbinWebRequest){
        return cardbinService.updateCardbin(updateCardbinWebRequest, cardbinId)
                .subscribeOn(Schedulers.io())
                .toSingle(() -> BaseWebResponse.successNoData());
    }

    @DeleteMapping(
            value = "/{cardbinId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Single<BaseWebResponse> deleteCardbin(@PathVariable(name = "cardbinId") Integer cardbinId){
        return cardbinService.deleteCardbin(cardbinId)
                .subscribeOn(Schedulers.io())
                .toSingle(() -> BaseWebResponse.successNoData());
    }

}
