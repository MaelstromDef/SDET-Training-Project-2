package com.ahuggins.warehousedemo.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ahuggins.warehousedemo.dtos.AdministratorDto;
import com.ahuggins.warehousedemo.dtos.WarehouseDto;
import com.ahuggins.warehousedemo.mappers.WarehouseMapper;
import com.ahuggins.warehousedemo.models.Administrator;
import com.ahuggins.warehousedemo.models.Warehouse;
import com.ahuggins.warehousedemo.repositories.WarehouseRepository;

@Service
public class WarehouseService {
    private WarehouseRepository repo;
    private WarehouseMapper mapper;

    public WarehouseService(WarehouseRepository repo, WarehouseMapper mapper){
        this.repo = repo;
        this.mapper = mapper;
    }

    public List<Warehouse> getAllWarehouses(int adminId){
        return repo.findByAdministrator(new Administrator(adminId));
    }

    public Optional<Warehouse> getWarehouseById(int adminId, int id){
        return repo.findByIdAndAdministrator(id, new Administrator(id));
    }

    public Optional<Warehouse> getWarehouseByName(int adminId, String name){
        return repo.findByNameAndAdministrator(name, new Administrator(adminId));
    }

    public List<Warehouse> getWarehouseByLocation(int adminId, String location){
        return repo.findByLocationAndAdministrator(location, new Administrator(adminId));
    }

    public Optional<WarehouseDto> createWarehouse(int adminId, Warehouse warehouse) {
        if(repo.findByIdOrNameAndAdministrator(warehouse.getId(), warehouse.getName(), new Administrator(adminId)).isEmpty()){
            return Optional.of(mapper.toDto(repo.save(warehouse)));
        }
        
        return Optional.empty();
    }

    public Optional<WarehouseDto> updateWarehouse(int adminId, int warehouseId, Warehouse warehouse){
        if(repo.findByIdAndAdministrator(warehouseId, new Administrator(adminId)).isPresent()){
            // Information assurance
            warehouse.setId(warehouseId);
            warehouse.setAdministrator(new Administrator(adminId));

            // Save and return
            Warehouse saved = repo.save(warehouse);
            return Optional.of(mapper.toDto(saved));
        }
        
        return Optional.empty();
    }

    public void deleteWarehouse(int adminId, int warehouseId){
        if(repo.findByIdAndAdministrator(warehouseId, new Administrator(adminId)).isEmpty()) return;    // Remove possibility of cross-admin deletions.
        repo.deleteById(Integer.valueOf(warehouseId));
    }
}