package com.ahuggins.warehousedemo.mappers;

import org.springframework.stereotype.Component;

import com.ahuggins.warehousedemo.dtos.WarehouseDto;
import com.ahuggins.warehousedemo.models.Warehouse;

@Component
public class WarehouseMapper {
    public Warehouse toWarehouse(WarehouseDto dto){
        return new Warehouse(dto.getId(), dto.getName(), dto.getLocation(), dto.getSize());
    }

    public WarehouseDto toDto(Warehouse warehouse){
        return new WarehouseDto(warehouse.getId(), warehouse.getName(), warehouse.getLocation(), warehouse.getSize());
    }
}
