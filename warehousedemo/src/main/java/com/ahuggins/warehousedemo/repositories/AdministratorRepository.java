package com.ahuggins.warehousedemo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ahuggins.warehousedemo.models.Administrator;
import java.util.List;


public interface AdministratorRepository extends JpaRepository<Administrator, Integer> {
    public List<Administrator> findByCompanyNameAndPassword(String companyName, String password);

    public List<Administrator> findByCompanyName(String companyName);
}
