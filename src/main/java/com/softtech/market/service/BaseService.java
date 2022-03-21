package com.softtech.market.service;

import com.softtech.market.model.BaseAdditionalFields;
import com.softtech.market.model.BaseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class BaseService<E extends BaseEntity> {

    private AuthenticationService authenticationService;

    @Autowired
    public void setProductService(@Lazy AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    public void addBaseAdditionalFields(E entity){

        BaseAdditionalFields baseAdditionalFields = entity.getBaseAdditionalFields();
        Long currentUserId = getCurrentUserId();

        if (baseAdditionalFields == null){
            baseAdditionalFields = new BaseAdditionalFields();
            entity.setBaseAdditionalFields(baseAdditionalFields);
        }

        if (entity.getId() == null){
            baseAdditionalFields.setCreateDate(new Date());
            baseAdditionalFields.setCreatedBy(currentUserId);
        }

        baseAdditionalFields.setUpdateDate(new Date());
        baseAdditionalFields.setUpdatedBy(currentUserId);
    }

    public Long getCurrentUserId() {
        Long currentUserId = authenticationService.getCurrentUserId();
        return currentUserId;
    }

}
