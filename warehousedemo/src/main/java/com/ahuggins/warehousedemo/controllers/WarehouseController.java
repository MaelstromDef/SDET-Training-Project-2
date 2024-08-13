package com.ahuggins.warehousedemo.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.ahuggins.warehousedemo.dtos.WarehouseDto;
import com.ahuggins.warehousedemo.models.Warehouse;
import com.ahuggins.warehousedemo.services.WarehouseService;


@CrossOrigin
@RestController
@RequestMapping("/{adminId}")
public class WarehouseController {
    private WarehouseService service;

    public WarehouseController(WarehouseService service){
        this.service = service;
    }

    // GET METHODS

    /**
     * Retrieves all warehouses owned by an administrator.
     * @param adminId   ID of the owning administrator.
     * @return          A list of all ownded warehouses.
     */
    @GetMapping
    @ResponseStatus(HttpStatus.FOUND)
    public List<Warehouse> getAllWarehouses(@RequestAttribute int adminId){
        return service.getAllWarehouses(adminId);
    }

    /**
     * Retrieves a particular warehouse.
     * @param adminId       ID of the owning administrator.
     * @param warehouseId   ID of the warehouse to retrieve.
     * @return              The warehouse requested, or a 404 error.
     */
    @GetMapping("/{warehouseId}")
    @ResponseStatus(HttpStatus.FOUND)
    public Warehouse getWarehouseById(@RequestAttribute int adminId, @PathVariable int warehouseId){
        Optional<Warehouse> warehouse = service.getWarehouseById(adminId, warehouseId);
        
        if(warehouse.isPresent()) return warehouse.get();
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    /**
     * Retrieves a particular warehouse.
     * @param adminId       ID of the owning administrator.
     * @param warehouseName Name of the warehouse to retrieve.
     * @return              The warehouse requested, or a 404 error.
     */
    @GetMapping("/warehouseByName")
    @ResponseStatus(HttpStatus.FOUND)
    public Warehouse getWarehouseByName(@RequestAttribute int adminId, @RequestParam String warehouseName){
        Optional<Warehouse> warehouse = service.getWarehouseByName(adminId, warehouseName);

        if(warehouse.isPresent()) return warehouse.get();
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    /**
     * Retrieves warehouses that are in a particular location.
     * @param adminId   ID of the owning administrator.
     * @param location  Location to retrieve the warehouses from.
     * @return          A list of the warehouses at that location, or a 404 error.
     */
    @GetMapping("/warehouseByLocation")
    @ResponseStatus(HttpStatus.FOUND)
    public List<Warehouse> getWarehouseByLocation(@RequestAttribute int adminId, @RequestParam String location){
        List<Warehouse> warehouses = service.getWarehouseByLocation(adminId, location);

        if(warehouses.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return warehouses;
    }

    // POST METHODS

    /**
     * Creates a warehouse.
     * @param adminId   ID of the administrator to create the warehouse for.
     * @param warehouse Warehouse to create for the administrator.
     * @return          A representation of the warehouse that was created, or a 409 error.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public WarehouseDto createWarehouse(@RequestAttribute int adminId, @RequestBody Warehouse warehouse){
        Optional<WarehouseDto> optional = service.createWarehouse(adminId, warehouse);

        if(optional.isPresent()) return optional.get();
        throw new ResponseStatusException(HttpStatus.CONFLICT);
    }

    // PUT METHODS

    /**
     * Updates a warehouse's information.
     * @param adminId       ID of the owning administrator.
     * @param warehouseId   ID of the warehouse to update.
     * @param warehouse     Information to update the warehouse with.
     * @return              A representation of the warehouse that was updated, or a 404 error.
     */
    @PutMapping("/{warehouseId}")
    @ResponseStatus(HttpStatus.OK)
    public WarehouseDto updateWarehouse(@RequestAttribute int adminId, @PathVariable int warehouseId, @RequestBody Warehouse warehouse){
        Optional<WarehouseDto> optional = Optional.empty();
        try{
            optional = service.updateWarehouse(adminId, warehouseId, warehouse);
        }catch(DataIntegrityViolationException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        if(optional.isPresent()) return optional.get();
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    // DELETE METHODS

    /**
     * Removes a warehouse from a database.
     * @param adminId       ID of the owning administrator.
     * @param warehouseId   ID of the warehouse to delete.
     */
    @DeleteMapping("/{warehouseId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteWarehouse(@RequestAttribute int adminId, @PathVariable int warehouseId){
        service.deleteWarehouse(adminId, warehouseId);
    }
}