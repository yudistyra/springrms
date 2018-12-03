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

import com.yudis.spring.inventory.model.Product;
import com.yudis.spring.inventory.service.ProductService;

@Controller
public class ProductController {

	@Autowired
	private ProductService productService;

	@GetMapping("/products")
	public String products(Model model) {
		List<Product> products = productService.findAllProduct();
		model.addAttribute("products", products);
		return "products/list";
	}
	
	@GetMapping("/products/create")
	public String createProduct(Model model) {
		Product product = new Product();
		model.addAttribute("product", product);
		return "products/create";
	}
	
	@PostMapping("/products/submit")
	public String saveProduct(@Valid Product product, BindingResult binding, Model model) {
		
		if (!binding.hasErrors()) {
			String result = productService.save(product);
            if(result.equalsIgnoreCase("success")) {
        		return "redirect:/products";
            }
        } 
		
		return "products/create";
	}
	
	@GetMapping("/products/edit/{id}")
	public String editProduct(@PathVariable String id, Model model) {
		Product product = productService.findById(Long.parseLong(id));
		
		if(product == null) {
			return "redirect:/products";
		}
		
		model.addAttribute("product", product);
		return "products/edit";
	}
	
	@GetMapping("/products/delete/{id}")
	public String deleteProduct(@PathVariable String id,Model model) {
		String result = productService.delete(Long.parseLong(id));
		
		if(result.equalsIgnoreCase("success")) {
			model.addAttribute("message", "Product has been deleted");
		} else {
			model.addAttribute("message", "Product failed to delete");
		}
		
		return "redirect:/products";
	}
}
