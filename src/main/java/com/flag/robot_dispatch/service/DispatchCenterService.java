package com.flag.robot_dispatch.service;

import com.flag.robot_dispatch.model.DispatchCenter;
import com.flag.robot_dispatch.repository.DispatchCenterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DispatchCenterService {
    private DispatchCenterRepository dispatchCenterRepository;

    @Autowired
    private DispatchCenterService(DispatchCenterRepository dispatchCenterRepository) {
        this.dispatchCenterRepository = dispatchCenterRepository;
    }

//    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void add(DispatchCenter center) {
        dispatchCenterRepository.save(center);
    }

    public DispatchCenter getCenterById(Long id) {
        return dispatchCenterRepository.findByIdIs(id);
    }

    public void deleteCenter(Long id) {
        dispatchCenterRepository.deleteById(id);
    }

}
