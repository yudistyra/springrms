package com.yudis.spring.inventory.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yudis.spring.inventory.model.Warehouse;
import com.yudis.spring.inventory.repository.WarehouseRepository;

@Service
public class WarehouseService {
	@Autowired
	private WarehouseRepository warehouseRepo;
	
	public Warehouse findById(Integer id) {
		return warehouseRepo.findById(id).orElse(null);
	}
	
	public List<Warehouse> findAll() {
		return warehouseRepo.findAll();
	}
	
	public String save(Warehouse warehouse) {
		
		if(warehouse.getId() == null)
			warehouse.setActive(true);
		
		Warehouse res = warehouseRepo.save(warehouse);
		
		if(res != null)
			return "success";
		
		return "failed";
	}
	
	public String delete(Integer id) {
		Warehouse warehouse = findById(id);
		
		if(warehouse != null) {
			warehouseRepo.delete(warehouse);
			return "success";
		}
		
		return "failed";
	}
}
