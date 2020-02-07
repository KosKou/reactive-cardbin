package com.demo.demo.web;

import com.demo.demo.service.attribute.AttributeService;
import com.demo.demo.servicedto.request.AddAttributeRequest;
import com.demo.demo.servicedto.request.UpdateAttributeRequest;
import com.demo.demo.servicedto.response.AttributeResponse;
import com.demo.demo.webdto.request.UpdateAttributeWebRequest;
import com.demo.demo.webdto.response.BaseWebResponse;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/attributes")
public class AttributeRestController {

    private final AttributeService attributeService;

    //CREATE

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Single<ResponseEntity<BaseWebResponse>> addAttribute(@RequestBody AddAttributeRequest addAttributeRequest){
        return attributeService.addAttribute(addAttributeRequest)
                .subscribeOn(Schedulers.io())
                .map(s -> ResponseEntity
                            .created(URI.create("/api/attributes/" + s))
                            .body(BaseWebResponse.successNoData()));
    }

    //UPDATE

    @PutMapping(
            value = "/{attributeId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Single<ResponseEntity<BaseWebResponse>> updateAttribute(@PathVariable(value = "attributeId") int attributeId,
            @RequestBody UpdateAttributeWebRequest updateAttributeWebRequest){
        return attributeService.updateAttribute(toUpdateAttributeRequest(attributeId,updateAttributeWebRequest))
                .subscribeOn(Schedulers.io())
                .toSingle(() -> ResponseEntity.ok(BaseWebResponse.successNoData()));
    }

    private UpdateAttributeRequest toUpdateAttributeRequest(int id,UpdateAttributeWebRequest updateAttributeWebRequest){
        UpdateAttributeRequest updateAttributeRequest = new UpdateAttributeRequest();
        BeanUtils.copyProperties(updateAttributeWebRequest, updateAttributeRequest);
        updateAttributeRequest.setId(id);
        return updateAttributeRequest;
    }

    //RETRIEVE

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Single<ResponseEntity<BaseWebResponse<List<AttributeResponse>>>> getAllAttributes(
            @RequestParam(value = "limit", defaultValue = "5") int limit,
            @RequestParam(value = "page", defaultValue = "0") int page){
        return attributeService.getAllAttributes(limit, page)
                .subscribeOn(Schedulers.io())
                .map(attributeResponses -> ResponseEntity.ok(BaseWebResponse.successWithData(
                    attributeResponses
                )));
    }

    @GetMapping(
            value = "/{attributeId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Single<ResponseEntity<BaseWebResponse<AttributeResponse>>> getAttributeDetail(
            @PathVariable(value = "attributeId") int attributeId){
        return attributeService.getAttributeDetail(attributeId)
                .subscribeOn(Schedulers.io())
                .map(attributeResponse -> ResponseEntity.ok(BaseWebResponse.successWithData(attributeResponse)));
    }

    //DELETE
    @DeleteMapping(
            value = "/{attributeId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Single<ResponseEntity<BaseWebResponse>> deleteAttribute(
            @PathVariable(value = "attributeId") int attributeId,
            @RequestParam(value = "type", defaultValue = "fisic") String delType){
        return attributeService.deleteAttribute(attributeId, delType)
                .subscribeOn(Schedulers.io())
                .toSingle(() -> ResponseEntity.ok(BaseWebResponse.successNoData()));
    }




}
