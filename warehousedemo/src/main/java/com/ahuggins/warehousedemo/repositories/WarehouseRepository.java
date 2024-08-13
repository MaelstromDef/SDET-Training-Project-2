package com.ahuggins.warehousedemo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ahuggins.warehousedemo.models.Administrator;
import com.ahuggins.warehousedemo.models.Warehouse;
import java.util.List;
import java.util.Optional;


public interface WarehouseRepository extends JpaRepository<Warehouse, Integer> {
    Optional<Warehouse> findByIdAndAdministrator(int id, Administrator administrator);
    Optional<Warehouse> findByIdOrNameAndAdministrator(int id, String name, Administrator administrator);

    List<Warehouse> findByAdministrator(Administrator administrator);

    Optional<Warehouse> findByName(String name);
    Optional<Warehouse> findByNameAndAdministrator(String name, Administrator administrator);

    List<Warehouse> findByLocation(String location);
    List<Warehouse> findByLocationAndAdministrator(String location, Administrator administrator);
}
