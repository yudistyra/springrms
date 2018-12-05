package com.yudis.spring.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yudis.spring.inventory.model.ProductWarehouse;

@Repository
public interface ProductWarehouseRepository extends JpaRepository<ProductWarehouse, Long> {

}
