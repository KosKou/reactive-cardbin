package com.demo.demo.service.cardbin;

import com.demo.demo.entity.Attribute;
import com.demo.demo.entity.Cardbin;
import com.demo.demo.repository.AttributeRepository;
import com.demo.demo.repository.CardbinRepository;
import com.demo.demo.servicedto.request.AddCardbinRequest;
import com.demo.demo.servicedto.request.UpdateCardbinRequest;
import com.demo.demo.webdto.request.UpdateCardbinWebRequest;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CardbinServiceImpl implements CardbinService {

    private final CardbinRepository cardbinRepository;
    private final AttributeRepository attributeRepository;

    @Override
    public Completable deleteCardbin(Integer cardbinId) {
        return Completable.create(completableEmitter -> {
            Optional<Cardbin> optionalCardbin = cardbinRepository.findById(cardbinId);
            if (!optionalCardbin.isPresent()){
                completableEmitter.onError(new EntityNotFoundException());
            }else {
                cardbinRepository.deleteById(cardbinId);
                completableEmitter.onComplete();
            }
        });
    }

    @Override
    public Completable updateCardbin(UpdateCardbinWebRequest updateCardbinWebRequest, Integer cardbinId) {
        return Completable.create(completableEmitter -> {
            UpdateCardbinRequest updateCardbinRequest =
                    toCardbinRequest(updateCardbinWebRequest, cardbinId);
            Optional<Cardbin> optionalCardbin = cardbinRepository
                   .findById(updateCardbinRequest.getId());
           if (!optionalCardbin.isPresent()){
               completableEmitter.onError(new EntityNotFoundException());
           }else {
               Cardbin cardbin = optionalCardbin.get();
               cardbin.setBinType(updateCardbinRequest.getBinType());
               cardbin.setBinNumber(updateCardbinRequest.getBinNumber());
               cardbinRepository.save(cardbin);
               completableEmitter.onComplete();
           }
        });
    }

    private UpdateCardbinRequest toCardbinRequest(UpdateCardbinWebRequest updateCardbinWebRequest, Integer cardbinId){
        UpdateCardbinRequest updateCardbinRequest = new UpdateCardbinRequest();
        BeanUtils.copyProperties(updateCardbinWebRequest, updateCardbinRequest);
        updateCardbinRequest.setId(cardbinId);
        return updateCardbinRequest;
    }

    @Override
    public Observable<List<Attribute>> getCardbinAttributes(Integer id) {
        return Observable.just(attributeRepository.findAllByCardbin_Id(id));
    }

    @Override
    public Single<Cardbin> getCardbinDetail(Integer id) {
        return Single.create(singleEmitter -> {
            Optional<Cardbin> optionalCardbin = cardbinRepository.findById(id);
            if (!optionalCardbin.isPresent()){
                singleEmitter.onError(new EntityNotFoundException());
            }else {
                Cardbin cardbin = optionalCardbin.get();
                singleEmitter.onSuccess(cardbin);
            }
        });
    }

    @Override
    public Observable<List<Cardbin>> getAllCardbins() {
        return findAllCardbinsInRepository();
    }

    private Observable<List<Cardbin>> findAllCardbinsInRepository(){
        return Observable.just(cardbinRepository.findAll());
    }

    @Override
    public Single<Integer> addCardbin(AddCardbinRequest addCardbinRequest) {
        return addCarbinInRepository(addCardbinRequest);
    }

    private Single<Integer> addCarbinInRepository(AddCardbinRequest addCardbinRequest){
        return Single.create(singleEmitter -> {
            Integer addedCardbinId = cardbinRepository.save(toCardbin(addCardbinRequest)).getId();
            singleEmitter.onSuccess(addedCardbinId);
        });
    }

    private Cardbin toCardbin(AddCardbinRequest addCardbinRequest){
        Cardbin cardbin = new Cardbin();
        BeanUtils.copyProperties(addCardbinRequest, cardbin);
        cardbin.setState("ACTIVE");
        return cardbin;
    }
}
