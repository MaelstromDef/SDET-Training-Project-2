package com.ahuggins.warehousedemo.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.ahuggins.warehousedemo.dtos.AdministratorDto;
import com.ahuggins.warehousedemo.models.Administrator;
import com.ahuggins.warehousedemo.services.AdminService;


@CrossOrigin
@RestController
public class AdminController {
    private AdminService service;

    public AdminController(AdminService service){
        this.service = service;
    }

    // GET METHODS

    /**
     * Retrieves a list of all administrators from the database.
     * @return  A list of the ids and company names for all administrators stored.
     * @apiNote This mapping is unavailable within production, and should only be used for testing.
     */
    @GetMapping
    public List<AdministratorDto> getAdministrators() {
        return service.getAllAdministrators();
    }

    // POST METHODS

    /**
     * Attempts to log in an administrator.
     * @param admin Administrator to log in.
     * @return      Authorization token for the administrator, or a 401 error.
     */
    @PostMapping("/login")
    public String login(@RequestBody Administrator admin){
        try {
            return service.login(admin);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Attempts to sign up an administrator.
     * @param admin Administrator to sign up.
     * @return      Administrator information (excluding password), or a 409 error.
     */
    @PostMapping("/signup")
    public AdministratorDto signup(@RequestBody Administrator admin){
        Optional<AdministratorDto> dto = service.createAdministrator(admin);

        if(dto.isPresent()) return dto.get();
        throw new ResponseStatusException(HttpStatus.CONFLICT);
    }

    // PUT METHODS

    /**
     * Updates an administrator's information.
     * @param adminId   ID of the administrator to update.
     * @param admin     Administrator information to update with.
     * @return          Administrator information, or a 404 error.
     * @throws IllegalAccessException   Returns illegal access if the administrator's ID does not match the ID found in the auth. token.
     * @throws ResponseStatusException  404 error on not found.
     */
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public AdministratorDto updateAccount(@RequestAttribute int adminId, @RequestBody Administrator admin) 
        throws IllegalAccessException, ResponseStatusException{
        
        Optional<AdministratorDto> dto = service.updateAdministrator(adminId, admin);

        if(dto.isPresent()) return dto.get();
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    // DELETE METHODS

    /**
     * Removes an administrator from the database. 
     * @param adminId   ID of the administrator to remove, found in the request's authorization token.
     */
    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deleteAccount(@RequestAttribute int adminId){
        service.deleteAdministrator(adminId);
    }
}