package com.study.base.boot.config.mapstruct.base.config;

import com.study.base.boot.config.mapstruct.base.BaseEntity;
import org.hibernate.Hibernate;
import org.mapstruct.MapperConfig;

import java.util.List;

@MapperConfig
public interface MapstructConfig {

    default  boolean isLoaded(List<? extends BaseEntity> entities){
        return Hibernate.isInitialized(entities);
    }

    default  boolean isLoaded(BaseEntity entity){ return Hibernate.isInitialized(entity); }
}
