package com.demo.demo.service.cardbin;

import com.demo.demo.entity.Attribute;
import com.demo.demo.entity.Cardbin;
import com.demo.demo.servicedto.request.AddCardbinRequest;
import com.demo.demo.servicedto.request.UpdateCardbinRequest;
import com.demo.demo.webdto.request.UpdateCardbinWebRequest;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

import java.util.List;

public interface CardbinService {
    Single<Integer> addCardbin(AddCardbinRequest addCardbinRequest);

    Observable<List<Cardbin>> getAllCardbins();

    Single<Cardbin> getCardbinDetail(Integer id);

    Observable<List<Attribute>> getCardbinAttributes(Integer id);

//    Completable updateCardbin(UpdateCardbinRequest updateCardbinRequest);
    Completable updateCardbin(UpdateCardbinWebRequest updateCardbinWebRequest, Integer cardbinId);

    Completable deleteCardbin(Integer id);
}
