package com.yudis.spring.inventory.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yudis.spring.inventory.model.Product;
import com.yudis.spring.inventory.model.ProductWarehouse;
import com.yudis.spring.inventory.model.Warehouse;
import com.yudis.spring.inventory.repository.ProductWarehouseRepository;

@Service
public class ProductWarehouseService {

	@Autowired
	private WarehouseService warehouseService;
	@Autowired
	private ProductWarehouseRepository productWarehouseRepo;
	
	public ProductWarehouse findById(Long id) {
		return productWarehouseRepo.findById(id).get();
	}
	
	public List<ProductWarehouse> getAllProductByWarehouse(Integer warehouseId) {
		Warehouse theWarehouse = warehouseService.findById(warehouseId);
		
		return theWarehouse.getProductWarehouse();
	}
	
	public String addWarehouseStock(ProductWarehouse productWarehouse) {
		
		Warehouse wh = productWarehouse.getWarehouse();
		
		if(wh.getMaxCapacity() >= (wh.getCurrentCapacity() + productWarehouse.getQty())) {
			productWarehouse.setTransDate(new Date());
			productWarehouseRepo.save(productWarehouse);
			
			wh.setCurrentCapacity(wh.getCurrentCapacity()+productWarehouse.getQty());
			warehouseService.save(wh);
			return "success";
		}
		else {
			return "Insuficient Capacity";
		}
		
	}
	
	public String deleteWarehouseStock(Long id) {
		try {
			ProductWarehouse pw = findById(id);
			pw.getWarehouse().setCurrentCapacity(pw.getWarehouse().getCurrentCapacity() - pw.getQty());
			warehouseService.save(pw.getWarehouse());
			productWarehouseRepo.deleteById(id);
			return "success";
		} catch (IllegalArgumentException e) {
			return "failed";
		}
	}
}
