package com.demo.demo.service.cardbin;

import com.demo.demo.entity.Cardbin;
import com.demo.demo.repository.CardbinRepository;
import com.demo.demo.servicedto.request.AddCardbinRequest;
import io.reactivex.Single;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CardbinServiceImpl implements CardbinService {

    private final CardbinRepository cardbinRepository;

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
