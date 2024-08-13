package com.ahuggins.warehousedemo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ahuggins.warehousedemo.models.Item;
import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Integer>{
    Optional<Item> findByName(String name);
}
