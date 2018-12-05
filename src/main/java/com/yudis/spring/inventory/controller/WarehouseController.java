package com.yudis.spring.inventory.controller;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.yudis.spring.inventory.model.ProductWarehouse;
import com.yudis.spring.inventory.model.Warehouse;
import com.yudis.spring.inventory.service.ProductService;
import com.yudis.spring.inventory.service.ProductWarehouseService;
import com.yudis.spring.inventory.service.WarehouseService;

@Controller
public class WarehouseController {

	@Autowired
	private WarehouseService warehouseService;
	@Autowired
	private ProductWarehouseService transService;
	@Autowired
	private ProductService productService;
	
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
	
	@GetMapping("/warehouse/detail/{id}")
	public String warehouseDetails(@PathVariable String id, Model model) {
		List<ProductWarehouse> details = transService.getAllProductByWarehouse(Integer.parseInt(id));
		model.addAttribute("warehouseId", id);
		model.addAttribute("warehouseProduct", details);
		return "warehouse/detail/list";
	}
	
	@GetMapping("/warehouse/detail/{id}/addproduct")
	public String warehouseAddProduct(@PathVariable String id, Model model, @ModelAttribute("errorMessage") String errorMessage) {	
		model.addAttribute("warehouseId", id);
		model.addAttribute("productList", productService.findAllProduct());
		
		Warehouse w = warehouseService.findById(Integer.parseInt(id));
		ProductWarehouse pw = new ProductWarehouse();
		pw.setWarehouse(w);		
		model.addAttribute("productWarehouse", pw);
		if(!errorMessage.isEmpty()) {
			System.out.println(errorMessage);
			model.addAttribute("paramError", true);
			model.addAttribute("errorMessage", errorMessage);
		}
		
		return "warehouse/detail/addproduct";
	}
	
	@PostMapping("warehouse/detail/addproduct/submit")
	public String saveProductWarehouse(@Valid ProductWarehouse productWarehouse, BindingResult binding, RedirectAttributes redirectAttr, Model model) {
		
		if (binding.hasErrors()) {
			model.addAttribute("warehouseId", productWarehouse.getWarehouse().getId());
			model.addAttribute("productList", productService.findAllProduct());
			return "warehouse/detail/addproduct";
		}
		else {
			String result = transService.addWarehouseStock(productWarehouse);
            if(result.equalsIgnoreCase("success")) {
        		return "redirect:/warehouse/detail/" + productWarehouse.getWarehouse().getId();
            }
            else {
            	//redirectAttr.addAttribute("errorMessage", result);
            	redirectAttr.addFlashAttribute("errorMessage", result);
            }
        } 
		
		return "redirect:/warehouse/detail/" + productWarehouse.getWarehouse().getId() + "/addproduct";
	}
	
	@GetMapping("warehouse/detail/delete/{id}")
	public String deleteProductWarehouse(@PathVariable String id, Model model) {
		ProductWarehouse pw = transService.findById(Long.parseLong(id));
		Integer warehouseId = pw.getWarehouse().getId();
		String result = transService.deleteWarehouseStock(Long.parseLong(id));
		
		if(result.equalsIgnoreCase("success")) {
			return "redirect:/warehouse";
		}
		
		return "redirect:/warehouse/detail/" + warehouseId;
	}
}
