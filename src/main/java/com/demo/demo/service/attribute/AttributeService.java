package com.demo.demo.service.attribute;


import com.demo.demo.servicedto.request.AddAttributeRequest;
import com.demo.demo.servicedto.request.UpdateAttributeRequest;
import com.demo.demo.servicedto.response.AttributeResponse;
import com.demo.demo.webdto.request.UpdateAttributeWebRequest;
import io.reactivex.Completable;
import io.reactivex.Single;

import java.util.List;

public interface AttributeService {

    Single addAttribute(AddAttributeRequest addAttributeRequest);

    Completable updateAttribute(UpdateAttributeWebRequest updateAttributeWebRequest, Integer attributeId);

    Single<List<AttributeResponse>> getAllAttributes(int limit, int page);

    Single<AttributeResponse> getAttributeDetail(int id);

    Completable deleteAttribute(int id, String delType);
}
