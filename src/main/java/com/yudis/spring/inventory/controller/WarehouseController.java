package com.yudis.spring.inventory.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.yudis.spring.inventory.model.Warehouse;
import com.yudis.spring.inventory.service.WarehouseService;

@Controller
public class WarehouseController {

	@Autowired
	private WarehouseService warehouseService;
	
	@GetMapping("/warehouse")
	public String warehouses(Model model) {
		List<Warehouse> warehouse = warehouseService.findAll();
		model.addAttribute("warehouse", warehouse);
		return "warehouse/list";
	}
	
	@GetMapping("/warehouse/create")
	public String createWarehouse(Model model) {
		Warehouse warehouse = new Warehouse();
		model.addAttribute("warehouse", warehouse);
		return "warehouse/create";
	}
	
	@PostMapping("/warehouse/submit")
	public String saveWarehouse(@Valid Warehouse warehouse, BindingResult binding, Model model) {
		
		if (!binding.hasErrors()) {
			String result = warehouseService.save(warehouse);
            if(result.equalsIgnoreCase("success")) {
        		return "redirect:/warehouse";
            }
        } 
		
		if(warehouse.getId() != null)
			return "warehouse/edit";
		
		return "warehouse/create";
	}
	
	@GetMapping("/warehouse/edit/{id}")
	public String editWarehouse(@PathVariable String id, Model model) {
		Warehouse warehouse = warehouseService.findById(Integer.parseInt(id));
		
		if(warehouse == null) {
			return "redirect:/warehouse";
		}
		
		model.addAttribute("warehouse", warehouse);
		return "warehouse/edit";
	}
	
	@GetMapping("/warehouse/delete/{id}")
	public String deleteWarehouse(@PathVariable String id,Model model) {
		String result = warehouseService.delete(Integer.parseInt(id));
		
		if(result.equalsIgnoreCase("success")) {
			model.addAttribute("message", "Warehouse has been deleted");
		} else {
			model.addAttribute("message", "Warehouse failed to delete");
		}
		
		return "redirect:/warehouse";
	}
}
