package com.demo.hospital.config;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.stereotype.Component;

@Component
public class MapperFacadeConfig {

    public MapperFacade mapper;

    public MapperFacadeConfig() {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        mapper = mapperFactory.getMapperFacade();
    }


}
