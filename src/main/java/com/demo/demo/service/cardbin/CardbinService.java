package com.demo.demo.service.cardbin;

import com.demo.demo.servicedto.request.AddCardbinRequest;
import io.reactivex.Single;

public interface CardbinService {
    Single<Integer> addCardbin(AddCardbinRequest addCardbinRequest);
}
