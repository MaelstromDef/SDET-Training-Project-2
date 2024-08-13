package com.ahuggins.warehousedemo.mappers;

import org.springframework.stereotype.Component;

import com.ahuggins.warehousedemo.dtos.AdministratorDto;
import com.ahuggins.warehousedemo.models.Administrator;

@Component
public class AdminMapper {
    public Administrator toAdmin(AdministratorDto dto){
        return new Administrator(dto.getId(), dto.getCompanyName());
    }

    public AdministratorDto toDto(Administrator admin){
        return new AdministratorDto(admin.getId(), admin.getCompanyName());
    }
}
