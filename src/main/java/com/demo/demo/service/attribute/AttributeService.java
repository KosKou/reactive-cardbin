package com.demo.demo.service.attribute;


import com.demo.demo.servicedto.request.AddAttributeRequest;
import com.demo.demo.servicedto.request.UpdateAttributeRequest;
import com.demo.demo.servicedto.response.AttributeResponse;
import io.reactivex.Completable;
import io.reactivex.Single;

import java.util.List;

public interface AttributeService {

    Single<Integer> addAttribute(AddAttributeRequest addAttributeRequest);

    Completable updateAttribute(UpdateAttributeRequest updateAttributeRequest);

    Single<List<AttributeResponse>> getAllAttributes(int limit, int page);

    Single<AttributeResponse> getAttributeDetail(int id);

    Completable deleteAttribute(int id, String delType);
}
