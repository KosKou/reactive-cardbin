package com.demo.demo.service.attribute;

import com.demo.demo.entity.Attribute;
import com.demo.demo.entity.Cardbin;
import com.demo.demo.repository.AttributeRepository;
import com.demo.demo.repository.CardbinRepository;
import com.demo.demo.servicedto.request.AddAttributeRequest;
import com.demo.demo.servicedto.request.UpdateAttributeRequest;
import com.demo.demo.servicedto.response.AttributeResponse;
import com.demo.demo.webdto.request.UpdateAttributeWebRequest;
import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AttributeServiceImpl implements AttributeService{

    private final AttributeRepository attributeRepository;
    private final CardbinRepository cardbinRepository;

    @Override
    public Single addAttribute(AddAttributeRequest addAttributeRequest) {
        return addAttributeToRepository(addAttributeRequest);
    }

    //TODO: Think about a better method name
//    private Maybe refactoringSaveTry(Integer id, Attribute data, JpaRepository validate, JpaRepository action){
//        return Maybe.fromCallable(() -> validate.getOne(id))
//                .switchIfEmpty(Single.error(new EntityNotFoundException()))
//                .map(s -> action.save(data))
//                .toMaybe();
//    }


    private Single<Integer> addAttributeToRepository(AddAttributeRequest addAttributeRequest){
        //TODO: Processing
//        return refactoringSaveTry(addAttributeRequest.getCardbinId(),
//                toAttribute(addAttributeRequest),
//                cardbinRepository,
//                attributeRepository).toSingle();
        return Single.create(singleEmitter -> {
            Optional<Cardbin> optionalCardbin = cardbinRepository.findById(addAttributeRequest.getCardbinId());
            if (!optionalCardbin.isPresent()){
                singleEmitter.onError(new EntityNotFoundException());
            }else {
                Integer addedAttributeId = attributeRepository
                        .save(toAttribute(addAttributeRequest)).getId();
                singleEmitter.onSuccess(addedAttributeId);
            }
        });
    }

    private Attribute toAttribute(AddAttributeRequest addAttributeRequest){
        return Attribute.builder()
                .key(addAttributeRequest.getKey())
                .value(addAttributeRequest.getValue())
                .state("ACTIVE")
                .cardbin(Cardbin.builder()
                        .id(addAttributeRequest.getCardbinId())
                        .build()
                ).build();
    }

    @Override
    public Completable updateAttribute(UpdateAttributeWebRequest updateAttributeWebRequest, Integer attributeId) {
        return updateAttributeRepository(toUpdateAttributeRequest(updateAttributeWebRequest, attributeId));
    }

    private UpdateAttributeRequest toUpdateAttributeRequest(UpdateAttributeWebRequest updateAttributeWebRequest
            , Integer attributeId){
        return UpdateAttributeRequest.builder()
                .key(updateAttributeWebRequest.getKey())
                .value(updateAttributeWebRequest.getValue())
                .id(attributeId)
                .build();
    }

    private Completable updateAttributeRepository(UpdateAttributeRequest updateAttributeRequest){
        //TODO: Processing
//        return refactoringSaveTry(updateAttributeRequest.getId(), Attribute.builder()
//                .id(updateAttributeRequest.getId())
//                .key(updateAttributeRequest.getKey())
//                .value(updateAttributeRequest.getValue())
//                .build(),
//                attributeRepository,
//                attributeRepository)
//                .ignoreElement();

        return Completable.create(completableEmitter -> {
            Optional<Attribute> optionalAttribute = attributeRepository.findById(updateAttributeRequest.getId());
            if (!optionalAttribute.isPresent()){
                completableEmitter.onError(new EntityNotFoundException());
            }else {
                Attribute attribute = optionalAttribute.get();
                attribute.setKey(updateAttributeRequest.getKey());
                attribute.setValue(updateAttributeRequest.getValue());
                attribute.setState(optionalAttribute.get().getState());     //Keep State
                attributeRepository.save(attribute);
                completableEmitter.onComplete();
            }
        });
    }

    @Override
    public Single<List<AttributeResponse>> getAllAttributes(int limit, int page) {
        return findAllAttributesInRepository(limit, page)
                .map(this::toAttributeResponseList);
    }

    private Single<List<Attribute>> findAllAttributesInRepository(int limit, int page){
        return Single.create(singleEmitter -> {
            List<Attribute> attributes = attributeRepository.findAll(PageRequest.of(page,limit)).getContent();
            singleEmitter.onSuccess(attributes);
        });
    }

    private List<AttributeResponse> toAttributeResponseList(List<Attribute> attributeList){
        return attributeList
                .stream()
                .map(this::toAttributeResponse)
                .collect(Collectors.toList());
    }

    private AttributeResponse toAttributeResponse(Attribute attribute){
        return AttributeResponse.builder()
                .id(attribute.getId())
                .key(attribute.getKey())
                .value(attribute.getValue())
                .state(attribute.getState())
                .binType(attribute.getCardbin().getBinType())
                .build();
    }

    @Override
    public Single<AttributeResponse> getAttributeDetail(int id) {
        return getAttributeDetailInRepository(id);
    }

    private Single<AttributeResponse> getAttributeDetailInRepository(int id){
        return Single.create(singleEmitter -> {
            Optional<Attribute> optionalAttribute = attributeRepository.findById(id);
            if (!optionalAttribute.isPresent()){
                singleEmitter.onError(new EntityNotFoundException());
            }else {
                AttributeResponse attributeResponse = toAttributeResponse(optionalAttribute.get());
                singleEmitter.onSuccess(attributeResponse);
            }
        });
    }

    @Override
    public Completable deleteAttribute(int id, String delType) {
        return deleteAttributeInRepository(id, delType);
    }
    private Completable deleteAttributeInRepository(int id, String delType){
        return Completable.create(completableEmitter -> {
            Optional<Attribute> optionalAttribute = attributeRepository.findById(id);
            if (!optionalAttribute.isPresent()){
                completableEmitter.onError(new EntityNotFoundException());
            }else{
                if (delType.equals("fisic")){
                    attributeRepository.delete(optionalAttribute.get());
                }else {
                    Attribute attribute = optionalAttribute.get();
                    attribute.setState("INACTIVE");
                    attributeRepository.save(attribute);
                }
                completableEmitter.onComplete();
            }
        });
    }
}
