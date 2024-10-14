package com.ahuggins.warehousedemo.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ahuggins.warehousedemo.dtos.AdministratorDto;
import com.ahuggins.warehousedemo.mappers.AdminMapper;
import com.ahuggins.warehousedemo.models.Administrator;
import com.ahuggins.warehousedemo.repositories.AdministratorRepository;

@Service
public class AdminService {
    private AdministratorRepository repo;
    private AdminMapper mapper;

    public AdminService(AdministratorRepository repo, AdminMapper mapper){
        this.repo = repo;
        this.mapper = mapper;
    }

    public List<AdministratorDto> getAllAdministrators(){
        return repo.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    public Optional<AdministratorDto> getAdministratorById(int id){
        return repo.findById(id).map(mapper::toDto);
    }
    
    public String login(Administrator admin) throws Exception{
        String companyName=admin.getCompanyName(), password=SecurityService.hashString(admin.getPassword());

        // Find and verify admin
        List<Administrator> fromRepo = repo.findByCompanyNameAndPassword(companyName, password);
        if(fromRepo.isEmpty()) throw new Exception("Credentials not found");

        // Create JWT
        return SecurityService.getJwt(mapper.toDto(fromRepo.get(0)));
    }

    public Optional<AdministratorDto> createAdministrator(Administrator admin) {
        // Data validation
        if(admin.getCompanyName().isEmpty() ||
            admin.getPassword().isEmpty()) return Optional.empty();

        // Store administrator
        if(repo.findByCompanyName(admin.getCompanyName()).isEmpty()){
            try {
                String password=SecurityService.hashString(admin.getPassword());
                admin.setPassword(password);
                return Optional.of(mapper.toDto(repo.save(admin)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        return Optional.empty();
    }

    public Optional<AdministratorDto> updateAdministrator(int adminId, Administrator admin) throws IllegalAccessException {
        //if(adminId != admin.getId()) throw new IllegalAccessException("Mismatched administrator ID.");
        admin.setId(adminId);
        try{
            admin.setPassword(SecurityService.hashString(admin.getPassword()));
        }catch(Exception e){
            throw new IllegalAccessException("Password cannot be changed.");
        }

        Optional<Administrator> optStoredAdmin = repo.findById(admin.getId());

        if(optStoredAdmin.isPresent()){
            // Ensure data is never null/empty
            Administrator storedAdmin = optStoredAdmin.get();
            if(!admin.getCompanyName().isEmpty()) storedAdmin.setCompanyName(admin.getCompanyName());
            if(!admin.getPassword().isEmpty()) storedAdmin.setPassword(admin.getPassword());

            // Save and return
            return Optional.of(mapper.toDto(repo.save(storedAdmin)));
        }

        return Optional.empty();
    }

    public void deleteAdministrator(int id){
        repo.deleteById(id);
    }
}