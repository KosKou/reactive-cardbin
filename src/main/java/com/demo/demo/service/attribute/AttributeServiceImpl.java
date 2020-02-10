package com.demo.demo.service.attribute;

import com.demo.demo.entity.Attribute;
import com.demo.demo.entity.Cardbin;
import com.demo.demo.repository.AttributeRepository;
import com.demo.demo.repository.CardbinRepository;
import com.demo.demo.servicedto.request.AddAttributeRequest;
import com.demo.demo.servicedto.request.UpdateAttributeRequest;
import com.demo.demo.servicedto.response.AttributeResponse;
import io.reactivex.Completable;
import io.reactivex.Single;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AttributeServiceImpl implements AttributeService{

    private final AttributeRepository attributeRepository;
    private final CardbinRepository cardbinRepository;


    @Override
    public Single<Integer> addAttribute(AddAttributeRequest addAttributeRequest) {
        return saveAttributeToRepository(addAttributeRequest);
    }

    private Single<Integer> saveAttributeToRepository(AddAttributeRequest addAttributeRequest){
        return Single.create(singleEmitter -> {
            Optional<Cardbin> optionalCardbin = cardbinRepository.findById(addAttributeRequest.getCardbinId());
            if (!optionalCardbin.isPresent()){
                singleEmitter.onError(new EntityNotFoundException());
            }else {
                Integer addedAttributeId = attributeRepository
                        .save(toAttribute(addAttributeRequest)).getId();
            }
        });
    }

    private Attribute toAttribute(AddAttributeRequest addAttributeRequest){
        Attribute attribute = new Attribute();
//        List<Attribute> attributes = new ArrayList<Attribute>();
//        cardbin.setAttributes(attributes);
        BeanUtils.copyProperties(addAttributeRequest, attribute);
        attribute.setState("ACTIVE");
//        attribute.setCardbin(cardbin);
        attribute.setCardbin(Cardbin.builder()
            .id(addAttributeRequest.getCardbinId())
            .build());
//        attribute.getCardbin().setBinType("TEST");
        return attribute;
    }

    @Override
    public Completable updateAttribute(UpdateAttributeRequest updateAttributeRequest) {
        return updateAttributeRepository(updateAttributeRequest);
    }

    private Completable updateAttributeRepository(UpdateAttributeRequest updateAttributeRequest){
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
        AttributeResponse attributeResponse = new AttributeResponse();
        BeanUtils.copyProperties(attribute, attributeResponse);
        attributeResponse.setBinType(attribute.getCardbin().getBinType());
        return attributeResponse;
    }

    @Override
    public Single<AttributeResponse> getAttributeDetail(int id) {
        return null;
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
    //TODO: Check if this shit works or not
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
