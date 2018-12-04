package com.yudis.spring.inventory.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yudis.spring.inventory.model.Product;
import com.yudis.spring.inventory.model.ProductWarehouse;
import com.yudis.spring.inventory.model.Warehouse;

@Service
public class TransactionService {

	@Autowired
	private WarehouseService warehouseService;
	@Autowired
	private ProductService productService;
	
	public List<ProductWarehouse> getAllProductByWarehouse(Integer warehouseId) {
		Warehouse theWarehouse = warehouseService.findById(warehouseId);
		
		return theWarehouse.getProductWarehouse();
	}
	
	public String addWarehouseStock(ProductWarehouse productWarehouse) {
		Warehouse warehouse = warehouseService.findById(productWarehouse.getWarehouse().getId());
		System.out.println(warehouse.getProductWarehouse().size());
		if(warehouse != null) {
			productWarehouse.setWarehouse(warehouse);
			productWarehouse.setTransDate(new Date());
			warehouse.getProductWarehouse().add(productWarehouse);
			System.out.println(warehouse.getProductWarehouse().get(1).getProduct().getName());
			warehouseService.save(warehouse);
		}
		
		return "success";
	}
}
